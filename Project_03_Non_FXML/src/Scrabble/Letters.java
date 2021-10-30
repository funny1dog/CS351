package Scrabble;
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

class Letters extends StackPane {

    private Text text; // what actually gets displayed
    private Rectangle rectangle;
    boolean containsLetter = false;
    private Paint originalColor;
    private String bonusText = ""; // denotes bonus text or star
    private Location letterlocation;

    Letters(String displaySting, Paint originalColor, final int row, final int col, GridPane parent) {
        letterlocation = new Location(row, col, parent);
        text = new Text(displaySting);
        bonusText = displaySting;
        rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(originalColor);
        rectangle.setHeight(40);
        rectangle.setWidth(40);
        this.originalColor = originalColor;
        getChildren().addAll(rectangle, text);

        // set the actionlisteners while moving the letter
        setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasString() && !containsLetter) {
                this.originalColor = rectangle.getFill();
                rectangle.setFill(Color.YELLOW);
            }
            event.consume();
        });

        setOnDragExited(event -> {
            if (!containsLetter) {
                rectangle.setFill(originalColor);
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
                db.setDragView(snapshot(null, new WritableImage(41, 41)));
                db.setDragViewOffsetX(35);
                db.setDragViewOffsetY(35);
                ClipboardContent content = new ClipboardContent();
                content.putString(text.getText());
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

    // add letter to letter container
    void addLetter(String character) {
        text.setText(character.toUpperCase());
        rectangle.setFill(Color.GREEN);
        setStyle("-fx-font: 24 arial;");
        containsLetter = true;
        if (this.getParent() instanceof GameBoard) {
            ScrabbleGameLogic.addLetterToRowColOnBoard(character.charAt(0), this);
        }
    }

    // remove letter
    void removeLetter() {
        text.setText(bonusText);
        rectangle.setFill(originalColor);
        containsLetter = false;
        if (bonusText.equals("★")) {
            setStyle("-fx-font: 40 arial;");
        }
        if (this.getParent() instanceof GameBoard) {
            ScrabbleGameLogic.clearSpaceOnBoard(this);
        }
    }

    // get and set text
    String getText() {
        return text.getText().length() > 1 ? "" : text.getText();
    }

    void setText(String str) {
        text.setText(str);
    }

    // position of the word
    class Location {

        private int row;
        private int col;
        private GridPane parent;

        Location(int row, int col, GridPane parentContainer){
            this.row = row;
            this.col = col;
            this.parent = parentContainer;
        }

        int getRow() {
            return row;
        }

        int getCol() {
            return col;
        }
    }

    // get location
    Location getLocation() {
        return letterlocation;
    }

    String getBonusText() {
        return bonusText;
    }

}
