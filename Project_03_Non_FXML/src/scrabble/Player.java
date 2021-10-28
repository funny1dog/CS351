package scrabble;
/**
 *  Jiajun Guo
 *
 *  Player class
 *
 */
import java.util.HashMap;
import java.util.Map;

abstract class Player {

    private int score;
    private LetterRack letterRack;
    private Map<Move, Integer> playableMoves;


    Player() {
        letterRack = new LetterRack();
        playableMoves = new HashMap<>();
    }

    boolean letterRackContainsLetter(String letter) {
        for (LetterContainer lc : letterRack.getLetters()) {
            if (lc.getText().equalsIgnoreCase(letter)) {
                return true;
            }
        }

        return false;
    }

    /**
     * get letter rack
     * @return letterRack
     */
    LetterRack getLetterRack() {
        return letterRack;
    }

    /**
     * randomly change the letter from player's hand
     */
    void dumpLetters() {
        Character[] oldLetters = new Character[7];
        int i = 0;
        for (LetterContainer letterContainer : letterRack.getLetters()) {
            String letter = letterContainer.getText();
            if (letter.equals("")) {
                return; // bag must be empty
            }
            oldLetters[i] = letter.charAt(0);
            String newLetter = LetterBag.getRandomFromBagAsString();
            if (newLetter != null && !newLetter.equals("")) {
                letterContainer.setText(newLetter);
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

    /**
     * get the score
     * @return score
     */
    int getScore() {
        return score;
    }

    /**
     * upate the score
     * @param score
     */
    void updateScore(int score) {
        this.score += score;
    }

    /**
     * initial the letter for player's hand
     */
    void fillLetterRack() {
        for (LetterContainer letterContainer : letterRack.getLetters()) {
            if (letterContainer.getText().equals("")) {
                String letter = LetterBag.getRandomFromBagAsString();

                if (letter != null && !letter.equals("")) {
                    letterContainer.addLetter(letter);
                }
            }
        }
    }

    LetterContainer removeLetterFromRack(String letter) {
        for (LetterContainer lc : letterRack.getLetters()) {
            if (lc.getText().equalsIgnoreCase(letter)) {
                lc.removeLetter();
                return lc;
            }
        }

        return null;
    }

    void putLetterInRack(String string, LetterContainer lc) {
        lc.addLetter(string);
    }

    void addPlayableMove(Move move, int score) {
        playableMoves.put(move, score);
    }

    /**
     * get where the player moves
     * @return playableMoves
     */
    Map<Move, Integer> getPlayableMoves() {
        return playableMoves;
    }

    /**
     * clear
     */
    void clearPlayableMoves() {
        playableMoves.clear();
    }
}
