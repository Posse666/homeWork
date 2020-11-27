import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MainCircles extends JFrame {

    private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    private static final int PADDING = 100;
    private static final int POS_X = PADDING;
    private static final int POS_Y = PADDING;
    private static final int WINDOW_WIDTH = SCREEN_WIDTH - PADDING * 2;
    private static final int WINDOW_HEIGHT = SCREEN_HEIGHT - PADDING * 2;

    private int currentSprites = 10;
    private final int MAX_SPRITES = 1000;
    private final Sprite[] sprites = new Sprite[MAX_SPRITES];
    private final Background background;
    private final GameCanvas canvas;

    public static void main(String[] args) {
        new MainCircles();
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        canvas = new GameCanvas(this);
        setTitle("Circles");
        background = new Background();
        add(canvas);
        setVisible(true);
        initApplication();
    }

    private void initApplication() {
        for (int i = 0; i < currentSprites; i++) {
            sprites[i] = new Ball(canvas);
        }
    }

    private void update(GameCanvas canvas, float deltaTime) {
        for (int i = 0; i < currentSprites; i++) {
            sprites[i].update(canvas, deltaTime);
        }
    }

    private void render(Graphics g) {
        for (int i = 0; i < currentSprites; i++) {
            sprites[i].render(g);
        }
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        background.colorUpdate();
        canvas.setBackground(background.getColor());
        render(g);
        update(canvas, deltaTime);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (currentSprites < MAX_SPRITES) {
                currentSprites += 1;
                sprites[currentSprites - 1] = new Ball(canvas);
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (currentSprites > 0) currentSprites -= 1;
        }
    }
}
