package at.edu.c02.puzzleroom;

import at.edu.c02.puzzleroom.commands.CommandLoad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldTest {
    @Test
    public void startField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // We're using CommandLoad here - therefore it's not a full unit test, but allows us to test things easier
        // without having to duplicate the loading logic.
        // You will often find these constructs in "real life" applications (especially if tests were added later), when it's hard
        // to isolate everything.
        new CommandLoad(new String[]{"src/test/resources/startField.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start in 2nd row, 1st column
        assertEquals(2, player.getRow());
        assertEquals(1, player.getCol());
    }

    @Test
    public void pathField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start at 0 steps
        assertEquals(0, player.getStepCount());

        // Moving right should be possible, since there is a path field
        boolean success = player.moveRight();
        assertTrue(success);

        // Player should now be at 1 step
        assertEquals(1, player.getStepCount());
    }

    @Test
    public void wallField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start at 0 steps
        assertEquals(0, player.getStepCount());

        // Moving left should not be possible, since there is a wall field
        boolean success = player.moveLeft();
        assertFalse(success);

        // Player should still be at 0 steps
        assertEquals(0, player.getStepCount());
    }

    @Test
    public void finishField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Game should not be finished after loading
        assertFalse(gameBoard.isFinished());
        player.moveRight();
        // Game should still not be finished after moving right once
        assertFalse(gameBoard.isFinished());
        player.moveRight();
        // Game should be finished after moving right twice
        assertTrue(gameBoard.isFinished());
    }

    @Test
    public void oneWayField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/oneWay.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start at 0 steps
        assertEquals(0, player.getStepCount());


        boolean success = player.moveRight();
        assertTrue(success);

        boolean success1 = player.moveUp();
        assertTrue(success);

        boolean success2 = player.moveLeft();
        assertFalse(success2);

        // Player should now be at 1 step
        assertEquals(2, player.getStepCount());
    }

    @Test
    public void iceFieldSlidesUntilPath() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/iceSimple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        boolean firstMove = player.moveRight();
        assertTrue(firstMove);
        // Slide over two ice fields onto a path (3 steps total)
        assertEquals(3, player.getStepCount());
        assertEquals(1, player.getRow());
        assertEquals(4, player.getCol());

        boolean secondMove = player.moveRight();
        assertTrue(secondMove);
        assertTrue(gameBoard.isFinished());
        assertEquals(4, player.getStepCount());
        assertEquals(1, player.getRow());
        assertEquals(5, player.getCol());
    }

    @Test
    public void iceFieldStopsAtWall() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/iceWall.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        boolean firstMove = player.moveRight();
        assertTrue(firstMove);
        // Step onto ice and count the blocked slide against the wall
        assertEquals(2, player.getStepCount());
        assertEquals(1, player.getRow());
        assertEquals(2, player.getCol());

        boolean secondMove = player.moveRight();
        assertFalse(secondMove);
        // Attempting to move into the wall again should not change steps or position
        assertEquals(2, player.getStepCount());
        assertEquals(1, player.getRow());
        assertEquals(2, player.getCol());
    }
}
