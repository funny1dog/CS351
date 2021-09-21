package com.example.project_2_new;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HumanBenchmark extends Application {
    @Override
    public void start(Stage Primarystage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HumanBenchmark.class.getResource("HumanBenchmark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Button BtnReactionTime = (fxmlLoader.load());

        Primarystage.setTitle("Human Benchmark");
        Primarystage.setScene(scene);
        Primarystage.show();

        //List<MiniGame> miniGames = new ArrayList<>();
        //MiniGame miniGame = new MiniGame(scene, miniGames);
        BtnReactionTime.setOnAction(e -> BtnReactionTime.setText("Test"));
    }

    public static void main(String[] args) {
        launch();
    }

}