import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Board implements ITickable, IRenderable, MouseListener, MouseMotionListener, KeyListener {

    public static final int ROWS = 75;
    public static final int COLS = 125;

    public static final Dimension CELL_DIMENSIONS = new Dimension(10, 10);

    private static final int[][] delta = {
            {-1, -1},
            {0, -1},
            {1, -1},
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1},
            {-1, 0}
    };

    private Cell[][] board;

    private boolean running = false;

    public Board() {

        board = new Cell[ROWS][COLS];

        randomizeBoard();
    }

    public void render(Graphics g) {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                g.setColor(Color.WHITE);
                if (board[i][j].isAlive())
                    g.setColor(Color.BLACK);

                g.fillRect(j * CELL_DIMENSIONS.width, i * CELL_DIMENSIONS.height, CELL_DIMENSIONS.width, CELL_DIMENSIONS.height);
            }
        }

        // Render grid
        g.setColor(Color.GRAY);
        for (int i = 0; i < ROWS; i++)
            g.drawLine(0, i * CELL_DIMENSIONS.height, Window.WINDOW_DIMENSIONS.width, i * CELL_DIMENSIONS.height);
            for (int j = 0; j < COLS; j++)
                g.drawLine(j * CELL_DIMENSIONS.width, 0, j * CELL_DIMENSIONS.width, Window.WINDOW_DIMENSIONS.height);
    }

    public void tick() {
        Cell[][] copy = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                copy[i][j] = new Cell(i, j);
            }

        if (running) {
            for (int y = 0; y < ROWS; y++) {
                for (int x = 0; x < COLS; x++) {
                    int aliveNeighbours = 0;
                    for (int[] d : delta) {
                        try {
                            aliveNeighbours += board[y + d[1]][x + d[0]].isAlive() ? 1 : 0;
                        } catch (Exception ignored) {
                        }
                    }

                    if ((board[y][x].isAlive() && aliveNeighbours < 2) || (board[y][x].isAlive() && aliveNeighbours > 3))
                        copy[y][x].setAlive(false);
                    else if (!board[y][x].isAlive() && aliveNeighbours == 3)
                        copy[y][x].setAlive(true);
                    else
                        copy[y][x].setAlive(board[y][x].isAlive());
                }
            }
            board = copy;
        }
    }

    private void resetBoard() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                board[i][j] = new Cell(i, j);
    }

    private void randomizeBoard() {
        Random rand = new Random();

        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                if (rand.nextInt(6) == 0)
                    board[i][j] = new Cell(i, j, true);
                else
                    board[i][j] = new Cell(i, j, false);
    }

    public String toString() {
        String s = "";
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++)
                s += board[row][col].isAlive() ? "1 " : "0 ";
            s += "\n";
        }
        return s;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        running = false;

        int indexY = (int) (e.getY() / CELL_DIMENSIONS.height);
        int indexX = (int) (e.getX() / CELL_DIMENSIONS.width);

        board[indexY][indexX].setAlive(!board[indexY][indexX].isAlive());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int indexY = (int) (e.getY() / CELL_DIMENSIONS.height);
        int indexX = (int) (e.getX() / CELL_DIMENSIONS.width);

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (!board[indexY][indexX].isAlive())
                board[indexY][indexX].setAlive(true);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (board[indexY][indexX].isAlive())
                board[indexY][indexX].setAlive(false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    // Keys Down
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            running = !running;
        } else if (key == KeyEvent.VK_C) {
            running = false;
            resetBoard();
        } else if (key == KeyEvent.VK_R) {
            running = false;
            randomizeBoard();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
