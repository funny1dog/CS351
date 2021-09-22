package com.example.project_2_new;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class ReactionTimeController extends MiniGame {

    @FXML public Rectangle recMain;
    @FXML public Label lblMain;
    private Scene scene;
    private boolean gameRunning;
    private boolean clickReady;
    private long startTime;


    public ReactionTimeController(/*String n, String unit, boolean inverse*/) {
        //super(n, unit, inverse);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setRecMain(Rectangle recMain){
        this.recMain = recMain;
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

    @Override
    public void playGame() {

    Thread timer = new Thread(() ->{
        Object o = new Object();
        int rand = (int)(Math.random()*5000)+1500;
        synchronized(o){
            try{
                o.wait(rand);
                if(gameRunning){
                    clickReady = true;
                    startTime = System.nanoTime();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    });
    clickReady = false;
        timer.start();
}

    public void mouseClick (MouseEvent mouseEvent){
        System.out.println("test");
        setRecMain(recMain);
        FXMLLoader loadermouseClick = new FXMLLoader(getClass().getResource("ReactionTime.fxml"));
        ReactionTimeController controller = loadermouseClick.getController();
        controller.setRecMain(recMain);
        try {
            Rectangle recMain = loadermouseClick.load();
            recMain.setFill(Color.WHITE);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        try {
            recMain.setFill((Color.WHITE));
        } catch (IOException e) {
            e.printStackTrace();
        }

         */



    }

    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    @Override
    public void instructionsPopUp() {

    }
}
