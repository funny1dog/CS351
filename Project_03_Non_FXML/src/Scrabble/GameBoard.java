package Scrabble;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.util.*;

class GameBoard extends GridPane {


    // create a new virtual board with 15*15 size
    static char[][] virtualBoard = new char[15][15];

    // create the grid with score
    static final String wordDoubleModifier = "W*2";
    static final String wordTripleModifier = "W*3";
    static final String letterDoubleModifier = "L*2";
    static final String letterTripleModifier = "L*3";
    static final String STAR = "★";

    // create a new set of words
    static Set<Letters> newlyPopulatedContainers = new HashSet<>();

    //create board
    GameBoard() {
        buildVirtualBoard();
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Letters square;
                if (wordTriplePosition(row, col)) {
                    square = new Letters(wordTripleModifier, Color.LIGHTCORAL, row, col, this);
                } else if (letterDoublePosition(row, col)) {
                    square = new Letters(letterDoubleModifier, Color.LIGHTBLUE, row, col, this);
                } else if (wordDoublePositon(row, col)) {
                    square = new Letters(wordDoubleModifier, Color.LIGHTGREEN, row, col, this);
                } else if (letterTriplePosition(row, col)) {
                    square = new Letters(letterTripleModifier, Color.RED, row, col, this);
                } else if (row == 7 && col == 7) {
                    square = new Letters(STAR, Color.RED, row, col, this);
                    square.setStyle("-fx-font: 40 arial;");
                } else {
                    square = new Letters("", Color.LIGHTGREY, row, col, this);
                }
                add(square, col, row);
                setConstraints(square, col, row);
            }
        }
        setPadding(new Insets(20,20,20,20));
    }

    // followings are determine the position of different modifiers
    private boolean wordDoublePositon(int row, int col) {
        return (row == 1 || row == 2 || row == 3 || row == 4 || row == 10 || row == 11 || row == 12 || row == 13) &&
                (col == row || (col + row == 14));
    }

    private boolean wordTriplePosition(int row, int col) {
        return ((row == 0 || row == 14) && (col == 0 || col == 7 || col == 14)) ||
                (row == 7 && (col == 0 || col == 14));
    }

    private boolean letterDoublePosition(int row, int col) {
        return (row == 0 && col == 3 || row == 0 && col == 11 || row == 2 && col == 6 ||
                row == 2 && col == 8 || row == 3 && col == 0 || row == 3 && col == 7 ||
                row == 3 && col == 14 || row == 6 && col == 2 || row == 6 && col == 6 ||
                row == 6 && col == 8 || row == 6 && col == 12 || row == 7 && col == 3 ||
                row == 7 && col == 11 || row == 8 && col == 2 || row == 8 && col == 6 ||
                row == 8 && col == 8 || row == 8 && col == 12 || row == 11 && col == 0 ||
                row == 11 && col == 7 || row == 11 && col == 14 || row == 14 && col == 3 ||
                row == 14 && col == 11);
    }

    private boolean letterTriplePosition(int row, int col) {
        return (row == 1 || row == 5 || row == 9 || row == 13) &&
                (col == 1 || col == 5 || col == 9 || col == 13);
    }

    // create board
    private void buildVirtualBoard() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                virtualBoard[row][col] = ' ';
            }
        }
    }

    // get board
    char[][] getVirtualBoard() {
        return virtualBoard;
    }

    // get the coordinates of the grid
    Letters girdCoordinates (int row, int col) {
        if (row > 14 || col > 14 || row < 0 || col < 0) {
            return null;
        }
        ObservableList<Node> children = getChildren();
        for (Node n : children) {
            if (getColumnIndex(n) == col && getRowIndex(n) == row) {
                return (Letters) n;
            }
        }
        return null;
    }

    Letters getNextRight(Letters.Location location) {
        int row = location.getRow();
        int col = location.getCol() + 1;
        if (col == 15) {
            return ScrabbleGameLogic.getColumnFifteenSentinels(row);
        }
        return girdCoordinates(row, col);
    }

    Letters getNextDown(Letters.Location location) {
        int row = location.getRow() + 1;
        int col = location.getCol();
        if (row == 15) {
            return ScrabbleGameLogic.getRowFifteenSentinels(col);
        }
        return girdCoordinates(row, col);
    }
}
