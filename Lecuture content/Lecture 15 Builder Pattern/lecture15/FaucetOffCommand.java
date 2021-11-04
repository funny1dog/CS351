
// command
public class FaucetOffCommand implements Command {
    private FaucetControl faucet;

    public FaucetOffCommand(FaucetControl faucet) {
        this.faucet = faucet;
    }

    @Override
    public void execute() {
        faucet.closeValve();
    }

    @Override
    public void undo() {
        faucet.openValve();
    }
}
