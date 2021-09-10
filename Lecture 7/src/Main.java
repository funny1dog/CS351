import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application  {
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primartyStage.setTitle("Plus Sign");
        Label numClicks = new Label();
        numClicks.setFont(new Font(size:24));
        numClicks.setAlignment(Pos.Center);

        Pane leftPane = new Pane();
        Pane rightPane = new Pane();

        PlusSign plusSign = new PlusSign (200,100, numClicks, leftPane, rightPane);

    }
}
