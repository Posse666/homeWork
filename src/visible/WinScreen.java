package visible;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WinScreen extends JDialog {

    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;
    private final GameController gameController;
    private JLabel lblFinalMessage = new JLabel("");

    public WinScreen(GameController gameController) {

        this.gameController = gameController;
        WINDOW_WIDTH = gameController.getWidthForWinScreen();
        WINDOW_HEIGHT = gameController.getHeightForWinScreen();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setWindowLocation();
        setTitle("ИТОГ");
        add(lblFinalMessage);

        JButton buttonPlayAgain = new JButton("Начать заново!");
        buttonPlayAgain.addActionListener(e -> {
            gameController.hideGamePanel();
            dispose();
            gameController.mainWindowPlayButtonPressed();
        });

        JButton buttonExitGame = new JButton("ВЫХОД");
        buttonExitGame.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                gameController.hideGamePanel();
                dispose();
            }
        });

        drawBottomPanel(buttonPlayAgain, buttonExitGame);
        setModal(true);
        setResizable(false);
        setVisible(false);
    }

    private void setWindowLocation() {
        Rectangle mainWindowBounds = gameController.getMainWindowBounds();
        int posX = (int) mainWindowBounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) mainWindowBounds.getCenterY() - WINDOW_HEIGHT / 2;
        setLocation(posX, posY);
    }

    private String getWinnerString(char currentChar) {
        String winnerName;
        if (currentChar == gameController.getUserChar()) winnerName = " Игрок";
        else if (currentChar == gameController.getComputerChar()) winnerName = " Компьютер";
        else winnerName = "а Дружба";
        return "Победил" + winnerName;
    }

    private void drawBottomPanel(JButton btnPlayAgain, JButton btnExitGame) {
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));
        panelBottom.add(btnPlayAgain);
        panelBottom.add(btnExitGame);
        add(panelBottom, BorderLayout.SOUTH);
    }

    public void showScreen(char currentChar) {
        remove(lblFinalMessage);
        String finalMessage = getWinnerString(currentChar);
        setWindowLocation();
        lblFinalMessage = new JLabel(finalMessage);
        lblFinalMessage.setFont(new Font("Serif", Font.PLAIN, gameController.getScreenHeight() / 30));
        lblFinalMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblFinalMessage);
        revalidate();
        setVisible(true);
    }
}
