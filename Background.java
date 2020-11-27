import java.awt.*;
import java.util.Random;

public class Background {

    private static final Random RANDOM = new Random();
    private Color color = Color.WHITE;

    private int r = RANDOM.nextInt(256);
    private int g = RANDOM.nextInt(256);
    private int b = RANDOM.nextInt(256);

    private int rDiff = 1;
    private int gDiff = 1;
    private int bDiff = 1;

    private boolean changeDirection (){
        return RANDOM.nextInt(2) > 0;
    }

    public void colorUpdate() {
        color = new Color(r, g, b);
        if (r >= 255 || r <= 0 || g >= 255 || g <= 0 || b >= 255 || b <= 0) {
            if (r >= 255 || r <= 0 || changeDirection()) rDiff = -rDiff;
            if (g >= 255 || g <= 0 || changeDirection()) gDiff = -gDiff;
            if (b >= 255 || b <= 0 || changeDirection()) bDiff = -bDiff;
        }
        r += rDiff;
        g += gDiff;
        b += bDiff;
    }

    public Color getColor() {
        return color;
    }

}
