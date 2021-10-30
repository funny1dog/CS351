package Scrabble;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Objects;

class LetterRack extends GridPane {

    private Letters[] letters = new Letters[7];
    // initial letter
    LetterRack() {
        for (int i = 0; i < 7; i++){
            String letter = LetterBag.getRandomFromBagAsString();
            Letters newLetters = new Letters("", Color.TAN,0, i, this);
            newLetters.addLetter(letter);
            add(newLetters, i, 0);
            letters[i] = newLetters;
        }
        setPadding(new Insets(0,15,15,15));
    }

    //get letter from container
    Letters[] getLetters() {
        return letters;
    }

    // initial empty letter
    boolean isEmpty() {
        for (Letters lc : letters) {
            if (!Objects.equals(lc.getText(), "")) {
                return false;
            }
        }
        return true;
    }
}
