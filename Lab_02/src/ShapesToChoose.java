import javafx.scene.shape.*;

import java.util.concurrent.ThreadLocalRandom;

public enum ShapesToChoose {
    CIRCLE, RECTANGLE, ELLIPSE, ARC;

    private final static ThreadLocalRandom rand = ThreadLocalRandom.current();

    private static double randomSize() {
        return rand.nextDouble(10);
    }

    public Shape toRandomShape(double width, double height) {
        if (this == CIRCLE) {
            return new Circle(rand.nextDouble(width), rand.nextDouble(height), randomSize());
        }
        else if (this == ELLIPSE) {
            return new Ellipse (rand.nextDouble(width), rand.nextDouble(height), randomSize(),randomSize());
        }
        else if (this == ARC) {
//            return new Arc (rand.nextDouble(width), rand.nextDouble(height),randomSize(),randomSize(),randomSize(),randomSize());
            Arc arc = new Arc (rand.nextDouble(width), rand.nextDouble(height), randomSize(), randomSize(), randomSize(), randomSize());
            arc.setType(ArcType.ROUND);
            return arc;
        }
        else {
            return new Rectangle(rand.nextDouble(width), rand.nextDouble(height), randomSize(), randomSize());
        }
    }
}
