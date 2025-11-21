package at.edu.c02.puzzleroom;

import at.edu.c02.puzzleroom.commands.Command;
import at.edu.c02.puzzleroom.commands.CommandFactory;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomException;
import at.edu.c02.puzzleroom.fields.Field;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class GameBoardImpl implements GameBoard {

    private Player player;
    private boolean quit = false;
    private boolean finished = false;
    private List<List<Field>> fields;

    @Override
    public void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CommandFactory commandFactory = new CommandFactory();

        while (!quit) {
            System.out.print("> ");

            String input = reader.readLine();
            if (input == null) {
                // End of input -> quit game
                quit();
                break;
            }

            input = input.trim().toLowerCase();
            if (input.isEmpty()) {
                // Ignore empty input
                continue;
            }

            String[] inputParts = input.split(" ");
            String commandString = inputParts[0];
            String[] commandArgumentStrings = Arrays.copyOfRange(inputParts, 1, inputParts.length);

            try {
                Command command = commandFactory.createCommand(commandString, commandArgumentStrings);
                command.execute(this);
            } catch (PuzzleRoomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void quit() {
        // Do not change the output here
        System.out.println("Goodbye!");
        quit = true;
    }

    @Override
    public void setFinished() {
        // Do not change the output here
        System.out.println("You finished the maze in " + player.getStepCount() + " steps.");
        this.finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void initialize(List<List<Field>> fields) throws PuzzleRoomException {
        this.player = new PlayerImpl(this);
        this.finished = false;
        this.fields = fields;

        for (List<Field> fieldRow : fields) {
            for (Field field : fieldRow) {
                field.initialize();
            }
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Field getField(int row, int column) {
        return fields.get(row).get(column);
    }

    @Override
    public List<List<Field>> getFields() {
        return fields;
    }
}
