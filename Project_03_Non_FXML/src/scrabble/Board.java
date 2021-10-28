package scrabble;
/**
 *  Jiajun Guo
 *
 *  This class is for board
 *
 */
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.*;


class Board extends GridPane {

    static Set<LetterContainer> newlyPopulatedContainers = new HashSet<>();

    static char[][] virtualBoard = new char[15][15];
    // create the grid with score
    static final String TRIPLE_WORD_SCORE = "Triple\nWord\nScore";
    static final String DOUBLE_LETTER_SCORE = "Double\n Letter\n Score";
    static final String DOUBLE_WORD_SCORE = "Double\n Word\n Score";
    static final String TRIPLE_LETTER_SCORE = "Triple\nLetter\nScore";
    static final String STAR = "★";
    //create board
    Board() {
        buildVirtualBoard();
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                LetterContainer square;
                if (isCheckIfTripleWordScoreCoordinates(row, col)) {
                    square = new LetterContainer(TRIPLE_WORD_SCORE, Color.INDIANRED, row, col, this);
                } else if (checkIfDoubleLetterScoreCoordinates(row, col)) {
                    square = new LetterContainer(DOUBLE_LETTER_SCORE, Color.LIGHTBLUE, row, col, this);
                } else if (checkIfDoubleWordScoreCoordinates(row, col)) {
                    square = new LetterContainer(DOUBLE_WORD_SCORE, Color.SALMON, row, col, this);
                } else if (isCheckIfTripleLetterScoreCoordinates(row, col)) {
                    square = new LetterContainer(TRIPLE_LETTER_SCORE, Color.DEEPSKYBLUE, row, col, this);
                } else if (row == 7 && col == 7) {
                    square = new LetterContainer(STAR, Color.SALMON, row, col, this);
                    square.setStyle("-fx-font: 40 arial;");
                } else {
                    square = new LetterContainer("", Color.TAN, row, col, this);
                }
                add(square, col, row);
                setConstraints(square, col, row);
            }
        }
        setPadding(new Insets(0,15,0,15));
    }

    private boolean isCheckIfTripleLetterScoreCoordinates(int row, int col) {
        return (row == 1 || row == 5 || row == 9 || row == 13) &&
                (col == 1 || col == 5 || col == 9 || col == 13);
    }

    private boolean checkIfDoubleWordScoreCoordinates(int row, int col) {
        return (row == 1 || row == 2 || row == 3 || row == 4 || row == 10 || row == 11 || row == 12 || row == 13) &&
                (col == row || (col + row == 14));
    }

    private boolean isCheckIfTripleWordScoreCoordinates(int row, int col) {
        return ((row == 0 || row == 14) && (col == 0 || col == 7 || col == 14)) ||
                (row == 7 && (col == 0 || col == 14));
    }

    private boolean checkIfDoubleLetterScoreCoordinates(int row, int col) {
        return (row == 0 && col == 3 || row == 0 && col == 11 || row == 2 && col == 6 ||
                row == 2 && col == 8 || row == 3 && col == 0 || row == 3 && col == 7 ||
                row == 3 && col == 14 || row == 6 && col == 2 || row == 6 && col == 6 ||
                row == 6 && col == 8 || row == 6 && col == 12 || row == 7 && col == 3 ||
                row == 7 && col == 11 || row == 8 && col == 2 || row == 8 && col == 6 ||
                row == 8 && col == 8 || row == 8 && col == 12 || row == 11 && col == 0 ||
                row == 11 && col == 7 || row == 11 && col == 14 || row == 14 && col == 3 ||
                row == 14 && col == 11);
    }

    private void buildVirtualBoard() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                virtualBoard[row][col] = ' ';
            }
        }
    }
    // print Board
    static void printBoard() {
        if (!GameManager.containersWithCommittedLetters.isEmpty()) {
            System.out.println("-------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------");
            for (int row = 0; row < 15; row++) {
                System.out.print("|    ");
                for (int col = 0; col < 15; col++) {
                    System.out.print(virtualBoard[row][col] + "    |    ");
                }
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------" +
                        "------------------------------------------------------------------------");
            }
        }
    }

    private static char[][] transposeBoard(char [][] board){
        char[][] transposed = new char[board[0].length][board.length];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                transposed[j][i] = board[i][j];
        return transposed;
    }

    char[][] getVirtualBoard() {
        return virtualBoard;
    }

    LetterContainer getRefToSquareByRowColumn(int row, int col) {
        if (row > 14 || col > 14 || row < 0 || col < 0) {
            return null;
        }
        ObservableList<Node> children = getChildren();
        for (Node n : children) {
            if (getColumnIndex(n) == col && getRowIndex(n) == row) {
                return (LetterContainer) n;
            }
        }
        return null;
    }

    LetterContainer getNextRight(LetterContainer.Location location) {
        int row = location.getRow();
        int col = location.getCol() + 1;
        if (col == 15) {
            return GameManager.getColumnFifteenSentinels(row);
        }
        return getRefToSquareByRowColumn(row, col);
    }

    LetterContainer getNextDown(LetterContainer.Location location) {
        int row = location.getRow() + 1;
        int col = location.getCol();
        if (row == 15) {
            return GameManager.getRowFifteenSentinels(col);
        }
        return getRefToSquareByRowColumn(row, col);
    }
}
