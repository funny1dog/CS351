
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Client
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Shapes");

        // Later
        HBox controls = new HBox(10);
        //

        VBox buttons = new VBox(10);

        Button draw = new Button("Draw");
        Button resize = new Button("Resize");
        // Later
        Button newNumberButton = new Button("New Number");
        //

        buttons.getChildren().addAll(draw, resize, newNumberButton);

        // Later
        Label someNumber = new Label("0");
        String someNumberLabel = "SomeNumber";
        someNumber.setUserData(someNumberLabel);

        controls.getChildren().addAll(buttons, someNumber);
        //
        Pane root = new Pane(controls);

        Scene scene = new Scene(root, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        Drawing drawing = new Drawing(root);

        drawing.addShape(new MyCircle());
        drawing.addShape(new MyRectangle());
        drawing.addShape(new GeometricShapeAdapter(new Triangle(1, 2, 3)));
        drawing.addShape(new GeometricShapeAdapter(new Rhombus(1, 2)));

        draw.setOnAction(event -> {
            drawing.drawAll();
        });

        resize.setOnAction(event -> {
            drawing.resizeAll();
        });

        // Later
        // Make mistake of using root first
        NewNumber newNumber = new NewNumber(someNumberLabel, controls);
        newNumberButton.setOnAction(event -> {
            newNumber.updateNumber();
        });
    }
}
