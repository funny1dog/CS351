
import javafx.scene.layout.Pane;

// Target
public interface Shape {
    void draw(Pane root);
    void resize(Pane root);
    String description();
}