import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlusSign {
    private final Group shapes;
    private final Label numClicksLabel;
    private final Pane leftPane;
    private final Pane rightpane;

    private int numClicks = 0;
    private boolean inLeft = true;


    public PlusSign(Label numClicksLabel, Pane leftPane, Pane rightpane) {
        this.numClicksLabel = numClicksLabel;
        this.leftPane = leftPane;
        this.rightpane = rightpane;
        setNumClicks();
        shapes = makeRects(width, height);
    }

    private void setNumClicks(){
        numClicksLabel.setText(Integer.toString(numClicks));
        numClicks++;
    }

    private void addToNumClicksAndMove(Event event){
        setNumClicks();
        if (inLeft){
            leftPane.getChildren().clear();
            rightpane.getChildren().add(shapes);
        }
        else {
            rightpane.getChildren().remove(shapes);
            leftPane.getChildren().add(shapes);
        }

        inLeft = !inLeft;
    }

    private Group makeRects (double width, double height) {
        Rectangle horizontal = new Rectangle(0, 100, width, height);
        horizontal.setFill(Color.RED);

        Rectangle vertical = new Rectangle(
                horizontal.getX() + (width/2)-(height/2),
                horizontal.getY() + (width/2)-(height/2),
                height, width);
                vertical.setFill(Color.BLUE);

        //horizontal.setOnMouseClicked(event -> { more thinking of functions
        horizontal.setOnMouseClicked(this::addToNumClicksAndMove);

        vertical.setOnMouseClicked(this::addToNumClicksAndMove);

        return new Group


    }
}
