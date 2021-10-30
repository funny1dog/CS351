import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javax.xml.stream.Location;
import java.util.Locale;

public class LetterPiece extends StackPane {
    private Text LetterPieceText;
    private Rectangle LetterPieceRectangle;
    private Location LetterPieceLocation;
    private Paint LetterPieceColor;
    boolean containsLetter = false;
    private String bonusText = "";

    LetterPiece(String topDisplay, Paint letterPieceColor, final int row, final int col, GridPane parent) {
        LetterPieceText = new Text(topDisplay);
        // setup shape
        LetterPieceRectangle = new Rectangle();
        LetterPieceRectangle.setFill(letterPieceColor);
        LetterPieceRectangle.setStroke(Color.BLACK);
        LetterPieceRectangle.setHeight(40);
        LetterPieceRectangle.setWidth(40);
        this.LetterPieceColor = letterPieceColor;

        getChildren().addAll(LetterPieceRectangle, LetterPieceText);

        // setup animations/listeners while moving pieces
        setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasString() && !containsLetter) {
                this.LetterPieceColor = LetterPieceRectangle.getFill();
                LetterPieceText.setFill(Color.YELLOW);
            }
            event.consume();
        });

        setOnDragExited(event -> {
            if (!containsLetter) {
                LetterPieceRectangle.setFill(LetterPieceColor);
            }
            event.consume();
        });

        setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE &&
                    event.getGestureSource() != event.getGestureTarget()) {
                removeLetter();
            } else {
                addLetter(event.getDragboard().getString());
            }

            event.consume();
        });

        setOnDragDetected(event -> {
            if (containsLetter) {
                Dragboard db = startDragAndDrop(TransferMode.MOVE);
                db.setDragView(snapshot(null, new WritableImage(51, 51)));
                db.setDragViewOffsetX(35);
                db.setDragViewOffsetY(35);
                ClipboardContent content = new ClipboardContent();
                content.putString(LetterPieceText.getText());
                removeLetter();
                db.setContent(content);

                event.consume();
            }
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            event.acceptTransferModes(TransferMode.MOVE);
            if (db.hasString() && !containsLetter) {
                addLetter(db.getString());
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }

    void addLetter(String character) {
        LetterPieceText.setText(character.toUpperCase());
        LetterPieceRectangle.setFill(Color.BLUE);
        setStyle("-fx-font: 12 arial;");
        containsLetter = true;
        if (this.getParent() instanceof GameBoard) {
            ScrabbleMainLogic.addLetterToRowColOnBoard(character.charAt(0), this);
        }
    }

    void removeLetter() {
        LetterPieceText.setText(bonusText);
        LetterPieceRectangle.setFill(LetterPieceColor);
        containsLetter = false;
        if (bonusText.equals("*")) {
            setStyle("-fx-font: 30 arial;");
        }
        if (this.getParent() instanceof GameBoard) {
            ScrabbleMainLogic.clearSpaceOnBoard(this);
        }
    }


    String getText(){
        return LetterPieceText.getText().length() > 1? "" : LetterPieceText.getText();
    }

    void setLetterPieceText(String string) {
        LetterPieceText.setText(string);
    }

    public static void setText(String newLetter) {
    }

    Location getLetterPieceLocation() {
        return LetterPieceLocation;
    }

    String getBonusText() {
        return bonusText;
    }

    class Location {
        private int row;
        private int col;
        private GridPane parent;

        Location (int row, int col, GridPane parentPane) {
            this.row = row;
            this.col = col;
            this.parent = parentPane;
        }

        int getRow(){
            return row;
        }

        int getCol(){
            return col;
        }
    }
}
