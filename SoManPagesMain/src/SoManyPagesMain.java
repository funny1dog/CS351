import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SoManyPagesMain extends Application {

    public static void main(String[] args){launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setup main stage
        primaryStage.setTitle("So many Pages");
        List<SomePage> somePages = new ArrayList<>();
        Button start = new Button("Start");

        // setup main pane, with button start in it
        Pane root = new Pane(start);

        // setup main scene, with pane root in it
        Scene scene = new Scene(root, 1000,1000);

        // show scene.
        primaryStage.setScene(scene);
        primaryStage.show();

        //
        SomePage somePage = new SomePage(scene, somePages);
        start.setOnAction(event -> somePage.show());
    }
}
