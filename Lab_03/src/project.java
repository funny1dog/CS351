import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class project {
    private final Rectangle rectangle;
    private final Pane drawing;
    private final String str;

    double randomNum = new Random().nextInt(40) + 10;

    // instantiate
    public project(Pane drawing, String str) {
        this.drawing = drawing;
        this.str = str;

        rectangle = new Rectangle();
        rectangle.setWidth(randomNum);
        rectangle.setHeight(randomNum);
        rectangle.setFill(randomColor());

        this.drawing.getChildren().add(rectangle);
    }

    // random color
    private static javafx.scene.paint.Color randomColor() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        return new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);
    }

    public void start() {
        TranslateTransition t = new TranslateTransition(Duration.millis(8000),rectangle);

        if (str == "TL2BR") {
            t.setFromX(0);
            t.setFromY(0);
            t.setToX(drawing.getMinWidth()-rectangle.getWidth());
            t.setToY(drawing.getMinHeight()-rectangle.getHeight());

        } else if (str == "TR2BL") {
            t.setFromX(drawing.getMinWidth()-rectangle.getWidth());
            t.setFromY(0);
            t.setToX(0);
            t.setToY(drawing.getMinHeight()-rectangle.getHeight());
        }
        t.setCycleCount(TranslateTransition.INDEFINITE);
        t.setAutoReverse(true);
        t.play();
    }
}
