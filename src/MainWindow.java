import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final Settings settingsWindow = new Settings(this);
    private JPanel gamePanel = new JPanel();

    MainWindow() {
        draw();
        getContentPane().setBackground(Color.GRAY);

        JButton gameStartButton = new JButton("ИГРАТЬ!");
        gameStartButton.addActionListener(e -> {
            settingsWindow.setWindowLocation(this);
            settingsWindow.setVisible(true);
        });

        JButton gameExitButton = new JButton("ВЫХОД");
        gameExitButton.addActionListener(e -> System.exit(0));

        drawBottomPanel(gameStartButton, gameExitButton);
        setResizable(false);
        setVisible(true);
    }

    private void draw() {
        setSize(SCREEN_HEIGHT / 2, SCREEN_HEIGHT / 2);
        setLocation(SCREEN_WIDTH / 2 - SCREEN_HEIGHT / 4, SCREEN_HEIGHT / 4);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Крестики - Нолики");
    }

    private void drawBottomPanel(JButton btnStart, JButton btnExitGame) {
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));
        panelBottom.add(btnStart);
        panelBottom.add(btnExitGame);
        add(panelBottom, BorderLayout.SOUTH);
    }

    public void startNewGame(int gameMode, int fieldSize, int gameWinCount) {
        GameMap gameMap = new GameMap(fieldSize, this);
        remove(gamePanel);
        gamePanel = gameMap.getGamePanel();
        add(gamePanel);
        revalidate();
        WinScreen winScreen = new WinScreen(this, settingsWindow);
        new GameStart(fieldSize, winScreen, gameMode, gameWinCount, gameMap);
    }

    public void hideGamePanel() {
        gamePanel.setVisible(false);
    }
}
