package com.example.project_2_new;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HumanBenchmark extends Application {

    @Override
    public void start(Stage Primarystage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HumanBenchmark.class.getResource("HumanBenchmark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        HumanBenchmarkController controller = fxmlLoader.getController();
        controller.setScene(scene);

        Primarystage.setTitle("Human Benchmark");
        Primarystage.setScene(scene);
        Primarystage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}