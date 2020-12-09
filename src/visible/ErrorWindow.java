package visible;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class ErrorWindow extends JDialog {

    public ErrorWindow(GameController gameController, String errorString) {
        int windowWidth = gameController.getWidthForWinScreen();
        int windowHeight = gameController.getHeightForWinScreen();

        JLabel errorMessage = new JLabel(errorString);
        errorMessage.setFont(new Font("Serif", Font.PLAIN, gameController.getScreenHeight() / 60));

        setSize(windowWidth, windowHeight);
        Rectangle bounds = gameController.getBoundsForExternalWindow();
        int posX = (int) bounds.getCenterX() - windowWidth / 2;
        int posY = (int) bounds.getCenterY() - windowHeight / 2;
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
