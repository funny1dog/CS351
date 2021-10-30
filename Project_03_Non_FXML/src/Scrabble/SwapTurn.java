package Scrabble;
public abstract class SwapTurn implements Runnable {

    private final int turnId;
    private final GameSync gameSync;
    private Player player;

    SwapTurn(GameSync gameSync, int turnId, Player player) {
        this.turnId = turnId;
        this.gameSync = gameSync;
        this.player = player;
    }
    //check if it is your turn
    @Override
    public void run(){
        while(true){
            try {
                gameSync.waitForTurn(turnId, player);
                player.getLetterRack().setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
