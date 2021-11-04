
// invoker
public class RemoteControl {
    private Command[] slots = new Command[10];
    private Command recent;

    public void setCommand(int index, Command command) {
        slots[index] = command;
    }

    public void buttonWasPressed(int index) {
        slots[index].execute();
        recent = slots[index];
    }

    public void undoWasPressed() {
        if (recent != null) {
            recent.undo();
        }
    }
}
