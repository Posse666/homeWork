package visible;

import controller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GameMap extends JPanel {

    private static final Random RANDOM = new Random();
    private static final int BORDER_SIZE = 36;
    private static final String USER_ICONS_PATH = "icons/iconX";
    private static final String COMP_ICONS_PATH = "icons/iconO";
    private final GameController gameController;
    private int fieldSize;
    private final ArrayList<ImageIcon> scaledUserIcons = new ArrayList<>();
    private final ArrayList<ImageIcon> scaledCompIcons = new ArrayList<>();
    private JPanel[][] buttonPanels;
    private JButton[][] gameButtons;
    private JPanel gamePanel;
    private CompoundBorder buttonBorder;
    private CompoundBorder buttonPressedBorder;
    private JButton lastButtonPressed;
    private boolean resourceError = false;

    public GameMap(GameController gameController) {
        this.gameController = gameController;
    }

    public void init(int fieldSize) {
        gamePanel = new JPanel();
        this.fieldSize = fieldSize;
        int borderDimension = BORDER_SIZE / fieldSize;
        gamePanel.setLayout(new GridLayout(fieldSize, fieldSize));
        gamePanel.setBackground(Color.GRAY);
        gameButtons = new JButton[fieldSize][fieldSize];
        buttonPanels = new JPanel[fieldSize][fieldSize];
        buttonBorder = new CompoundBorder(new EmptyBorder(borderDimension, borderDimension, borderDimension, borderDimension), new BevelBorder(BevelBorder.RAISED));
        buttonPressedBorder = new CompoundBorder(new EmptyBorder(borderDimension, borderDimension, borderDimension, borderDimension), new BevelBorder(BevelBorder.LOWERED));

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                JButton gameButton = new JButton();
                gameButton.setFocusPainted(false);
                int finalY = y;
                int finalX = x;
                gameButton.addActionListener(e -> jButtonPressed(finalY, finalX));
                gameButton.setBackground(Color.GRAY);
                gameButtons[y][x] = gameButton;
                gamePanel.add(makeButton(gameButton, y, x));
            }
        }

        lastButtonPressed = gameButtons[0][0];

        initScaledIcons(scaledUserIcons, USER_ICONS_PATH);
        initScaledIcons(scaledCompIcons, COMP_ICONS_PATH);
    }

    private JPanel makeButton(JButton gameButton, int y, int x) {
        buttonPanels[y][x] = new JPanel();
        buttonPanels[y][x].setLayout(new BorderLayout());
        buttonPanels[y][x].add(gameButton);
        buttonPanels[y][x].setBorder(buttonBorder);
        buttonPanels[y][x].setBackground(Color.GRAY);
        buttonPanels[y][x].setVisible(true);
        return buttonPanels[y][x];
    }

    private void jButtonPressed(int y, int x) {
        buttonLowered(y, x, gameController.getCurrentChar() != gameController.getFirstPlayerChar());
        gameController.gameFieldButtonPressed(y, x);
    }

    public void buttonLowered(int y, int x, boolean secondPlayerMove) {
        buttonPanels[y][x].setBorder(buttonPressedBorder);
        if (resourceError) {
            String buttonText = String.valueOf(gameController.getFirstPlayerChar());
            if (secondPlayerMove) buttonText = String.valueOf(gameController.getSecondPlayerChar());
            gameButtons[y][x].setFont(new Font("Serif", Font.PLAIN, gameController.getScreenHeight() / fieldSize / 10));
            gameButtons[y][x].setText(buttonText);
        } else {
            ImageIcon icon = scaledUserIcons.get(getRandomIconNumber(scaledUserIcons.size()));
            if (secondPlayerMove) {
                icon = scaledCompIcons.get(getRandomIconNumber(scaledCompIcons.size()));
            }
            gameButtons[y][x].setIcon(icon);
            gameButtons[y][x].setDisabledIcon(icon);
        }
        lastButtonPressed.setBackground(Color.GRAY);
        gameButtons[y][x].setBackground(Color.DARK_GRAY);
        lastButtonPressed = gameButtons[y][x];
        gameButtons[y][x].setEnabled(false);
    }

    private void initScaledIcons(ArrayList<ImageIcon> scaledIcons, String iconsPath) {
        File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if (jarFile.isFile()) {  // Run with JAR file
            JarFile jar;
            try {
                jar = new JarFile(jarFile);
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.contains(iconsPath) && name.endsWith(".png"))
                        try {
                            scaleIcons(scaledIcons, new ImageIcon(ImageIO.read(getClass().getResourceAsStream(name.substring(name.lastIndexOf(iconsPath))))));
                        } catch (Exception e) {
                            gameController.showError(e.toString());
                        }
                }
                jar.close();
                if (scaledIcons.size() == 0) showErrorWindow(jarFile.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // Run with IDE
            URL url = GameMap.class.getResource("/" + this.getClass().getPackage().getName() + "/" + iconsPath);
            try {
                File[] icons = new File(url.toURI()).listFiles();
                if (icons != null) {
                    for (File file : icons) {
                        if (file.toString().endsWith(".png")) {
                            scaleIcons(scaledIcons, new ImageIcon(file.toString()));
                        }
                    }
                }
                if (scaledIcons.size() == 0) showErrorWindow(url.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void scaleIcons(ArrayList<ImageIcon> scaledIcons, ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(gameController.getScreenHeight() / fieldSize / 4, gameController.getScreenHeight() / fieldSize / 4, Image.SCALE_SMOOTH);
        scaledIcons.add(new ImageIcon(newImg));
    }

    private void showErrorWindow(String path) {
        String errorString = "<html><div WIDTH=WINDOW_WIDTH><center>Внимание! Не найдено ресурсов по пути: " + path + ". Будут использованы обычные символы!</center></div></html>";
        gameController.showError(errorString);
        resourceError = true;
    }

    private int getRandomIconNumber(int maxNumber) {
        return RANDOM.nextInt(maxNumber);
    }

    public JPanel getPanel() {
        return gamePanel;
    }
}
