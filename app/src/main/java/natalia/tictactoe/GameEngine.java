package natalia.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEngine {

    private static final String EMPTY = "[]";
    public static final String PLAYER_SYMBOL = "X";
    public static final String COMPUTER_SYMBOL = "0";

    private static final Random RANDOM = new Random();
    private List<String> spaces;
    private String currentTurn = PLAYER_SYMBOL;
    private boolean finished;
    private String winner;


    public GameEngine() {
        newGame();
    }

    private List<String> getEmptyList() {
        List<String> emptyList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            emptyList.add(EMPTY);
        }
        return emptyList;
    }

    public void newGame() {
        spaces = new ArrayList<>(getEmptyList());
        finished = false;
        winner = EMPTY;
    }

    public int getSpaceIndex(int line, int column) {
        if (line == 0) {
            return line + column;
        } else if (line == 1) {
            return 3 + column;
        } else {
            return 6 + column;
        }
    }

    public String getWinner() {
        return winner;
    }

    public String getSpaceValue(int line, int column) {
        return spaces.get(getSpaceIndex(line, column));
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean makePlayerMove(int line, int column) {
        int index = getSpaceIndex(line, column);
        if (!isFinished() && spaces.get(index).equals(EMPTY)) {
            spaces.set(index, PLAYER_SYMBOL);
        }
        return validateBoard();
    }

    public boolean makeComputerMove() {
        if (!isFinished()) {
            int spaceIndex;
            do {
                spaceIndex = RANDOM.nextInt(9);
            } while (!spaces.get(spaceIndex).equals(EMPTY));
            spaces.set(spaceIndex, COMPUTER_SYMBOL);
        }
        return validateBoard();
    }

    private void changeTurn() {
        currentTurn = (currentTurn.equals(PLAYER_SYMBOL)) ? COMPUTER_SYMBOL : PLAYER_SYMBOL;
    }

    public boolean validateBoard() {
        if (validate(spaces.get(0), spaces.get(1), spaces.get(2)) || //Line 1
            validate(spaces.get(3), spaces.get(4), spaces.get(5)) || //Line 2
            validate(spaces.get(6), spaces.get(7), spaces.get(8)) || //Line 3
            validate(spaces.get(0), spaces.get(3), spaces.get(6)) || //Column 1
            validate(spaces.get(1), spaces.get(4), spaces.get(7)) || //Column 2
            validate(spaces.get(2), spaces.get(5), spaces.get(8)) || //Column 3
            validate(spaces.get(0), spaces.get(4), spaces.get(8)) || //Diagonal 1
            validate(spaces.get(2), spaces.get(4), spaces.get(6)) //Diagonal 2
            ) {
            finished = true;
            winner = currentTurn;
        }
        changeTurn();
        return finished;
    }

    private boolean validate(String... items) {
        return items[0].equals(currentTurn) &&
                items[1].equals(currentTurn) &&
                items[2].equals(currentTurn);
    }

}