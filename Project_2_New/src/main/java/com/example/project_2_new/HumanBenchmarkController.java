package com.example.project_2_new;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HumanBenchmarkController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}