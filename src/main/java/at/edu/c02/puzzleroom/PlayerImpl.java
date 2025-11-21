package at.edu.c02.puzzleroom;

import at.edu.c02.puzzleroom.fields.Field;

public class PlayerImpl implements Player {

    private final GameBoard gameBoard;
    private int row;
    private int col;
    private int steps;

    public PlayerImpl(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean moveUp() {
        return moveTo(row - 1, col, Direction.Up);
    }

    @Override
    public boolean moveDown() {
        return moveTo(row + 1, col, Direction.Down);
    }

    @Override
    public boolean moveLeft() {
        return moveTo(row, col - 1, Direction.Left);
    }

    @Override
    public boolean moveRight() {
        return moveTo(row, col + 1, Direction.Right);
    }

    private boolean moveTo(int targetRow, int targetCol, Direction direction) {
        if (gameBoard.isFinished()) {
            return false;
        }

        Field currentField = gameBoard.getField(this.row, this.col);
        if (!currentField.leaveField(direction)) {
            return false;
        }

        Field newField = gameBoard.getField(targetRow, targetCol);
        return newField.enterField(direction);
    }

    @Override
    public void walkStep() {
        walkSteps(1);
    }

    @Override
    public void walkSteps(int amount) {
        steps += amount;
        if (steps < 0) {
            steps = 0;
        }
    }

    @Override
    public int getStepCount() {
        return steps;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }
}
``
