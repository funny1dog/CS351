
// client
public class RemoteControlTest {
    // Client
    public static void main(String[] args) {
        // Invoker
        RemoteControl remote = new RemoteControl();

        // Receiver
        Light light = new Light();

        // Command
        Command lightOn = new LightOnCommand(light);

        remote.setCommand(0, lightOn);
        remote.buttonWasPressed(0);
        remote.undoWasPressed();
        remote.undoWasPressed();

        // Later
        FaucetControl faucet = new FaucetControl();
        FaucetOffCommand faucetOff = new FaucetOffCommand(faucet);
        remote.setCommand(1, faucetOff);
        remote.buttonWasPressed(1);
    }
}
