public class Cell {

    private int row;
    private int col;
    private boolean alive;

    public Cell(int row, int col, boolean alive) {
        this.row = row;
        this.col = col;
        this.alive = alive;
    }

    public Cell(int row, int col) {
        new Cell(row, col, false);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
