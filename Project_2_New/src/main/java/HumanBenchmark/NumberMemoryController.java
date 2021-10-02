package HumanBenchmark;


import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;


public class NumberMemoryController extends MiniGame {
    private Scene scene;
    @FXML
    TextField userInputTextField;
    @FXML
    Button btnNext;
    @FXML
    Button btnNew;
    @FXML
    Button btnSubmit;
    @FXML
    Label questionNumber;
    @FXML
    Label userInput;
    @FXML Label userlevel;
    private int generatedNumber;
    private int digitOfNumber= 0;
    private int userinputNumber;

    public NumberMemoryController(/*String n, String unit, boolean inverse*/) throws Exception {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setUserInput(Label label) {
        this.userInput = userInput;
    }

    public void setDigitOfNumber(int digitOfNumber) {this.digitOfNumber = digitOfNumber;}
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
//        Thread timer = new Thread(() -> {
//            Object o = new Object();
//            synchronized(o){
//                try{
//                    o.wait(3000);
//                    visible = false;
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        int num = 0;
//        double rand;
//        for (int i =0; i< digitOfNumber; i++){
//            rand = Math.random()*9;
//            num +=(int)(rand*Math.pow(10,i));
//        }
//        generatedNumber = num;
//        userinputNumber = -1;
//        timer.start();
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

    public int actionBtnNext(ActionEvent actionEvent) {
        digitOfNumber ++;
        System.out.println(digitOfNumber);
        userlevel.setText(String.valueOf(digitOfNumber));
        generatedNumber = generateRandomDigits(digitOfNumber);
        questionNumber.setText(String.valueOf(generatedNumber));
        System.out.println(generatedNumber);

        PauseTransition pause = new PauseTransition(
                Duration.seconds(3)
        );
        pause.setOnFinished(e -> {questionNumber.setText("????");
        });
        pause.play();
        return generatedNumber;

    }

    public int newRound(){
        digitOfNumber ++;
        System.out.println(digitOfNumber);
        userlevel.setText(String.valueOf(digitOfNumber));
        generatedNumber = generateRandomDigits(digitOfNumber);
        questionNumber.setText(String.valueOf(generatedNumber));
        System.out.println(generatedNumber);

        PauseTransition pause = new PauseTransition(
                Duration.seconds(3)
        );
        pause.setOnFinished(e -> {questionNumber.setText("????");
        });
        pause.play();
        return generatedNumber;
    }
    public void actionBtnSubmit(ActionEvent actionEvent) {
        setDigitOfNumber(digitOfNumber);
        setUserInput(userInput);
        userinputNumber = Integer.parseInt(userInputTextField.getText());
        System.out.println(userinputNumber);
        userInput.setText(String.valueOf(userinputNumber));

        if (userinputNumber== generatedNumber ){
            System.out.print("mathces");
        }
        else {
            System.out.print("does not match");
            gameOverPopOut(digitOfNumber);
            digitOfNumber = 0;
        }
    }

    private void gameOverPopOut(int digitOfNumber) {
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Game Over! \n" +
                "Your score is: " + digitOfNumber + " points");
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

    public int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        int p = m + new Random().nextInt(9 * m);
        return p;
    }

    public void menuNewGame(ActionEvent actionEvent) {
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Number Memory \n" +
                "The average person can remember 7 numbers at once. \n" +
                "Can you do more? \n" +
                "Click anywhere to start");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Number Memory Instruction");
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
}
