package scrabble;
/**
 *  Jiajun Guo
 *
 *  TileMove Class
 *
 */
class TileMove {

    private boolean isFromRack;
    private LetterContainer destination;
    private Character character;

    //move the letter from container to destination
    TileMove(boolean source, LetterContainer destination, Character character) {
        this.isFromRack = source;
        this.destination = destination;
        this.character = character;
    }
    //
    boolean isFromRack() {
        return isFromRack;
    }
    //get the destination
    LetterContainer getDestination() {
        return destination;
    }
    // get the letter
    Character getCharacter() {
        return character;
    }
}
