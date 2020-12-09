package visible;

import controller.GameController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Settings extends JDialog {

    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 350;
    private static final int MIN_WIN_LENGTH = 3;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 20;
    private static final int PADDING = 10;
    private static final String FIELD_SIZE_PREFIX = "Размер поля: ";
    private static final String WIN_LENGTH_PREFIX = "Условие победы: ";
    private final GameController gameController;
    private JPanel settingsPanel;

    private JRadioButton veryEasy;
    private JRadioButton easy;
    private JRadioButton normal;
    private JRadioButton hard;
    private JRadioButton secondPlayer;
    private JSlider slideWinCount;
    private JSlider slideFieldSize;

    public Settings(GameController gameController) {
        this.gameController = gameController;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Rectangle bounds = gameController.getBoundsForExternalWindow();
        setWindowLocation(bounds);
        setTitle("Настройки новой игры");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createSettingsPanel();
        add(settingsPanel);

        setModal(true);
        setResizable(false);
        setVisible(false);
    }

    private void createSettingsPanel() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(16, 1));
        settingsPanel.setBorder(new EmptyBorder(0, PADDING, 0, PADDING));
        addGameModeSetup();
        settingsPanel.add(splitter());
        addFieldMapControl();
        JButton buttonPlay = new JButton("Начать новую игру!");
        buttonPlay.addActionListener(e -> buttonPlayGameClick());
        settingsPanel.add(buttonPlay);
    }

    private void addGameModeSetup() {
        settingsPanel.add(new JLabel("Выберите режим игры:"));
        veryEasy = new JRadioButton("Очень легко", true);
        easy = new JRadioButton("Легко");
        normal = new JRadioButton("Нормально");
        hard = new JRadioButton("Тяжело");
        secondPlayer = new JRadioButton("Игра на Двоих");
        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(veryEasy);
        gameMode.add(easy);
        gameMode.add(normal);
        gameMode.add(hard);
        gameMode.add(secondPlayer);
        settingsPanel.add(veryEasy);
        settingsPanel.add(easy);
        settingsPanel.add(normal);
        settingsPanel.add(hard);
        settingsPanel.add(splitter());
        settingsPanel.add(secondPlayer);
    }

    private void addFieldMapControl() {
        JLabel labelFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel labelGameWinCount = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);

        slideFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        slideFieldSize.addChangeListener(e -> {
            int currentValue = slideFieldSize.getValue();
            labelFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
            int winDifference = 2;
            if (currentValue < MIN_FIELD_SIZE + winDifference) winDifference = 0;
            if (currentValue > 9) currentValue = 9;
            slideWinCount.setMaximum(currentValue - winDifference);
        });

        slideWinCount = new JSlider(MIN_WIN_LENGTH, MIN_FIELD_SIZE, MIN_FIELD_SIZE);
        slideWinCount.addChangeListener(e -> labelGameWinCount.setText(WIN_LENGTH_PREFIX + slideWinCount.getValue()));

        settingsPanel.add(new JLabel("Выберите размер поля"));
        settingsPanel.add(labelFieldSize);
        settingsPanel.add(slideFieldSize);
        settingsPanel.add(splitter());
        settingsPanel.add(new JLabel("Выберите условие победы"));
        settingsPanel.add(labelGameWinCount);
        settingsPanel.add(slideWinCount);
    }

    private void buttonPlayGameClick() {
        int gameMode;

        if (veryEasy.isSelected()) {
            gameMode = gameController.getVeryEasyGameMode();
        } else if (easy.isSelected()) {
            gameMode = gameController.getEasyGameMode();
        } else if (normal.isSelected()) {
            gameMode = gameController.getNormalGameMode();
        } else if (hard.isSelected()) {
            gameMode = gameController.getHardGameMode();
        } else if (secondPlayer.isSelected()) {
            gameMode = gameController.getTwoPlayersGameMode();
        } else {
            throw new RuntimeException("Unexpected game mode!");
        }

        int fieldSize = slideFieldSize.getValue();
        int gameWinCount = slideWinCount.getValue();
        dispose();
        gameController.settingsWindowPlayButtonPressed(gameMode, fieldSize, gameWinCount);
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

    public void setWindowLocation(Rectangle bounds) {
        int posX = (int) bounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) bounds.getCenterY() - WINDOW_HEIGHT / 2;
        setLocation(posX, posY);
    }

    public int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public int getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}
