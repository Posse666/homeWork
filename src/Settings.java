import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Settings extends JDialog {

    public static final int WINDOW_WIDTH = 350;
    public static final int WINDOW_HEIGHT = 270;
    private static final int MIN_WIN_LENGTH = 3;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final int PADDING = 10;
    private static final String FIELD_SIZE_PREFIX = "Размер поля: ";
    private static final String WIN_LENGTH_PREFIX = "Условие победы: ";
    private JPanel settingsPanel;
    private MainWindow mainWindow;

    private JRadioButton easy;
    private JRadioButton normal;
    private JRadioButton hard;
    private JSlider slideWinCount;
    private JSlider slideFieldSize;

    Settings(MainWindow mainWindow) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setWindowLocation(mainWindow);
        setTitle("Настройки новой игры");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createSettingsPanel();
        add(settingsPanel);

        setModal(true);
        setResizable(false);
        setVisible(false);
    }

    public void setWindowLocation(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        Rectangle gameWindowBounds = mainWindow.getBounds();
        int posX = (int) gameWindowBounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) gameWindowBounds.getCenterY() - WINDOW_HEIGHT / 2;
        setLocation(posX, posY);
    }

    private void createSettingsPanel(){
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(13, 1));
        settingsPanel.setBorder(new EmptyBorder(0, PADDING, 0, PADDING));
        addGameModeSetup();
        settingsPanel.add(splitter());
        addFieldMapControl();
        JButton btnPlay = new JButton("Начать новую игру!");
        btnPlay.addActionListener(e -> btnPlayGameClick());
        settingsPanel.add(btnPlay);
    }

    private void addGameModeSetup() {
        settingsPanel.add(new JLabel("Выберите режим игры:"));
        easy = new JRadioButton("Легко", true);
        normal = new JRadioButton("Нормально");
        hard = new JRadioButton("Тяжело");
        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(easy);
        gameMode.add(normal);
        gameMode.add(hard);
        settingsPanel.add(easy);
        settingsPanel.add(normal);
        settingsPanel.add(hard);
    }

    private void addFieldMapControl() {
        JLabel lblFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel lblGameWinCount = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);

        slideFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        slideFieldSize.addChangeListener(e -> {
            int currentValue = slideFieldSize.getValue();
            lblFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
            int winDifference = 2;
            if (currentValue < MIN_FIELD_SIZE + winDifference) winDifference = 0;
            slideWinCount.setMaximum(currentValue - winDifference);
        });

        slideWinCount = new JSlider(MIN_WIN_LENGTH, MIN_FIELD_SIZE, MIN_FIELD_SIZE);
        slideWinCount.addChangeListener(e -> lblGameWinCount.setText(WIN_LENGTH_PREFIX + slideWinCount.getValue()));

        settingsPanel.add(new JLabel("Выберите размер поля"));
        settingsPanel.add(lblFieldSize);
        settingsPanel.add(slideFieldSize);
        settingsPanel.add(splitter());
        settingsPanel.add(new JLabel("Выберите условие победы"));
        settingsPanel.add(lblGameWinCount);
        settingsPanel.add(slideWinCount);
    }

    private void btnPlayGameClick() {
        int gameMode;

        if (easy.isSelected()) {
            gameMode = Computer.MODE_EASY;
        } else if (normal.isSelected()) {
            gameMode = Computer.MODE_NORMAL;
        } else if (hard.isSelected()) {
            gameMode = Computer.MODE_HARD;
        } else {
            throw new RuntimeException("Unexpected game mode!");
        }

        int fieldSize = slideFieldSize.getValue();
        int gameWinCount = slideWinCount.getValue();
        dispose();

        mainWindow.startNewGame(gameMode, fieldSize, gameWinCount);
    }

    private JLabel splitter() {
        String splitterChar = "_";
        int splitterLength = 44;
        StringBuilder splitter = new StringBuilder();
        for (int i = 0; i < splitterLength; i++) {
            splitter.append(splitterChar);
        }
        return new JLabel(splitter.toString());
    }
}
