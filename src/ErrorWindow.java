import javax.swing.*;
import java.awt.*;

public class ErrorWindow extends JDialog {

    private static final int WINDOW_WIDTH = Settings.WINDOW_WIDTH;
    private static final int WINDOW_HEIGHT = Settings.WINDOW_HEIGHT;

    ErrorWindow(MainWindow mainWindow, String errorString) {
        JLabel errorMessage = new JLabel(errorString);
        errorMessage.setFont(new Font("Serif", Font.PLAIN, MainWindow.SCREEN_HEIGHT / 60));

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Rectangle mainWindowBounds = mainWindow.getBounds();
        int posX = (int) mainWindowBounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) mainWindowBounds.getCenterY() - WINDOW_HEIGHT / 2;
        setLocation(posX, posY);
        setTitle("Внимание!");
        add(errorMessage);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> dispose());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(ok, BorderLayout.SOUTH);
        setModal(true);
        setResizable(false);
        setVisible(true);
    }
}
