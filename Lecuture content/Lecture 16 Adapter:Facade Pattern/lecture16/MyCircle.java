
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

public class MyCircle implements Shape {
    private Circle circle;

    private Circle generateCircle() {
        double randColor = ThreadLocalRandom.current().nextDouble();
        double randSize = ThreadLocalRandom.current().nextDouble() * 50;
        Color color = new Color(randColor, 0.5, 0.5, 1);
        Circle circle = new Circle(
                randSize + 200,
                randSize + 200,
                randSize,
                color);

        return circle;
    }

    @Override
    public void draw(Pane root) {
        if (circle == null) {
            Circle circle = generateCircle();
            this.circle = circle;
            root.getChildren().add(circle);
        }
    }

    @Override
    public void resize(Pane root) {
        if (circle == null) {
            System.out.println("No circle to resize");
        }
        else {
            // Remove the old circle
            root.getChildren().remove(circle);

            // Create a new one
            Circle circle = generateCircle();
            this.circle = circle;
            root.getChildren().add(circle);
        }
    }

    @Override
    public String description() {
        return "Circle";
    }
}
