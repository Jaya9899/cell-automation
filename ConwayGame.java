//conway game of life implementation

public class ConwayGame implements Automata {
    @Override 
    public void nextGeneration(boolean[][] cells) {
        int rows = cells.length;
        int cols = cells[0].length;
        boolean[][] newCells = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int aliveNeighbors = countAliveNeighbors(cells, r, c);
                if (cells[r][c]) { 
                    newCells[r][c] = (aliveNeighbors == 2 || aliveNeighbors == 3);
                } else { 
                    newCells[r][c] = (aliveNeighbors == 3);
                }
            }
        }

        for (int r = 0; r < rows; r++) {
            System.arraycopy(newCells[r], 0, cells[r], 0, cols);
        }
    }
    private int countAliveNeighbors(boolean[][] cells, int row, int col) {
        int count = 0;
        int rows = cells.length;
        int cols = cells[0].length;

        for (int r=row-1; r<=row+1; r++) {
            for (int c=col-1; c<= col+1; c++) {
                if (r==row && c==col) continue; 
                if (r>=0 && r<rows && c >= 0 && c < cols && cells[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }
    @Override
    public String getRulesDescription() {
        return "Conway's Game of Life Rules:\n" +
               "1. Any live cell with fewer than two live neighbors dies, as if by under-population.\n"+
               "2. Any live cell with two or three live neighbors live on to the next generation.\n" +
               "3. Any live cell with more than three live neighbors dies, as if by overpopulation.\n" +
               "4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.";
    }
}
