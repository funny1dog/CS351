
import javafx.scene.layout.Pane;

public class GeometricShapeAdapter implements Shape {
    private GeometricShape adaptee;

    public GeometricShapeAdapter(GeometricShape adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void draw(Pane root) {
        adaptee.drawShape();
    }

    @Override
    public void resize(Pane root) {
        System.out.println("Can't resize");
    }

    @Override
    public String description() {
        if (adaptee instanceof Triangle) {
            return "Triangle";
        }
        else if (adaptee instanceof Rhombus) {
            return "Rhombus";
        }
        else {
            return "Unknown";
        }
    }
}
