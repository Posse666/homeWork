import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GameMap extends JPanel {

    public static final int MODE_EASY = 1;
    public static final int MODE_NORMAL = 2;
    public static final int MODE_HARD = 3;
    private static final Random RANDOM = new Random();
    private static final int BORDER_SIZE = 36;
    private final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
    private static final String USER_ICONS_PATH = "iconX";
    private static final String COMP_ICONS_PATH = "iconO";
    private final int fieldSize;
    private final ArrayList<ImageIcon> scaledUserIcons = new ArrayList<>();
    private final ArrayList<ImageIcon> scaledCompIcons = new ArrayList<>();
    private JPanel[][] buttonPanels;
    private JButton[][] gameButtons;
    private final JPanel gamePanel = new JPanel();
    private CompoundBorder buttonBorder;
    private CompoundBorder buttonPressedBorder;
    private JButton lastButtonPressed;
    private boolean resourceError = false;

    GameMap(int fieldSize, MainWindow mainWindow) {
        this.fieldSize = fieldSize;
        drawGamePanel(mainWindow);
        initScaledIcons(scaledUserIcons, USER_ICONS_PATH, mainWindow);
        initScaledIcons(scaledCompIcons, COMP_ICONS_PATH, mainWindow);
    }

    private void drawGamePanel(MainWindow mainWindow) {
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
        mainWindow.add(gamePanel, BorderLayout.CENTER);
        gamePanel.setVisible(true);

        lastButtonPressed = gameButtons[0][0];
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

    public void jButtonPressed(int y, int x) {
        buttonLowered(y, x, false);
        try {
            GameStart.buttonPressed(y, x);
        } catch (InterruptedException e) {
            System.out.println("Begin of new GAME");
        }
    }

    public void buttonLowered(int y, int x, boolean computer) {
        buttonPanels[y][x].setBorder(buttonPressedBorder);
        if (resourceError) {
            String buttonText = String.valueOf(GameStart.USER_CHAR);
            if (computer) buttonText = String.valueOf(GameStart.COMP_CHAR);
            gameButtons[y][x].setFont(new Font("Serif", Font.PLAIN, MainWindow.SCREEN_HEIGHT / fieldSize / 10));
            gameButtons[y][x].setText(buttonText);
        } else {
            ImageIcon icon = scaledUserIcons.get(getRandomIconNumber(scaledUserIcons.size()));
            if (computer) {
                icon = scaledCompIcons.get(getRandomIconNumber(scaledCompIcons.size()));
            }
            gameButtons[y][x].setIcon(icon);
            gameButtons[y][x].setDisabledIcon(icon);
        }
        lastButtonPressed.setBackground(Color.GRAY);
        if (computer) {
            gameButtons[y][x].setBackground(Color.DARK_GRAY);
            lastButtonPressed = gameButtons[y][x];
        }
        gameButtons[y][x].setEnabled(false);
    }

    private void initScaledIcons(ArrayList<ImageIcon> scaledIcons, String iconsPath, MainWindow mainWindow) {

        if (jarFile.isFile()) {  // Run with JAR file
            JarFile jar;
            try {
                jar = new JarFile(jarFile);
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.contains(iconsPath) && name.endsWith(".png"))
                        scaleIcons(scaledIcons, new ImageIcon(ImageIO.read(getClass().getResourceAsStream(name))));
                }
                jar.close();
                if (scaledIcons.size() == 0) new ErrorWindow(mainWindow, jarFile.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // Run with IDE
            URL url = GameMap.class.getResource("/" + iconsPath);
            try {
                File[] icons = new File(url.toURI()).listFiles();
                if (icons != null) {
                    for (File file : icons) {
                        if (file.toString().endsWith(".png")) {
                            scaleIcons(scaledIcons, new ImageIcon(file.toString()));
                        }
                    }
                }
                if (scaledIcons.size() == 0) new ErrorWindow(mainWindow, url.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void scaleIcons(ArrayList<ImageIcon> scaledIcons, ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(MainWindow.SCREEN_HEIGHT / fieldSize / 4, MainWindow.SCREEN_HEIGHT / fieldSize / 4, Image.SCALE_SMOOTH);
        scaledIcons.add(new ImageIcon(newImg));
    }

    private class ErrorWindow extends JDialog {

        private static final int WINDOW_WIDTH = Settings.WINDOW_WIDTH;
        private static final int WINDOW_HEIGHT = Settings.WINDOW_HEIGHT;

        ErrorWindow(MainWindow mainWindow, String iconsPath) {
            JLabel errorMessage = new JLabel("<html><div WIDTH=WINDOW_WIDTH><center>Внимание! Не найдено ресурсов по пути: " + iconsPath + ". Будут использованы обычные символы!</center></div></html>");
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

            setDefaultCloseOperation(HIDE_ON_CLOSE);
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    dispose();
                }
            });

            add(ok, BorderLayout.SOUTH);
            setModal(true);
            setResizable(false);
            setVisible(true);

            resourceError = true;
        }
    }

    private int getRandomIconNumber(int maxNumber) {
        return RANDOM.nextInt(maxNumber);
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }
}
