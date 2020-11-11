import javax.swing.*;
import java.awt.*;

public class Settings extends JDialog {

    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 270;
    private static final int MIN_WIN_LENGTH = 3;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final String FIELD_SIZE_PREFIX = "Размер поля: ";
    private static final String WIN_LENGTH_PREFIX = "Условие победы: ";
    private MainWindow mainWindow;

    private JRadioButton humVSAI;
    private JRadioButton humVSHum;
    private JSlider slideWinCount;
    private JSlider slideFieldSize;


    Settings(MainWindow mainWindow) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(mainWindow);
        setTitle("Настройки новой игры");
        setLayout(new GridLayout(12, 1));

        addGameModeSetup();
        add(splitter());
        addFieldMapControl();

        JButton btnPlay = new JButton("Начать новую игру!");
        btnPlay.addActionListener(e -> btnPlayGameClick());

        add(btnPlay);
        setModal(true);
        setResizable(false);
        setVisible(false);
    }

    public void setLocation(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        Rectangle gameWindowBounds = mainWindow.getBounds();
        int posX = (int) gameWindowBounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) gameWindowBounds.getCenterY() - WINDOW_HEIGHT / 2;
        setLocation(posX, posY);
    }

    private void addGameModeSetup() {
        add(new JLabel("Выберите режим игры:"));
        humVSAI = new JRadioButton("Против компьютера", true);
        humVSHum = new JRadioButton("Против человека");
        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(humVSAI);
        gameMode.add(humVSHum);
        add(humVSAI);
        add(humVSHum);
    }

    private void addFieldMapControl() {
        JLabel lblFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel lblGameWinCount = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);

        slideFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        slideFieldSize.addChangeListener(e -> {
            int currentValue = slideFieldSize.getValue();
            lblFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
            slideWinCount.setMaximum(currentValue);
        });

        slideWinCount = new JSlider(MIN_WIN_LENGTH, MIN_FIELD_SIZE, MIN_FIELD_SIZE);
        slideWinCount.addChangeListener(e -> lblGameWinCount.setText(WIN_LENGTH_PREFIX + slideWinCount.getValue()));

        add(new JLabel("Выберите размер поля"));
        add(lblFieldSize);
        add(slideFieldSize);
        add(splitter());
        add(new JLabel("Выберите условие победы"));
        add(lblGameWinCount);
        add(slideWinCount);
    }

    private void btnPlayGameClick() {
        int gameMode;

        if (humVSAI.isSelected()) {
            gameMode = GameMap.MODE_HVA;
        } else if (humVSHum.isSelected()) {
            gameMode = GameMap.MODE_HVH;
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
        int splitterLength = 45;
        StringBuilder splitter = new StringBuilder();
        for (int i = 0; i < splitterLength; i++) {
            splitter.append(splitterChar);
        }
        return new JLabel(splitter.toString());
    }
}
