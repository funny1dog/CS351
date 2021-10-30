public class Mutex {
    private int activePlayerID;
    private int numberOfPlayers;

    Mutex (int activePlayerID, int numberOfPlayers) {
        this.activePlayerID = activePlayerID;
        this.numberOfPlayers = numberOfPlayers;
    }

    synchronized void switchTurns() {
        activePlayerID = (activePlayerID + 1) % numberOfPlayers;
        notifyAll();
    }

    synchronized void waitForTurn(int id, Player player) throws InterruptedException {
        while(this.activePlayerID != id) {
            player.getLetterRack().setDisable(true);
            wait();
        }
    }

    String getWhoseTurnLabel () {
        return "Player " + activePlayerID + " 's turn.";
    }
}
