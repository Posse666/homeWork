package controller;

import model.Computer;
import model.GameConstants;
import model.RowChecker;
import visible.*;

import java.awt.*;
import java.util.Map;

public class GameController {

    private static final char USER_CHAR = GameConstants.USER_CHAR;
    private static final char COMP_CHAR = GameConstants.COMP_CHAR;
    private static final int INDEX_X = GameConstants.INDEX_X;
    private static final int INDEX_Y = GameConstants.INDEX_Y;
    private final MainWindow mainWindow = new MainWindow(this);
    private final Settings settingsWindow = new Settings(this);
    private GameMap gameMap;
    private WinScreen winScreen;
    private RowChecker rowChecker;
    private Computer computer;
    private char[][] cells;

    public void settingsWindowPlayButtonPressed(int gameMode, int fieldSize, int gameWinCount) {
        gameMap = new GameMap(fieldSize, this);
        mainWindow.addGamePanel(gameMap.getGamePanel());
        winScreen = new WinScreen(this);
        GameConstants gameConstants = new GameConstants(fieldSize);
        cells = gameConstants.getCells();
        rowChecker = new RowChecker(this, fieldSize, cells, gameWinCount);
        computer = new Computer(gameMode, fieldSize, gameWinCount, this, cells);
    }

    public void gameFieldButtonPressed(int y, int x) {
        try {
            cells[y][x] = USER_CHAR;
            rowChecker.checkRaw(USER_CHAR);
            int[] compMove = computer.makeMove();
            cells[compMove[INDEX_Y]][compMove[INDEX_X]] = COMP_CHAR;
            rowChecker.checkRaw(COMP_CHAR);
        } catch (InterruptedException e) {
            System.out.println("Begin of new GAME");
        }
    }

    public void showWinScreen(char winnerChar) {
        winScreen.showScreen(winnerChar);
    }

    public void mainWindowPlayButtonPressed() {
        settingsWindow.setWindowLocation(mainWindow.getBounds());
        settingsWindow.setVisible(true);
    }

    public void hideGamePanel() {
        mainWindow.hideMap();
    }

    public void showError(String errorString) {
        new ErrorWindow(this, errorString);
    }

    public Rectangle getMainWindowBounds() {
        return mainWindow.getBounds();
    }

    public int getVeryEasyGameMode() {
        return Computer.MODE_VERY_EASY;
    }

    public int getEasyGameMode() {
        return Computer.MODE_EASY;
    }

    public int getNormalGameMode() {
        return Computer.MODE_NORMAL;
    }

    public int getHardGameMode() {
        return Computer.MODE_HARD;
    }

    public char getUserChar() {
        return GameConstants.USER_CHAR;
    }

    public char getComputerChar() {
        return GameConstants.COMP_CHAR;
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

    public void setComputerMove(int y, int x) {
        gameMap.buttonLowered(y, x, true);
    }

    public Map<Integer, int[]> getUserMoves() {
        return rowChecker.getUserMoves();
    }

    public Map<Integer, int[]> getCompMoves() {
        return rowChecker.getCompMoves();
    }
}
