package com.example.project_2_new;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class HumanBenchmarkController implements Initializable {
    @FXML private Label LabelAbout;
    @FXML private MenuItem MenuClose;
    @FXML private Button BtnReactionTime;
    @FXML private Button BtnTest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    // Menu works
    public void WTFCLOSE(ActionEvent actionEvent) {
        System.out.println("test");
    }
}

