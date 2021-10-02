package HumanBenchmark;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class SequenceMemoryController extends MiniGame {

    @FXML
    public Button btn0, btn1, btn2, btn3, btn4,btn5,btn6,btn7,btn8, btnStart, btnReset;
    @FXML public Label lblClick, lblLvl;
    private Scene scene;

    private int totalMouseClick = 0;
    private ArrayList<Button> allButtons = new ArrayList<Button>();
    private ArrayList<Button> newButtons =new ArrayList<Button>();
    private ArrayList<Button> userButtons =new ArrayList<Button>();
    private int move;
    private boolean noError = true;
    private int level;

    public SequenceMemoryController(/*String n, String unit, boolean inverse*/) throws Exception {
        //super(n, unit, inverse);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setAllButtons(ArrayList allButtons) {
        this.allButtons = allButtons;
    }

    public void setNewButtons(ArrayList newButtons) {
        this.newButtons = newButtons;
    }

    public void setUserButtons(ArrayList userButtons) {
        this.userButtons = userButtons;
    }

    public void setMove(int lvl) {
        this.move = lvl;
    }

    public void setLevel(int level){this.level = level;};

    public void setNoError(boolean noError){ this.noError = noError;};

    public void setTotalMouseClick(int totalMouseClick){this.totalMouseClick = totalMouseClick;}

    @Override
    public void playGame() {

    }

    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    @Override
    public void instructionsPopUp() {

    }

    public void actionBtnStart(ActionEvent actionEvent) {
        setAllButtons(allButtons);
        allButtons.add(btn0);
        allButtons.add(btn1);
        allButtons.add(btn2);
        allButtons.add(btn3);
        allButtons.add(btn4);
        allButtons.add(btn5);
        allButtons.add(btn6);
        allButtons.add(btn7);
        allButtons.add(btn8);

        move = 1;
        level = 1;
        setMove(move);
        setLevel(level);
        setNoError(noError);
        setUserButtons(userButtons);
        setNewButtons(newButtons);

        setupCanvas(move);
    }

    public void newRound(){
        setAllButtons(allButtons);
        allButtons.add(btn0);
        allButtons.add(btn1);
        allButtons.add(btn2);
        allButtons.add(btn3);
        allButtons.add(btn4);
        allButtons.add(btn5);
        allButtons.add(btn6);
        allButtons.add(btn7);
        allButtons.add(btn8);

        move = 1;
        level = 1;
        setMove(move);
        setLevel(level);
        setNoError(noError);
        setUserButtons(userButtons);
        setNewButtons(newButtons);

        setupCanvas(move);
    }
    public void gameReset(){
        setAllButtons(allButtons);
        for (int i = 0; i<9; i++) {
            allButtons.get(i).setStyle(null);
        }
        setNewButtons(newButtons);
        setUserButtons(userButtons);
        setTotalMouseClick(totalMouseClick);
        allButtons = new ArrayList<>();
        newButtons = new ArrayList<>();
        userButtons = new ArrayList<>();
        totalMouseClick = 0;
    }

    private SequentialTransition playOneButton(int buttonIndex) {
        PauseTransition setButtonColor = new PauseTransition(Duration.seconds(0));
        setButtonColor.setOnFinished(e -> newButtons.get(buttonIndex).setStyle("-fx-base: #0096FF"));

        PauseTransition maintainButtonColor = new PauseTransition(Duration.millis(500));
        maintainButtonColor.setOnFinished(e -> newButtons.get(buttonIndex).setStyle(null));

        PauseTransition addDelay = new PauseTransition(Duration.millis(500));
        return new SequentialTransition(setButtonColor, maintainButtonColor, addDelay);
    }

    public void setupCanvas(int lvl){
        Random rand = new Random();
        int randomIndex = rand.nextInt(allButtons.size());
        newButtons.add(allButtons.get(randomIndex));
        System.out.println(newButtons + "\n");
        String btnID = newButtons.get(0).getId();
        System.out.println("this is btnID: " + btnID );

        SequentialTransition[] steps = new SequentialTransition[newButtons.size()];

        for (int j = 0; j < newButtons.size(); ++j) {
            steps[j] = playOneButton(j);
        }

        SequentialTransition all = new SequentialTransition(steps);
        all.play();
    }

    private void match() {
        setMove(move);
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        setNoError(noError);
        setUserButtons(userButtons);
        setNewButtons(newButtons);
        setAllButtons(allButtons);

        if (totalMouseClick ==1 && level == 1) {
            if (userButtons.equals(newButtons)) {
                System.out.println("lvl1 passed");
                setupCanvas(1);
                userButtons = new ArrayList<>();
                totalMouseClick=0;

                level++;
                lblLvl.setText("Level: "+ level);
            }
            else {
                System.out.println("game Over");
                gameOverPopOut(level);
                totalMouseClick = 0;
            }
        }
        if (totalMouseClick >1 && level>1 && totalMouseClick == level){
            if (userButtons.equals(newButtons)){
                System.out.println("lal1+ passed");
                setupCanvas(1);
                userButtons = new ArrayList<>();
                totalMouseClick = 0;
                level++;
                lblLvl.setText("Level: "+ level);
            }
            else {
                System.out.println("gameover");
                gameOverPopOut(level);
                totalMouseClick = 0;
            }
        }



    }

    public void actionBtnReset(ActionEvent actionEvent) {
        setAllButtons(allButtons);
        for (int i = 0; i<9; i++) {
            allButtons.get(i).setStyle(null);
        }
        setNewButtons(newButtons);
        setUserButtons(userButtons);
        setTotalMouseClick(totalMouseClick);
        allButtons = new ArrayList<>();
        newButtons = new ArrayList<>();
        userButtons = new ArrayList<>();
    }

    public void totalClickCount(int t){
        totalMouseClick++;
    }

    private void gameOverPopOut(int lvl) {

        Stage newGameStage = new Stage();
        Label instructions = new Label ("Game Over! \n" +
                "Your final level is: " + lvl + ".");
        Button startButton = new Button("Try Again!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Game Over");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            gameReset();
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

    public void menuNewGame(ActionEvent actionEvent) {
        Stage newGameStage = new Stage();
        Label instructions = new Label (" Sequence Memory \n" +
                "Memorize the Pattern");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Sequence Memory");
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
                "Human benchmark version 0.1 \n" +
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


    public void mouseClickBtn0(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn0.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn0.setStyle(null);
        });
        pause.play();
        userButtons.add(btn0);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn1(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn1.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn1.setStyle(null);
        });
        pause.play();
        userButtons.add(btn1);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn2(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn2.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn2.setStyle(null);
        });
        pause.play();
        userButtons.add(btn2);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn3(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn3.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn3.setStyle(null);
        });
        pause.play();
        userButtons.add(btn3);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn4(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn4.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn4.setStyle(null);
        });
        pause.play();
        userButtons.add(btn4);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn5(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn5.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn5.setStyle(null);
        });
        pause.play();
        userButtons.add(btn5);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn6(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn6.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn6.setStyle(null);
        });
        pause.play();
        userButtons.add(btn6);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn7(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn7.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn7.setStyle(null);
        });
        pause.play();
        userButtons.add(btn7);
        System.out.println("user input: " + btn7);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }

    public void mouseClickBtn8(MouseEvent mouseEvent) {
        setLevel(level);
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn8.setStyle("-fx-base: #008000");
        PauseTransition pause = new PauseTransition(
                Duration.millis(200)
        );
        pause.setOnFinished(e -> {
            btn8.setStyle(null);
        });
        pause.play();
        userButtons.add(btn8);
        System.out.println("user input: " + btn8);
        System.out.println("user" + userButtons);
        System.out.println("new" + newButtons);
        System.out.println("mouseClick" + totalMouseClick);
        System.out.println("level" + level);
        match();
    }
}
