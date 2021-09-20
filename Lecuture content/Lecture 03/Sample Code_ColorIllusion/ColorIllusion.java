import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class ColorIllusion extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Color Illusion");
        primaryStage.setMaximized(true);

        Pane root = new Pane();
//        BackgroundFill backgroundFill = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
//        Background background = new Background(backgroundFill);
//        root.setBackground(background);
        root.setStyle("-fx-background-color: green");

        Circle inner = new Circle();
        inner.centerXProperty().bind(primaryStage.widthProperty().divide(2));
        inner.centerYProperty().bind(primaryStage.heightProperty().divide(2));
        inner.setRadius(200);
        inner.setFill(Color.RED);

        Circle middle = new Circle();
        middle.centerXProperty().bind(primaryStage.widthProperty().divide(2));
        middle.centerYProperty().bind(primaryStage.heightProperty().divide(2));
        middle.setRadius(5);
        middle.setFill(Color.WHITE);

        Button start = new Button("Start");
        start.setOnAction(event -> {
            root.getChildren().remove(start);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    inner.setRadius(inner.getRadius() - 1);
                }
            };

            Timer timer = new Timer();
            long delay = 50;
            timer.scheduleAtFixedRate(task, delay, delay);
        });

        root.getChildren().addAll(start, inner, middle);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}