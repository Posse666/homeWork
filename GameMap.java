import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameMap extends JPanel {

    public static final int MODE_HVA = 0;
    public static final int MODE_HVH = 1;
    private static final int BORDER_SIZE = 36;
    private final int fieldSize;
    private JPanel[][] buttonPanels;
    private JButton[][] gameButtons;
    private final JPanel gamePanel = new JPanel();
    private Font buttonFont;
    private CompoundBorder buttonBorder;
    private CompoundBorder buttonPressedBorder;

    GameMap(int fieldSize) {
        this.fieldSize = fieldSize;
        drawGamePanel();
    }

    private void drawGamePanel() {
        int borderDimension = BORDER_SIZE / fieldSize;
        gamePanel.setLayout(new GridLayout(fieldSize, fieldSize));
        gameButtons = new JButton[fieldSize][fieldSize];
        buttonPanels = new JPanel[fieldSize][fieldSize];
        buttonFont = new Font("Serif", Font.BOLD,MainWindow.SCREEN_HEIGHT / 2 / fieldSize / 5);
        buttonBorder = new CompoundBorder(new EmptyBorder(borderDimension, borderDimension, borderDimension, borderDimension), new BevelBorder(BevelBorder.RAISED));
        buttonPressedBorder = new CompoundBorder(new EmptyBorder(borderDimension, borderDimension, borderDimension, borderDimension), new BevelBorder(BevelBorder.LOWERED));

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                JButton gameButton = new JButton("*");
                gameButton.setFocusPainted(false);
                int finalY = y;
                int finalX = x;
                gameButton.addActionListener(e -> jButtonPressed(finalY, finalX));
                gameButtons[y][x] = gameButton;
                gamePanel.add(makeButton(gameButton, y, x));
            }
        }
        add(gamePanel, BorderLayout.CENTER);
        gamePanel.setVisible(true);
    }

    private JPanel makeButton(JButton gameButton, int y, int x) {
        buttonPanels[y][x] = new JPanel();
        buttonPanels[y][x].setLayout(new BorderLayout());
        gameButton.setFont(buttonFont);
        buttonPanels[y][x].add(gameButton);
        buttonPanels[y][x].setBorder(buttonBorder);
        buttonPanels[y][x].setVisible(true);
        return buttonPanels[y][x];
    }

    private void jButtonPressed(int y, int x) {
        String buttonText;
        if (Math.random()>0.5) {
            buttonText = "X"; // TODO заглушка. Тут будет от очереди хода меняться символ
        } else buttonText ="O";
//        if (point.getCurrentChar() == "X") {
//            player.gameFieldShoot(y, x, "X", true);
//            buttonText = "X";
//        } else {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            buttonText = "O";
//        }
        buttonLowered(y, x, buttonText);
    }

    private void buttonLowered(int y, int x, String buttonText) {
        buttonPanels[y][x].setBorder(buttonPressedBorder);
        gameButtons[y][x].setText(buttonText);
        gameButtons[y][x].setEnabled(false);
    }

    public void startNewGame(int gameMode, int gameWinCount) {
        System.out.println("mode = " + gameMode +
                "\n fieldSize = " + fieldSize +
                "\n winLength = " + gameWinCount);
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }
}
