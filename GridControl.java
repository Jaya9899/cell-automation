
import java.awt.*;

public class GridControl {
    private boolean[][] grid;
    private Automata automaton;
    private CAframe frame;
    private Thread playThread;
    private volatile boolean running = false;

    public GridControl(CAframe frame, boolean[][] grid) {
        this.frame = frame;
        this.grid = grid;
        setAutomaton(new ConwayGame());
        addListeners();
    }

    public void setAutomaton(Automata automaton) {
        this.automaton = automaton;
    }
    private void addListeners() {
        frame.getPlayButton().addActionListener(_ -> start());
        frame.getPauseButton().addActionListener(_ -> pause());
        frame.getStopButton().addActionListener(_ -> reset());
        frame.getRandomButton().addActionListener(_ -> randomize());
        
        frame.getAutomataChoice().addItemListener(_ -> {
            String choice = frame.getAutomataChoice().getSelectedItem();
            switch (choice) {
                case "Conway's Game of Life": setAutomaton(new ConwayGame()); break;
                //case "Brian's Brain": setAutomaton(new BriansBrain()); break;
                // can add more automata here
            }
        });

        frame.getRulesButton().addActionListener(_ -> showPopup(automaton.getRulesDescription()));
        frame.getAboutButton().addActionListener(_ -> showPopup("This is an interactive Cellular Automata Simulator inspired by Conway’s Game of Life. Each cell on the grid is an object that can be alive or dead, and you can toggle cells by clicking. The simulation supports multiple automata types, and users can experiment with different rules and patterns. The project demonstrates OOP concepts like encapsulation (cells as objects), abstraction (different automata types), and polymorphism (extending rules easily). You can add your own rules, create new patterns, or change cell behaviors to explore the fascinating world of cellular automata!"));
        }

    private void start() {
        if (running) return;
        running = true;
        playThread = new Thread(() -> {
            while (running) {
                automaton.nextGeneration(grid);
                frame.repaintGrid();
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        });
        playThread.start();
    }

    private void pause() {
        running = false;
    }

    private void reset() {
        pause();
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                grid[i][j] = false;
        frame.repaintGrid();
    }

    private void randomize() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                grid[i][j] = Math.random() > 0.7;
        frame.repaintGrid();
    }

    private void showPopup(String message) {
    Dialog dialog = new Dialog(frame, "Info", true);
    dialog.setLayout(new BorderLayout());
    dialog.setSize(350, 180); // Small area

    TextArea textArea = new TextArea(message, 5, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
    textArea.setEditable(false);
    dialog.add(textArea, BorderLayout.CENTER);

    Button closeBtn = new Button("Close");
    closeBtn.addActionListener(_ -> dialog.dispose());
    Panel btnPanel = new Panel();
    btnPanel.add(closeBtn);
    dialog.add(btnPanel, BorderLayout.SOUTH);

    dialog.setLocationRelativeTo(frame);
    dialog.setVisible(true);
}
}
