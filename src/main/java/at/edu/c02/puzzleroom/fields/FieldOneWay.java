package at.edu.c02.puzzleroom.fields;

import at.edu.c02.puzzleroom.Direction;
import at.edu.c02.puzzleroom.GameBoard;

public class FieldOneWay extends BaseField {
    public FieldOneWay(GameBoard gameBoard, char name, int row, int col) {
        super(gameBoard, name, row, col);
    }

    public void initialize() {
    }

    public boolean enterField(Direction direction) {
        gameBoard.getPlayer().walkStep();
        setPlayerPositionToField();
        return true;
    }

    public boolean leaveField(Direction direction) {
        Direction allowedMove = null;

        if (name == '<')
            allowedMove = Direction.Left;
        else if (name == '^')
            allowedMove = Direction.Up;
        else if (name == '>')
            allowedMove = Direction.Right;
        else if (name == 'v')
            allowedMove = Direction.Down;


        if (direction == allowedMove)
            return true;

        return false;
    }
}

