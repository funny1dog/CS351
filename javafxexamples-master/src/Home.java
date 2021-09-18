import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Home {
    @FXML
    private VBox root;
    @FXML
    private Pane canvas;
    @FXML
    private ComboBox<ShapesToChoose> chooseShape;
    @FXML
    private TextField howManyValue;
    @FXML
    private CheckBox randomColorsCB;

    public void initialize() {
        chooseShape.getItems().addAll(Arrays.asList(ShapesToChoose.values()));
        chooseShape.setValue(ShapesToChoose.CIRCLE);
    }

    public void generateClicked(ActionEvent actionEvent) {
        ShapesToChoose shape = chooseShape.getValue();
        int howMany = Integer.parseInt(howManyValue.getText());
        boolean randomColors = randomColorsCB.isSelected();

        List<Shape> shapesToDraw = makeShapes(shape, howMany, randomColors, canvas.getWidth(), canvas.getHeight());
        canvas.getChildren().addAll(shapesToDraw);
    }

    public void resetClicked(ActionEvent actionEvent) {
        canvas.getChildren().clear();
    }

    private static Color randomColor() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        return new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);
    }

    private List<Shape> makeShapes(ShapesToChoose shape, int howMany, boolean randomColors,
                                   double width, double height) {
        List<Shape> shapes = new ArrayList<>();

        Color color = randomColor();

        for (int i = 0; i < howMany; i++) {
            Shape shapeToAdd = shape.toRandomShape(width, height);
            shapeToAdd.setFill(color);
            shapes.add(shapeToAdd);

            if (randomColors) {
                color = randomColor();
            }
        }
        return shapes;
    }
}
