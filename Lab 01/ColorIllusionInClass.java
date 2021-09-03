import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ColorIllusionInClass extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Color Illusion In Class");

        double size = 500;

        Pane root = new Pane();

        Circle outer = new Circle();
        outer.setCenterX(size / 2);
        outer.setCenterY(size / 2);
        outer.setRadius(size / 5);
        outer.setFill(Color.RED);

        Circle inner = new Circle();
        inner.setCenterX(size / 2);
        inner.setCenterY(size / 2);
        inner.setRadius(size / 100);
        inner.setFill(Color.WHITE);

        Rectangle backgroundRect = new Rectangle(size, size, Color.GREEN);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                outer.setRadius(outer.getRadius()-0.05);
            }
        };

        Button button = new Button("Start");
        button.setOnAction(event -> {
            System.out.println("event = " + event);
            timer.start();
        });

        root.getChildren().addAll(backgroundRect, outer, inner, button);

        Scene scene = new Scene(root, size, size);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
