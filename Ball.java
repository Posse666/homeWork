import java.awt.*;
import java.util.Random;

public class Ball extends Sprite {
    private static final Random RANDOM = new Random();

    private final Color color;
    private float vX;
    private float vY;

    Ball(GameCanvas canvas) {
        halfHeight = 20 + (float) (Math.random() * 50f);
        halfWidth = halfHeight;
        color = new Color(RANDOM.nextInt());
        vX = (float) (100f + (Math.random() * 200f));
        if (changeDirection()) vX = -vX;
        vY = (float) (100f + (Math.random() * 200f));
        if (changeDirection()) vY = -vY;
        x = RANDOM.nextInt(canvas.getRight() - (int) halfWidth) - canvas.getLeft() + halfWidth;
        y = RANDOM.nextInt(canvas.getBottom() - (int) halfHeight) - canvas.getTop() + halfHeight;
    }

    private boolean changeDirection (){
        return RANDOM.nextInt(2) > 0;
    }

    @Override
    void update(GameCanvas canvas, float deltaTime) {
        x += vX * deltaTime;
        y += vY * deltaTime;

        if (getLeft() < canvas.getLeft()) {
            setLeft(canvas.getLeft());
            vX = -vX;
        }
        if (getRight() > canvas.getRight()) {
            setRight(canvas.getRight());
            vX = -vX;
        }
        if (getTop() < canvas.getTop()) {
            setTop(canvas.getTop());
            vY = -vY;
        }
        if (getBottom() > canvas.getBottom()) {
            setBottom(canvas.getBottom());
            vY = -vY;
        }
    }

    @Override
    void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int) getLeft(), (int) getTop(),
                (int) getWidth(), (int) getHeight());
    }
}
