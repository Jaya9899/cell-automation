import java.awt.*;
import java.awt.event.*;

class CAframe extends Frame {
    private Panel controlPanel;
    private Canvas gridCanvas;
    private Choice automataType;
    private Panel bottomPanel;

    private int rows = 40; 
    private int cols = 60;
    private boolean[][] cells; 

    private Button playbtn,pausebtn,stopbtn,randombtn,rulesBtn,aboutBtn,helpBtn;

    public CAframe() {
        setTitle("Cellular Automata");
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        cells = new boolean[rows][cols];
        Color PURPLE = new Color(167, 148, 171);
        controlPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        controlPanel.setBackground(PURPLE);
        playbtn = new Button("Play");
        pausebtn = new Button("Pause");
        stopbtn = new Button("Reset");
        randombtn = new Button("Random Pattern");
        automataType = new Choice();
        
        automataType.add("Conway's Game of Life");
        automataType.add("Brian's Brain");
        automataType.add("Other");

        controlPanel.add(playbtn);
        controlPanel.add(new Label("   ")); 
        controlPanel.add(pausebtn);
        controlPanel.add(new Label("   ")); 
        controlPanel.add(stopbtn);
        controlPanel.add(new Label("   ")); 
        controlPanel.add(randombtn);
        controlPanel.add(new Label("   ")); 
        Label typeLabel=new Label("Automata Type:");
        typeLabel.setForeground(Color.WHITE);
        controlPanel.add(typeLabel);

        controlPanel.add(automataType);
        controlPanel.add(new Label("   ")); 

        add(controlPanel, BorderLayout.NORTH);

        gridCanvas = new Canvas() {
            Image offscreen;
            Graphics offG;

            @Override
            public void paint(Graphics g) {
                int canvasWidth = getWidth();
                int canvasHeight = getHeight();

                if (offscreen == null || offscreen.getWidth(this) != canvasWidth || offscreen.getHeight(this) != canvasHeight) {
                    offscreen = createImage(canvasWidth, canvasHeight);
                    offG = offscreen.getGraphics();
                }

                offG.setColor(getBackground());
                offG.fillRect(0, 0, canvasWidth, canvasHeight);

                int cellSize = Math.min(canvasWidth / cols, canvasHeight / rows);
                int xOffset = (canvasWidth - cellSize * cols) / 2;
                int yOffset = (canvasHeight - cellSize * rows) / 2;
                Color PINK = new Color(219, 196, 224);
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        offG.setColor(cells[i][j] ? PINK : Color.BLACK);
                        offG.fillRect(xOffset + j * cellSize, yOffset + i * cellSize, cellSize, cellSize);
                        offG.setColor(Color.DARK_GRAY);
                        offG.drawRect(xOffset + j * cellSize, yOffset + i * cellSize, cellSize, cellSize);
                    }
                }

                g.drawImage(offscreen, 0, 0, this);
            }

            @Override
            public void update(Graphics g) {
                paint(g); 
            }
        };


        gridCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int canvasWidth = gridCanvas.getWidth();
                int canvasHeight = gridCanvas.getHeight();
                int cellSize = Math.min(canvasWidth / cols, canvasHeight / rows);
                int xOffset = (canvasWidth - cellSize * cols) / 2;
                int yOffset = (canvasHeight - cellSize * rows) / 2;

                int c = (e.getX() - xOffset) / cellSize;
                int r = (e.getY() - yOffset) / cellSize;

                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    cells[r][c] = !cells[r][c];
                    gridCanvas.repaint(xOffset + c * cellSize, yOffset + r * cellSize, cellSize, cellSize);
                }
            }
        });

        Panel gridContainer = new Panel(new BorderLayout());
        gridContainer.setBackground(Color.BLACK);
        gridContainer.add(gridCanvas, BorderLayout.CENTER);
        gridContainer.setPreferredSize(new Dimension(800, 600)); 
        add(gridContainer, BorderLayout.CENTER);

        bottomPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bottomPanel.setBackground(PURPLE);

        rulesBtn = new Button("Rules");
        aboutBtn = new Button("About");
       
        bottomPanel.add(rulesBtn);
        bottomPanel.add(aboutBtn);
      

        add(bottomPanel, BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        }

        public Button getPlayButton() { 
            return playbtn; 
        }
        public Button getPauseButton() { 
            return pausebtn; 
        }
        public Button getStopButton() { 
            return stopbtn; 
        }
        public Button getRandomButton() { 
            return randombtn; 
        }
        public Choice getAutomataChoice() { 
            return automataType; 
        }
        public Button getRulesButton() { 
            return rulesBtn; 
        }
        public Button getAboutButton() { 
            return aboutBtn; 
        }
        public void repaintGrid() { 
            gridCanvas.repaint(); 
        }
        public boolean[][] getCells() { 
            return cells; 
        }
        

        
}
public class CellAutomata {
    public static void main(String[] args) {
        CAframe frame= new CAframe();
        new GridControl(frame, frame.getCells());
    }
}
