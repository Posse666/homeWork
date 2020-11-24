import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WinScreen extends JDialog {

    private static final int WINDOW_WIDTH = Settings.WINDOW_WIDTH;
    private static final int WINDOW_HEIGHT = Settings.WINDOW_HEIGHT;
    private final MainWindow mainWindow;
    private final Settings settingsWindow;
    private JLabel lblFinalMessage = new JLabel("");

    WinScreen(MainWindow mainWindow, Settings settingsWindow) {

        this.mainWindow = mainWindow;
        this.settingsWindow = settingsWindow;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setWindowLocation(mainWindow);
        setTitle("ИТОГ");
        add(lblFinalMessage);

        JButton buttonPlayAgain = new JButton("Начать заново!");
        buttonPlayAgain.addActionListener(e -> {
            mainWindow.hideGamePanel();
            dispose();
            showSettings();
        });

        JButton buttonExitGame = new JButton("ВЫХОД");
        buttonExitGame.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                mainWindow.hideGamePanel();
                dispose();
            }
        });

        drawBottomPanel(buttonPlayAgain, buttonExitGame);
        setModal(true);
        setResizable(false);
        setVisible(false);
    }

    private void setWindowLocation(MainWindow mainWindow) {
        Rectangle mainWindowBounds = mainWindow.getBounds();
        int posX = (int) mainWindowBounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) mainWindowBounds.getCenterY() - WINDOW_HEIGHT / 2;
        setLocation(posX, posY);
    }

    private void showSettings() {
        settingsWindow.setWindowLocation(mainWindow);
        settingsWindow.setVisible(true);
    }

    public void showScreen(char currentChar) {
        remove(lblFinalMessage);
        String finalMessage = getWinnerString(currentChar);
        setWindowLocation(mainWindow);
        lblFinalMessage = new JLabel(finalMessage);
        lblFinalMessage.setFont(new Font("Serif", Font.PLAIN, MainWindow.SCREEN_HEIGHT / 30));
        lblFinalMessage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblFinalMessage);
        revalidate();
        setVisible(true);
    }

    private String getWinnerString(char currentChar) {
        String winnerName;
        switch (currentChar) {
            case GameStart.USER_CHAR:
                winnerName = " Игрок";
                break;
            case GameStart.COMP_CHAR:
                winnerName = " Компьютер";
                break;
            default:
                winnerName = "а Дружба";
        }
        return "Победил" + winnerName;
    }

    private void drawBottomPanel(JButton btnPlayAgain, JButton btnExitGame) {
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));
        panelBottom.add(btnPlayAgain);
        panelBottom.add(btnExitGame);
        add(panelBottom, BorderLayout.SOUTH);
    }
}
