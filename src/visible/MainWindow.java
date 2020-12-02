package visible;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

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

    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
