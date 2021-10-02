package HumanBenchmark;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AimTrainerController extends MiniGame {

    @FXML
    AnchorPane centerScreen;
    @FXML
    Circle target;
    @FXML Label labelRemaining;
    private int targetHits;
    private long startTime;
    private Scene scene;


    public AimTrainerController(/*String n, String unit, boolean inverse*/) throws Exception {

    }

    //AnchorPane centerScreen = new AnchorPane(target);
    public void mouseClick(MouseEvent mouseEvent) {
        targetHits++;
        labelRemaining.setText("Remaining: " + (30-targetHits));
        if(targetHits ==30) {
            long nanoElapsed = System.nanoTime() - startTime;
            long millisElapsed = TimeUnit.NANOSECONDS.toMillis(nanoElapsed);
            setCurrScore((int) (millisElapsed / 300000));
            System.out.println(getCurrScore());
            targetHits =0;
            gameOverPopUp((millisElapsed/300000));
        }
        else {changeAnchors();}

}

    public void newRound(){
        targetHits = -1;
        targetHits++;
        labelRemaining.setText("Remaining: " + (30-targetHits));
        if(targetHits ==30) {
            long nanoElapsed = System.nanoTime() - startTime;
            long millisElapsed = TimeUnit.NANOSECONDS.toMillis(nanoElapsed);
            setCurrScore((int) (millisElapsed / 300000));
            System.out.println(getCurrScore());
            targetHits =0;
            gameOverPopUp((millisElapsed/300000));
        }
        else {changeAnchors();}

    }

    private void changeAnchors() {
        //centerScreen.getChildren().addAll(target);
        double topRand = Math.random()*200;
        double leftRand = Math.random()*300;
        AnchorPane.setTopAnchor(target, topRand);
        AnchorPane.setLeftAnchor(target, leftRand);
    }

    public void ActionBtnStart(ActionEvent actionEvent) {
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void ActionMenuMainPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loaderMainMenu = new FXMLLoader(getClass().getResource("HumanBenchmark.fxml"));
        try {
            Pane mainBorderPane = loaderMainMenu.load();
            HumanBenchmarkController controller = loaderMainMenu.getController();
            controller.setScene(scene);
            scene.setRoot(mainBorderPane);
            // trying to get back...
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void playGame() {
    }

    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    @Override
    public void instructionsPopUp() {
//        Stage instructionStage = new Stage();
//        Label instructions = new Label ("test");
//        Button startButton = new Button("Start!");
//        BorderPane borderPane = new BorderPane();
//        Scene scene;
//        instructionStage.initModality(Modality.APPLICATION_MODAL);
//        instructionStage.initOwner(getGameStage());
//        instructionStage.setAlwaysOnTop(true);
//        instructionStage.setTitle("Reaction Time Instruction");
//        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
//            instructionStage.close();
//            gameRunning = true;
//            playGame();
//        });
//
//        gameRunning = false;
//        clickReady = false;
//
//        borderPane.setCenter(instructions);
//        borderPane.setBottom(startButton);
//        BorderPane.setAlignment(instructions, Pos.CENTER);
//        BorderPane.setAlignment(startButton, Pos.CENTER);
//        scene = new Scene(borderPane, 300, 200);
//        instructionStage.setScene(scene);
//        instructionStage.show();

    }

    public void menuNewGame(ActionEvent actionEvent) {
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Aim Trainer \n" +
                "Hit 30 targets as quickly as you can, \n" +
                "Click Start to start");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Target Aim Instruction");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            newGameStage.close();
            newRound();
        });

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 200);
        newGameStage.setScene(scene);
        newGameStage.show();

    }

    public void menuAbout(ActionEvent actionEvent) {
        Stage instructionStage = new Stage();
        Label instructions = new Label ("CS351 Homework Project 2 \n" +
                "Human benchmark version 0.1" +
                "Zhibin 'Bing' Hong \n" +
                "hong@unm.edu \n");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        instructionStage.initModality(Modality.APPLICATION_MODAL);
        instructionStage.initOwner(getGameStage());
        instructionStage.setAlwaysOnTop(true);
        instructionStage.setTitle("Reaction Time Instruction");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            instructionStage.close();
        });
        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 200);
        instructionStage.setScene(scene);
        instructionStage.show();
    }

    public void gameOverPopUp(long result){
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Game Over! \n" +
                "Your score is: " + result + " ms!");
        Button startButton = new Button("Try Again!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Game Over");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            newGameStage.close();
            newRound();
        });

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 200);
        newGameStage.setScene(scene);
        newGameStage.show();
    }
}