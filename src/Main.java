import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    private Window window;
    private Thread thread;
    private boolean running;

    private Board board;

    public Main() {
        window = new Window(this);

        board = new Board();

        this.addMouseListener(board);
        this.addMouseMotionListener(board);
        this.addKeyListener(board);
        requestFocus();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 15.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                try {
                    tick();
                    render();
                    updates++;
                    frames++;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        // Getting graphics from buffer
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0, Window.WINDOW_DIMENSIONS.width, Window.WINDOW_DIMENSIONS.height);

        board.render(g);

        // showing graphics
        g.dispose();
        bs.show();
    }

    private void tick() {
        board.tick();
    }

    public static void main(String[] args) {
        new Main();
    }
}
