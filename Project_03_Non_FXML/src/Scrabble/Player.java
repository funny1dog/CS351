package Scrabble;
import java.util.HashMap;
import java.util.Map;

abstract class Player {

    private int score;
    private LetterRack letterRack;
    private Map<InGameMove, Integer> playableMoves;

    Player() {
        letterRack = new LetterRack();
        playableMoves = new HashMap<>();
    }

    // check if the letter is in the letterrack
    boolean letterRackContainsLetter(String letter) {
        for (Letters l : letterRack.getLetters()) {
            if (l.getText().equalsIgnoreCase(letter)) {
                return true;
            }
        }
        return false;
    }

    LetterRack getLetterRack() {
        return letterRack;
    }

    // give players new letters
    void randomLetters() {
        Character[] oldLetters = new Character[7];
        int i = 0;
        for (Letters lettersContainer : letterRack.getLetters()) {
            String letter = lettersContainer.getText();
            if (letter.equals("")) {
                return;
            }
            oldLetters[i] = letter.charAt(0);
            String newLetter = LetterBag.getRandomFromBagAsString();
            if (newLetter != null && !newLetter.equals("")) {
                lettersContainer.setText(newLetter);
                i++;
            } else {
                return;
            }
        }

        for (i = 0; i < oldLetters.length; i++) {
            LetterBag.addLetter(oldLetters[i]);
            i++;
        }
    }

    // get scores
    int getScore() {
        return score;
    }

    // update scores
    void updateScore(int score) {
        this.score += score;
    }

    // fill letter rack with new letters
    void fillLetterRack() {
        for (Letters l : letterRack.getLetters()) {
            if (l.getText().equals("")) {
                String letter = LetterBag.getRandomFromBagAsString();
                if (letter != null && !letter.equals("")) {
                    l.addLetter(letter);
                }
            }
        }
    }

    Letters removeLetterFromRack(String letter) {
        for (Letters lc : letterRack.getLetters()) {
            if (lc.getText().equalsIgnoreCase(letter)) {
                lc.removeLetter();
                return lc;
            }
        }
        return null;
    }

    void putLetterInRack(String string, Letters lc) {
        lc.addLetter(string);
    }

    void addPlayableMove(InGameMove inGameMove, int score) {
        playableMoves.put(inGameMove, score);
    }

    Map<InGameMove, Integer> getPlayableMoves() {
        return playableMoves;
    }

    void clearPlayableMoves() {
        playableMoves.clear();
    }
}
