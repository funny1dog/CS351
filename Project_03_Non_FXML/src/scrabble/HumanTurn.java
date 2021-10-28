package scrabble;
/**
 *  Jiajun Guo
 *
 *  interface
 *
 */
class HumanTurn extends Turn {
    HumanTurn(Mutex mutex, int turnId, Player player) {
        super(mutex, turnId, player);
    }
}
