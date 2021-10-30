import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class GameBoard extends GridPane {
    // create new set for letter pieces
    static Set<LetterPiece> newLetterPieceSet = new HashSet<>();

    // create a virtual board beneath the real board
    static char[][] virtualBoard = new char[15][15];

    // create board grid with score Modifier
    static final String DoubleLetterScore = "Double\n Letter\n Score";
    static final String TripleLetterScore = "Triple\n Letter\n Score";
    static final String DoubleWordScore = "Double\n Word\n Score";
    static final String TripleWordScore = "Triple\n Word\n Score";
    static final String Star = "*";

    // create virtual board
    private void buildVirtualBoard() {
        for (int row = 0; row < 15; row++){
            for (int col = 0; col < 15; col++) {
                virtualBoard[row][col] = ' ';
            }
        }
    }

    // create game board
    GameBoard(){
        buildVirtualBoard();
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                LetterPiece square;
                if (isDoubleLetterScoreMatches(row, col)){
                    square = new LetterPiece(DoubleLetterScore, Color.LIGHTBLUE, row, col, this);
                }
                else if (isTripleLetterScoreMatches(row, col)){
                    square = new LetterPiece(TripleLetterScore, Color.CADETBLUE, row, col, this);
                }
                else if (isDoubleWordScoreMatches(row, col)){
                    square = new LetterPiece(DoubleWordScore, Color.PEACHPUFF, row, col, this);
                }
                else if (isTripleWordScoreMatches(row, col)){
                    square = new LetterPiece(TripleWordScore, Color.SALMON, row, col, this);
                }
                else {
                    square = new LetterPiece ("", Color.TAN, row, col, this);
                }
                add (square, col, row);
                setConstraints(square, col, row);
            }
        }
        setPadding(new Insets(0,15,0,15));
    }

    // create special tiles on board based on col/row position
    private boolean isDoubleLetterScoreMatches(int row, int col) {
        return (row == 0 && col == 3 || row == 0 && col == 11 || row == 2 && col == 6 ||
                row == 2 && col == 8 || row == 3 && col == 0 || row == 3 && col == 7 ||
                row == 3 && col == 14 || row == 6 && col == 2 || row == 6 && col == 6 ||
                row == 6 && col == 8 || row == 6 && col == 12 || row == 7 && col == 3 ||
                row == 7 && col == 11 || row == 8 && col == 2 || row == 8 && col == 6 ||
                row == 8 && col == 8 || row == 8 && col == 12 || row == 11 && col == 0 ||
                row == 11 && col == 7 || row == 11 && col == 14 || row == 14 && col == 3 ||
                row == 14 && col == 11);
    }

    private boolean isTripleLetterScoreMatches(int row, int col) {
        return (row == 1 || row == 5 || row == 9 || row ==13) &&
                (row == col);
    }

    private boolean isDoubleWordScoreMatches(int row, int col) {
        return ((row == 1 || row == 2 || row == 3 || row == 4 ||
                row == 10 || row == 11 || row == 12 || row == 13) &&
                (row == col || row + col == 14));
    }

    private boolean isTripleWordScoreMatches(int row, int col) {
        return ((row == 0 || row == 14) && (col == 0 || col == 7 || col == 14)) ||
                (row == 7 && (col == 0 || col == 14));
    }

    char[][] getVirtualBoard(){
        return virtualBoard;
    }

    LetterPiece getRefToSquareByRowColumn(int row, int col) {
        if (row > 14 || col > 14 || row < 0 || col < 0) {
            return null;
        }
        ObservableList<Node> children = getChildren();
        for (Node n : children) {
            if (getColumnIndex(n) == col && getRowIndex(n) == row) {
                return (LetterPiece) n;
            }
        }
        return null;
    }

    LetterPiece getNextRight(LetterPiece.Location location) {
        int row = location.getRow();
        int col = location.getCol() + 1;
        if (col == 15) {
            return ScrabbleMainLogic.getColumnFifteenSentinels(row);
        }
        return getRefToSquareByRowColumn(row, col);
    }

    LetterPiece getNextDown(LetterPiece.Location location) {
        int row = location.getRow() + 1;
        int col = location.getCol();
        if (row == 15) {
            return ScrabbleMainLogic.getRowFifteenSentinels(col);
        }
        return getRefToSquareByRowColumn(row, col);
    }
}
