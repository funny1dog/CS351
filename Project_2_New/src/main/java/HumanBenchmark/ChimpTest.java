package HumanBenchmark;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ChimpTest extends MiniGame{

    public ChimpTest(/*String n, String unit, boolean inverse*/) throws IOException {
        //super("Reaction Time", "ms", true);

        FXMLLoader fxmlLoader = new FXMLLoader(HumanBenchmark.class.getResource("ReactionTime.fxml"));
        ReactionTimeController controller = fxmlLoader.getController();
        //controller.setRecMain(controller.recMain);


    }

    @Override
    public void playGame() {

    }

    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    @Override
    public void instructionsPopUp() {

    }
}
