
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.ThreadLocalRandom;

public class MyRectangle implements Shape {
    private Rectangle rect;

    private Rectangle generateRectangle() {
        double randColor = ThreadLocalRandom.current().nextDouble();
        double randSize = ThreadLocalRandom.current().nextDouble() * 50;
        Color color = new Color(0.5, 0.5, randColor, 1);
        Rectangle rect = new Rectangle(randSize, randSize, color);
        rect.setX(randSize + 200);
        rect.setY(randSize + 200);

        return rect;
    }

    @Override
    public void draw(Pane root) {
        if (rect == null) {
            Rectangle rect = generateRectangle();
            this.rect = rect;
            root.getChildren().add(rect);
        }
    }

    @Override
    public void resize(Pane root) {
        if (rect == null) {
            System.out.println("No rectangle to resize");
        }
        else {
            // Remove the old rectangle
            root.getChildren().remove(rect);

            // Create a new one
            Rectangle rect = generateRectangle();
            this.rect = rect;

            root.getChildren().add(rect);
        }
    }

    @Override
    public String description() {
        return "Rectangle";
    }
}
