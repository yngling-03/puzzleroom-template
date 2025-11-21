package at.edu.c02.puzzleroom;

import at.edu.c02.puzzleroom.commands.CommandFastMove;
import at.edu.c02.puzzleroom.commands.CommandLoad;
import at.edu.c02.puzzleroom.commands.CommandMove;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidArgumentsException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidMoveException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {
    @Test
    public void movePositiveTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        new CommandMove(new String[]{"right"}).execute(gameBoard);

        // Player should now be at 1 step and at column 2 (because they moved right once)
        assertEquals(1, player.getStepCount());
        assertEquals(1, player.getRow());
        assertEquals(2, player.getCol());
    }

    @Test(expected = PuzzleRoomInvalidMoveException.class)
    public void movePositiveTestNegative() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);

        // Moving left should throw an InvalidMoveException
        new CommandMove(new String[]{"left"}).execute(gameBoard);
    }

    @Test(expected = PuzzleRoomInvalidArgumentsException.class)
    public void movePositiveTestNegative2() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);

        // This should throw a PuzzleRoomInvalidArgumentsException
        new CommandMove(new String[]{"invalid"}).execute(gameBoard);
    }
    @Test
    public void fastmoveBehavesLikeTwoMoves() throws Exception {

        GameBoard gb1 = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gb1);
        new CommandMove(new String[]{"right"}).execute(gb1);
        new CommandMove(new String[]{"right"}).execute(gb1);
        Player p1 = gb1.getPlayer();


        GameBoard gb2 = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gb2);
        new CommandFastMove(new String[]{"r", "r"}).execute(gb2);
        Player p2 = gb2.getPlayer();


        assertEquals(p1.getStepCount(), p2.getStepCount());
        assertEquals(p1.getRow(),       p2.getRow());
        assertEquals(p1.getCol(),       p2.getCol());
    }

    @Test(expected = PuzzleRoomInvalidMoveException.class)
    public void fastmoveNegativeTestInvalidMove() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);


        new CommandFastMove(new String[]{"l"}).execute(gameBoard);
    }
    @Test(expected = PuzzleRoomInvalidArgumentsException.class)
    public void fastmoveNegativeTestInvalidArguments() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);

        // "x" ist kein gültiger Bewegungsparameter -> sollte InvalidArguments werfen
        new CommandFastMove(new String[]{"x"}).execute(gameBoard);
    }
}


}
