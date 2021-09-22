package com.example.multiplecontrollertest;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Part1Controller {


    @FXML
    private Button button;

    private MainController mainController;
    //注入MainController
    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    //得到想要的TextArea
    private JFXTextArea getTextArea () {
        return this.mainController.getOutputPane();
    }

    @FXML
    private void onButtonAction() {
        getTextArea().appendText("Fuck me\n");
    }

    @FXML
    private void initialize() {
    }
}