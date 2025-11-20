package at.edu.c02.puzzleroom.fields;

import at.edu.c02.puzzleroom.Direction;
import at.edu.c02.puzzleroom.GameBoard;

/**
 * An ice field. When entered, the player slides in the travel direction until
 * hitting a wall or a non-ice field.
 */
public class FieldIce extends BaseField {
    public FieldIce(GameBoard gameBoard, char name, int row, int col) {
        super(gameBoard, name, row, col);
    }

    public void initialize() {
    }

    public boolean enterField(Direction direction) {
        // Step onto the ice tile
        gameBoard.getPlayer().walkStep();
        setPlayerPositionToField();

        Field nextField = getNextField(direction);
        boolean couldEnterNext = nextField.enterField(direction);
        if (couldEnterNext) {
            return true;
        }

        // Next field was a wall: stay on current ice tile and count the failed slide attempt
        gameBoard.getPlayer().walkStep();
        return true;
    }

    public boolean leaveField(Direction direction) {
        return true;
    }

    private Field getNextField(Direction direction) {
        return switch (direction) {
            case Up -> gameBoard.getField(row - 1, col);
            case Down -> gameBoard.getField(row + 1, col);
            case Left -> gameBoard.getField(row, col - 1);
            case Right -> gameBoard.getField(row, col + 1);
        };
    }
}
