package com.example.project_2;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ResourceBundle;

public class ReactionTimeController implements Initializable {

    @FXML public Pane pane1;
    @FXML public Label LblMain;
    @FXML public Rectangle RTrectangle1;
    @FXML public Button BtnStart;
    @FXML public Label RTLabel1;
    public long clickTime;
    public long clockTime;
    public Array resultArray;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public long btnStartGame(ActionEvent actionEvent) {
        RTrectangle1.setFill(Color.DODGERBLUE);
        long clockTime = System.currentTimeMillis();
        LblMain.setText("Get Ready...");
        PauseTransition pause = new PauseTransition(
                Duration.seconds(3)
        );
        pause.setOnFinished(e -> {RTrectangle1.setFill(Color.GREEN);
            System.out.println(clockTime);
        });
        pause.play();
        this.clockTime = clockTime;
        return clockTime;
    }

    public long MouseClick(MouseEvent mouseEvent) {
        long clickTime = System.currentTimeMillis();
        long result = clickTime - clockTime - 3000;
        /*
        if (result <0) {
            LblMain.setText("Make sure click when you see color changes");
        }
         */
        this.clickTime =clickTime;
        System.out.println(clickTime);

        RTLabel1.setText("Your average time is: " + result+ "ms");
        RTrectangle1.setFill(Color.DODGERBLUE);
        LblMain.setText("Reaction time is: " + result+ "ms");
        return result;
    }


}