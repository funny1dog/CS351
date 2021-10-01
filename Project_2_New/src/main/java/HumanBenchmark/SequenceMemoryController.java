package HumanBenchmark;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SequenceMemoryController extends MiniGame {

    @FXML
    public Button btn0, btn1, btn2, btn3, btn4,btn5,btn6,btn7,btn8, btnStart, btnReset;
    @FXML public Label lblClick, lblLvl;
    private Scene scene;

    private int totalMouseClick = 0;
    private ArrayList<Button> allButtons = new ArrayList<Button>();
    private ArrayList<Button> newButtons =new ArrayList<Button>();
    private ArrayList<Button> userButtons =new ArrayList<Button>();
    private int lvl;
    private boolean noError = true;

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

    public void setlvl(int lvl) {
        this.lvl = lvl;
    }

    public void setNoError(boolean noError){ this.noError = noError;};


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

    }

    // get all buttons in allbuttons
    public void gameStart(){
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
    }
    // boolean noError = ture;
    // if noError == true:
        // for loop, add i=lvl items into newButtons, allow more items, just use regular random. allow duplication
        // display newButtons in the newButtons, with an order;
        // ask user to clickbuttons, moves = lvl;
        // do an equals() match;
            // if userButton.equalsto(newButtons)
                // lvl++
                // moves =0;
                // clearArray();
            // else
                // noError = false, gameOverpopuop();


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

        lvl = 1;
        setlvl(lvl);
        setNoError(noError);
        setUserButtons(userButtons);
        setNewButtons(newButtons);

        setupCanvas(lvl);
    }


    public void setupCanvas(int lvl){
        Random rand = new Random();
        int randomIndex = rand.nextInt(allButtons.size());
        newButtons.add(allButtons.get(randomIndex));
        System.out.println(newButtons + "\n");

        for (int j = 0; j <newButtons.size(); j++) {
            System.out.print(newButtons.get(j));
            newButtons.get(j).setStyle("-fx-base: #0096FF");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.play();
            int finalJ = j;
            pause.setOnFinished(e -> {
                newButtons.get(finalJ).setStyle(null);
            });

//        }

//        System.out.print("lvl in setupCanvas: "+ lvl + "\n");
//        lvl =1;
//        Random rand = new Random();
//        for (int i = 0; i <lvl; i++){
//            int randomIndex = rand.nextInt(allButtons.size());
//            newButtons.add(allButtons.get(randomIndex));
//        }
//
//        System.out.print(newButtons);
//
//        for (int j = 0; j <lvl; j++){
//            System.out.print(newButtons.get(j));
//            newButtons.get(j).setStyle("-fx-base: #0096FF");
//            PauseTransition pause = new PauseTransition(Duration.seconds(1));
//            int finalJ = j;
//            pause.setOnFinished(e ->{
//                newButtons.get(finalJ).setStyle(null);
//            });
//            pause.play();
//        }

    }


    }


    public void actionBtnReset(ActionEvent actionEvent) {
    }

    public void mouseClickBtn0(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn1(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn2(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn3(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn4(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn5(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn6(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn7(MouseEvent mouseEvent) {
    }

    public void mouseClickBtn8(MouseEvent mouseEvent) {
    }
}
