package controller;

import model.*;
import visible.*;

import javax.swing.*;
import java.awt.*;

public class GameController {

    private final char FIRST_PLAYER_CHAR;
    private final char SECOND_PLAYER_CHAR;
    private final char EMPTY_CHAR;
    private final int INDEX_X;
    private final int INDEX_Y;
    private final int TWO_PLAYERS_MODE = 5;
    private final MainWindow mainWindow;
    private final Settings settingsWindow;
    private final GameMap gameMap;
    private final RowChecker rowChecker;
    private final Computer computer;
    private final GameConstants gameConstants;
    private JPanel gamePanel;
    private WinScreen winScreen;
    private int gameMode;
    private char[][] cells;
    private char currentChar;

    public GameController() {
        this.FIRST_PLAYER_CHAR = GameConstants.FIRST_PLAYER_CHAR;
        this.SECOND_PLAYER_CHAR = GameConstants.SECOND_PLAYER_CHAR;
        this.EMPTY_CHAR = GameConstants.EMPTY_CHAR;
        this.INDEX_Y = GameConstants.INDEX_Y;
        this.INDEX_X = GameConstants.INDEX_X;
        gameConstants = new GameConstants();
        mainWindow = new MainWindow(this);
        settingsWindow = new Settings(this);
        gameMap = new GameMap(this);
        rowChecker = new RowChecker(this);
        computer = new Computer();
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.GRAY);
        addGamePanelToMainWindow();
    }

    public void settingsWindowPlayButtonPressed(int gameMode, int fieldSize, int gameWinCount) {
        currentChar = FIRST_PLAYER_CHAR;
        this.gameMode = gameMode;
        gameConstants.fieldInit(fieldSize);
        cells = gameConstants.getCells();
        gameMap.init(fieldSize);
        mainWindow.remove(gamePanel);
        gamePanel = gameMap.getPanel();
        addGamePanelToMainWindow();
        rowChecker.init(fieldSize, cells, gameWinCount);
        if (gameMode != TWO_PLAYERS_MODE) computer.init(gameMode, fieldSize, gameWinCount, cells);
    }

    private void addGamePanelToMainWindow() {
        mainWindow.add(gamePanel, BorderLayout.CENTER);
        gamePanel.setVisible(true);
        mainWindow.revalidate();
    }

    public void gameFieldButtonPressed(int y, int x) {
        try {
            cells[y][x] = currentChar;
            rowChecker.checkRow(currentChar);
            if (currentChar == FIRST_PLAYER_CHAR & gameMode == TWO_PLAYERS_MODE) currentChar = SECOND_PLAYER_CHAR;
            else currentChar = FIRST_PLAYER_CHAR;
            if (gameMode != TWO_PLAYERS_MODE) {
                int[] compMove = computer.makeMove(rowChecker.getCompMoves(), rowChecker.getUserMoves());
                cells[compMove[INDEX_Y]][compMove[INDEX_X]] = SECOND_PLAYER_CHAR;
                setComputerMoveToGameMap(compMove[INDEX_Y], compMove[INDEX_X]);
                rowChecker.checkRow(SECOND_PLAYER_CHAR);
            }
        } catch (InterruptedException e) {
            System.out.println("Begin of new GAME");
        }
    }

    public void mainWindowPlayButtonPressed() {
        settingsWindow.setWindowLocation(getBoundsForExternalWindow());
        settingsWindow.setVisible(true);
    }

    public void showWinScreen(char winnerChar) {
       winScreen = new WinScreen(this);
       winScreen.showScreen(winnerChar, gameMode);
    }

    public void setComputerMoveToGameMap(int y, int x) {
        gameMap.buttonLowered(y, x, true);
    }

    public void showError(String errorString) {
        new ErrorWindow(this, errorString);
    }

    public Rectangle getBoundsForExternalWindow() {
        return mainWindow.getBounds();
    }

    public void winScreenClosed(){
        gamePanel.setVisible(false);
        winScreen.dispose();
    }

    public void winScreenPlayAgainButtonPressed(){
        winScreenClosed();
        mainWindowPlayButtonPressed();
    }

    public int getVeryEasyGameMode() {
        return Computer.getModeVeryEasy();
    }

    public int getEasyGameMode() {
        return Computer.getModeEasy();
    }

    public int getNormalGameMode() {
        return Computer.getModeNormal();
    }

    public int getHardGameMode() {
        return Computer.getModeHard();
    }

    public int getTwoPlayersGameMode() {
        return TWO_PLAYERS_MODE;
    }

    public char getFirstPlayerChar() {
        return FIRST_PLAYER_CHAR;
    }

    public char getSecondPlayerChar() {
        return SECOND_PLAYER_CHAR;
    }

    public char getEmptyChar() {
        return EMPTY_CHAR;
    }

    public int getWidthForWinScreen() {
        return settingsWindow.getWindowWidth();
    }

    public int getHeightForWinScreen() {
        return settingsWindow.getWindowHeight();
    }

    public int getScreenHeight() {
        return mainWindow.getScreenHeight();
    }

    public char getCurrentChar() {
        return currentChar;
    }
}
