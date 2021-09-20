package com.example.project_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class ReactionTime extends Application {
    private Pane pane1;
    private Pane paneReactionTime;
    private Label RTLable1;
    private Rectangle RTrectangle1;

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Human.class.getResource("ReactionTime.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 480);
        stage.setTitle("ReactionTime");
        stage.setScene(scene);
        stage.show();


    }


}
