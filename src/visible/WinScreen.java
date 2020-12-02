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
    private JLabel labelFinalMessage = new JLabel("");

    public WinScreen(GameController gameController) {

        this.gameController = gameController;
        WINDOW_WIDTH = gameController.getWidthForWinScreen();
        WINDOW_HEIGHT = gameController.getHeightForWinScreen();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setWindowLocation();
        setTitle("ИТОГ");
        add(labelFinalMessage);

        JButton buttonPlayAgain = new JButton("Начать заново!");
        buttonPlayAgain.addActionListener(e -> gameController.winScreenPlayAgainButtonPressed());

        JButton buttonExitGame = new JButton("ВЫХОД");
        buttonExitGame.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                gameController.winScreenClosed();
            }
        });

        drawBottomPanel(buttonPlayAgain, buttonExitGame);
        setModal(true);
        setResizable(false);
        setVisible(false);
    }

    private void setWindowLocation() {
        Rectangle bounds = gameController.getBoundsForExternalWindow();
        int posX = (int) bounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) bounds.getCenterY() - WINDOW_HEIGHT / 2;
        setLocation(posX, posY);
    }

    private String getWinnerString(char currentChar, int gameMode) {
        String winnerName = " Игрок";
        if (currentChar == gameController.getEmptyChar()) winnerName = "а Дружба";
        else if (currentChar == gameController.getSecondPlayerChar()) winnerName = (gameMode == gameController.getTwoPlayersGameMode()) ? winnerName + " №2" : " Компьютер";
        else if (gameMode == gameController.getTwoPlayersGameMode()) winnerName += " №1";
        return "Победил" + winnerName;
    }

    private void drawBottomPanel(JButton btnPlayAgain, JButton btnExitGame) {
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));
        panelBottom.add(btnPlayAgain);
        panelBottom.add(btnExitGame);
        add(panelBottom, BorderLayout.SOUTH);
    }

    public void showScreen(char currentChar, int gameMode) {
        remove(labelFinalMessage);
        String finalMessage = getWinnerString(currentChar, gameMode);
        setWindowLocation();
        labelFinalMessage = new JLabel(finalMessage);
        labelFinalMessage.setFont(new Font("Serif", Font.PLAIN, gameController.getScreenHeight() / 30));
        labelFinalMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelFinalMessage);
        revalidate();
        setVisible(true);
    }
}
