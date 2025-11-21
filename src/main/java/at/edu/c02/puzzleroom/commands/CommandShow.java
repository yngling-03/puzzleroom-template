package at.edu.c02.puzzleroom.commands;

import at.edu.c02.puzzleroom.GameBoard;
import at.edu.c02.puzzleroom.Player;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomNoMazeLoadedException;
import at.edu.c02.puzzleroom.fields.Field;

import java.util.List;

/**
 * This command shows the current board, and replaces the current player position
 * with a `*`. It is automatically executed by a few other commands, e.g. when
 * successfully loading a new maze or after moving.
 * <p>
 * Example usage: `show`
 */
public class CommandShow implements Command {

    @Override
    public void execute(GameBoard gameBoard) throws PuzzleRoomException {
        List<List<Field>> fields = gameBoard.getFields();
        Player player = gameBoard.getPlayer();

        if (fields == null || fields.isEmpty() || player == null) {
            throw new PuzzleRoomNoMazeLoadedException();
        }

        int playerRow = player.getRow();
        int playerCol = player.getCol();

        for (int row = 0; row < fields.size(); row++) {
            List<Field> fieldRow = fields.get(row);
            for (int col = 0; col < fieldRow.size(); col++) {
                if (row == playerRow && col == playerCol) {
                    System.out.print('*');
                } else {
                    Field field = fieldRow.get(col);
                    System.out.print(field.getName());
                }
            }
            System.out.println();
        }
    }
}
