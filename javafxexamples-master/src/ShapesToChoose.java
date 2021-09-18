import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.concurrent.ThreadLocalRandom;

public enum ShapesToChoose {
    CIRCLE, RECTANGLE;

    private static ThreadLocalRandom rand = ThreadLocalRandom.current();

    private static double randomX(double width) {
        return rand.nextDouble(0, width);
    }

    private static double randomY(double height) {
        return rand.nextDouble(0, height);
    }

    private static double randomSize() {
        return rand.nextDouble(0, 10);
    }

    public Shape toRandomShape(double width, double height) {
        if (this == CIRCLE) {
            return new Circle(randomX(width), randomY(height), randomSize());
        }
        else {
            return new Rectangle(randomX(width), randomY(height),
                                 randomSize(), randomSize());
        }
    }
}
