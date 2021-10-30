import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Objects;

class LetterRack extends GridPane {

    private LetterPiece[] letters = new LetterPiece[7];
    // initial letter
    LetterRack() {
        for (int i = 0; i < 7; i++){
            String letter = LetterBag.getRandomChar();
            LetterPiece newLetterContainer = new LetterPiece("", Color.TAN,0, i, this);
            newLetterContainer.addLetter(letter);
            add(newLetterContainer, i, 0);
            letters[i] = newLetterContainer;
        }
        setPadding(new Insets(0,15,15,15));
    }

    //get letter from container
    LetterPiece[] getLetters() {
        return letters;
    }

    // initial empty letter
    boolean isEmpty() {
        for (LetterPiece lp : letters) {
            if (!Objects.equals(lp.getText(), "")) {
                return false;
            }
        }
        return true;
    }
}
