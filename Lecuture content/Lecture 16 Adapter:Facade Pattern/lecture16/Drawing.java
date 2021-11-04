
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Drawing {
    private final List<Shape> shapes = new ArrayList<>();
    private final Pane root;

    public Drawing(Pane root) {
        this.root = root;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public Pane getRoot() {
        return root;
    }

    public void drawAll() {
        for (Shape shape : shapes) {
            shape.draw(root);
        }
    }

    public void resizeAll() {
        for (Shape shape : shapes) {
            shape.resize(root);
        }
    }
}
