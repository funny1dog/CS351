import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShapesToChooseMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Random Shapes");

        double size = 800;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        BorderPane root = loader.load();

        System.out.println(root.getWidth());

        Scene scene = new Scene(root, size, size);
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println(root.getWidth());
    }
}
