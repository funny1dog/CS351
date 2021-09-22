package com.example.project_2_new;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HumanBenchmarkController implements Initializable {
    @FXML private Label LabelAbout;
    @FXML private MenuItem MenuClose;
    @FXML private Button BtnReactionTime;
    @FXML private Button BtnTest;
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Menu works
    public void WTFCLOSE(ActionEvent actionEvent) {
        System.out.println("test");
    }

    public void ActionBtnReactionTime(ActionEvent actionEvent) {
        FXMLLoader loaderActionReaction = new FXMLLoader(getClass().getResource("ReactionTime.fxml"));
        try {
            Pane newRoot = loaderActionReaction.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionMenuMainPage(ActionEvent actionEvent) {
        FXMLLoader loaderMainMenu = new FXMLLoader(getClass().getResource("HumanBenchmark.fxml"));
        try {
            Pane newRoot = loaderMainMenu.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionBtnAimTrainer(ActionEvent actionEvent) {
    }
}

