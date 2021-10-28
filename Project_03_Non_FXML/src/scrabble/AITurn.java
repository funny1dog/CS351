package scrabble;
/**
 *  Jiajun Guo
 *
 *  interface
 *
 */
class AITurn extends Turn {
    AITurn(Mutex mutex, int turnId, Player player) {
        super(mutex, turnId, player);
    }
}
