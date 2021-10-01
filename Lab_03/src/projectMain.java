import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class projectMain extends Application {
    public static void main(String[] args) { launch(args);}
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Moving Rectangle Addition");

        // setup basic
        double sizeOfCanvas = 640;
        Pane drawing = new Pane();
        drawing.setStyle("-fx-background-color: Blue");
        drawing.setMinHeight(sizeOfCanvas);
        drawing.setMinWidth(sizeOfCanvas);

        //initiate menu
        final String[] menu = new String[1];
        final String[] menuItems = new String[] {"TL2BR" ,"TR2BL"};
        final ChoiceBox options = new ChoiceBox(FXCollections.observableArrayList("TL2BR" ,"TR2BL"));
        options.setValue("CHOOSE PATH");
        options.setLayoutX(sizeOfCanvas/2);
        options.setLayoutY(30);
        options.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                menu[0] = menuItems[newValue.intValue()];
            }
        });
        // initialize button
        final Button startButton= new Button(" start ");
        startButton.setLayoutY(0);
        startButton.setLayoutX(sizeOfCanvas/2);
        drawing.getChildren().addAll(options,startButton);

        // standard scene
        Scene scene = new Scene(drawing, sizeOfCanvas, sizeOfCanvas);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button listener
        startButton.setOnAction(event -> {
            drawing.getChildren().clear();
            project box = new project(drawing, menu[0]);
            box.start();
            drawing.getChildren().addAll(options,startButton);
        });
    }
}