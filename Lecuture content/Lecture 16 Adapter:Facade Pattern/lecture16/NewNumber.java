
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.concurrent.ThreadLocalRandom;

public class  NewNumber {
    private Object label;
    private Pane root;

    public NewNumber(Object label,
                     Pane root) {
        this.label = label;
        this.root = root;
    }
    
    public void updateNumber() {
        Label toUpdate = null;

        //System.out.println("root.getChildren() = " + root.getChildren());
        for (Node n : root.getChildren()) {
            //System.out.println("n.getUserData() = " + n.getUserData());
            if (label.equals(n.getUserData())) {
                toUpdate = (Label) n;
            }
        }

        if (toUpdate != null) {
            int rand = ThreadLocalRandom.current().nextInt(0, 1000);
            toUpdate.setText(Integer.toString(rand));
        }
    }
}
