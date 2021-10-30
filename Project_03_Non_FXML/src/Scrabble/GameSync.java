package Scrabble;
class GameSync {

    // initiate the number of players and player id.
    private int activePlayerId;
    private int numberOfPlayers;

    GameSync(int activePlayerId, int numberOfPlayers) {
        this.activePlayerId = activePlayerId;
        this.numberOfPlayers = numberOfPlayers;
    }

    // switch the turn
    synchronized void switchTurns(){
        activePlayerId = (activePlayerId + 1) % numberOfPlayers;
        notifyAll();
    }
    // wait the turn ends
    synchronized void waitForTurn(int id, Player player) throws InterruptedException{
        while(this.activePlayerId != id) {
            player.getLetterRack().setDisable(true);
            wait();
        }

    }

    String getWhoseTurnLabel() {
        return "Player " + activePlayerId + " 's turn.";
    }

}
