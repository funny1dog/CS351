package com.example.project_2_new;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class ReactionTime extends MiniGame{

    public ReactionTime(/*String n, String unit, boolean inverse*/) throws IOException {
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
