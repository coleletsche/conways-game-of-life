import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    public static final Dimension WINDOW_DIMENSIONS = new Dimension(Board.COLS * Board.CELL_DIMENSIONS.width, Board.ROWS * Board.CELL_DIMENSIONS.height);
    public static final String TITLE = "Conway's Game of Life";
    private final JFrame frame = new JFrame(TITLE);

    public Window(Main main) {

        frame.setPreferredSize(WINDOW_DIMENSIONS);
        frame.setMaximumSize(WINDOW_DIMENSIONS);
        frame.setMinimumSize(WINDOW_DIMENSIONS);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(main);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        main.start();
    }
}
