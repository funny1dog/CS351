import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.concurrent.ThreadLocalRandom;

public enum ShapesToChoose {
    CIRCLE, RECTANGLE;

    private final static ThreadLocalRandom rand = ThreadLocalRandom.current();

    private static double randomCoord(double size) {
        return rand.nextDouble(0, size);
    }

    private static double randomSize() {
        return rand.nextDouble(0, 10);
    }

    public Shape toRandomShape(double width, double height) {
        if (this == CIRCLE) {
            return new Circle(randomCoord(width), randomCoord(height), randomSize());
        }
        else {
            return new Rectangle(randomCoord(width), randomCoord(height), randomSize(), randomSize());
        }
    }
}
