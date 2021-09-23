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
    @FXML
    private Label LabelAbout;
    @FXML
    private MenuItem MenuClose;
    @FXML
    private Button BtnReactionTime;
    @FXML
    private Button BtnTest;
    private Scene scene;


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    // get to different panels
    public void initialize(URL location, ResourceBundle resources) {

    }

    // menu control
    public void WTFCLOSE(ActionEvent actionEvent) {
        System.out.println("test");
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

    public void ActionMenuPrint(ActionEvent actionEvent){};

    // mini games
    public void ActionBtnReactionTime(ActionEvent actionEvent) {
        FXMLLoader loaderActionReaction = new FXMLLoader(getClass().getResource("ReactionTime.fxml"));
        try {
            Pane newRoot = loaderActionReaction.load();
            ReactionTimeController controller = loaderActionReaction.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionBtnAimTrainer(ActionEvent actionEvent) {
    FXMLLoader loaderActionBtnAimTrainer = new FXMLLoader(getClass().getResource("AimTrainer.fxml"));
        try {
        Pane newRoot = loaderActionBtnAimTrainer.load();
        scene.setRoot(newRoot);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void ActionBtnSequenceMemory(ActionEvent actionEvent) {
        FXMLLoader loaderActionBtnSequenceMemory = new FXMLLoader(getClass().getResource("SequenceMemory.fxml"));
        try {
            Pane newRoot = loaderActionBtnSequenceMemory.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionBtnChimpTest(ActionEvent actionEvent) {
        FXMLLoader loaderActionBtnChimpTest = new FXMLLoader(getClass().getResource("ChimpTest.fxml"));
        try {
            Pane newRoot = loaderActionBtnChimpTest.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ActionBtnVisualMemory(ActionEvent actionEvent) {
        FXMLLoader ActionBtnVisualMemory = new FXMLLoader(getClass().getResource("VisualMemory.fxml"));
        try {
            Pane newRoot = ActionBtnVisualMemory.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionBtnNumberMemory(ActionEvent actionEvent) {
        FXMLLoader loaderActionBtnNumberMemory = new FXMLLoader(getClass().getResource("NumberMemory.fxml"));
        try {
            Pane newRoot = loaderActionBtnNumberMemory.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ActionBtnTypingTest(ActionEvent actionEvent) {
        FXMLLoader ActionBtnTypingTest = new FXMLLoader(getClass().getResource("TypingTest.fxml"));
        try {
            Pane newRoot = ActionBtnTypingTest.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionBtnVerbalMemory(ActionEvent actionEvent) {
        FXMLLoader ActionBtnVerbalMemory = new FXMLLoader(getClass().getResource("VerbalMemory.fxml"));
        try {
            Pane newRoot = ActionBtnVerbalMemory.load();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

