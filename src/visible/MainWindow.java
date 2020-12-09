package visible;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static final int WIDTH = SCREEN_HEIGHT * 3 / 4;
    private static final int HEIGHT = SCREEN_HEIGHT * 3 / 4;

    public MainWindow(GameController gameController) {
        draw();
        getContentPane().setBackground(Color.GRAY);

        JButton gameStartButton = new JButton("ИГРАТЬ!");
        gameStartButton.addActionListener(e -> gameController.mainWindowPlayButtonPressed());

        JButton gameExitButton = new JButton("ВЫХОД");
        gameExitButton.addActionListener(e -> System.exit(0));

        drawBottomPanel(gameStartButton, gameExitButton);
        setResizable(false);
        setVisible(true);
    }

    private void draw() {
        setSize(WIDTH, HEIGHT);
        setLocation(SCREEN_WIDTH / 2 - WIDTH / 2, SCREEN_HEIGHT / 2 - HEIGHT / 2);
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

    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
