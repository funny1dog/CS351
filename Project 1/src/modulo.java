import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class modulo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create canvas and element space size
        double Width = 1600;
        double Height = 1200;
        double Spacing = 15;

        // initialize scenes;
        primaryStage.setTitle("Modulo Times Tables Visualization ");
        Canvas canvas1 = new Canvas (Width, Height);
        Pane pane1 = new Pane (canvas1);
        Scene scene1 = new Scene (pane1);
        primaryStage.setScene(scene1);
        primaryStage.show();

        // create buttons and containers

        HBox elementHorizontal = new HBox (Spacing);
        Button BtnStart = new Button ("Start");
        Button BtnStop = new Button ("Stop");
        Button BtnRandom = new Button ("Generate Random Pictures");
        Button BtnJump = new Button ("Jump to");

        VBox elementVertical = new VBox(Spacing);
        elementVertical.setLayoutX (100);
        elementVertical.setLayoutY (200);

        elementHorizontal.getChildren().addAll()

    }
}
