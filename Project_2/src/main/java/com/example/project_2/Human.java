package com.example.project_2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Human extends Application {


    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Human.class.getResource("Human.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 480);
        stage.setTitle("Human Benchmark!");
        stage.setScene(scene);
        stage.show();

        Pane pane1 = null;
        Button btnReactionTime = null;
        btnReactionTime.setOnAction(event -> {
            pane1.getChildren().clear();

        });

    }
    
    public static void main(String[] args) {
        launch();
    }
}