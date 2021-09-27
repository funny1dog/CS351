package HumanBenchmark;


import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private boolean visible;
    private boolean roundEnd;

    public NumberMemoryController(/*String n, String unit, boolean inverse*/) throws Exception {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setUserInput(Label label) {
        this.userInput = userInput;
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
    public void actionBtnSubmit(ActionEvent actionEvent) {
        setUserInput(userInput);
        userinputNumber = Integer.parseInt(userInputTextField.getText());
        System.out.println(userinputNumber);
        userInput.setText(String.valueOf(userinputNumber));

        if (userinputNumber== generatedNumber ){
            System.out.print("mathces");
        }
        else {
            System.out.print("does not match");
        }

        //return userinputNumber;


    }

    public void actionBtnNew(ActionEvent actionEvent) {

    }

    public int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        int p = m + new Random().nextInt(9 * m);
        //System.out.println(p);
        return p;
    }



    private int[] compareNumbers() {
        int[] numsCorrect = new int[this.digitOfNumber];
        int loopBound;
        if (this.userinputNumber == 0) {
            loopBound = 1;
        } else {
            loopBound = (int)(Math.log10((double)this.userinputNumber) + 1.0D);
        }

        if (loopBound > this.digitOfNumber) {
            numsCorrect = new int[loopBound];
        }

        int tempNum = this.generatedNumber;
        int tempInput = this.userinputNumber;

        for(int i = 0; i < loopBound; ++i) {
            if (i >= this.digitOfNumber) {
                numsCorrect[i] = 0;
            } else if (tempNum % 10 == tempInput % 10) {
                numsCorrect[i] = 1;
            } else {
                numsCorrect[i] = 0;
            }

            tempNum /= 10;
            tempInput /= 10;
        }

        return numsCorrect;
    }
}
//    public void updateLabel() {
//    }

//    private int getInput() {
////        int userinputNumber = -1;
////        if(userInputTextField.getText().length()!=0){
////            try {userinputNumber = Integer.parseInt(userInputTextField.getText()); }
////            catch (NumberFormatException e) {gameOverPopup();}
////        }
////        return userinputNumber;
////    }
//
//    private void gameOverPopup() {
//    }
//}

//    public void actionBtnNew(ActionEvent actionEvent) {
////        AnimationTimer a = new AnimationTimer() {
////            @Override
////            public void handle(long now) {
////                updateLabel();
////            }
////        };
////        a.start();
////
////        Thread timer = new Thread(() -> {
////            Object o = new Object();
////            synchronized(o){
////                try{
////                    o.wait(3000);
////                    visible = false;
////                } catch(InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////        int num = 0;
////        double rand;
////        for (int i =0; i< digitOfNumber; i++){
////            rand = Math.random()*9;
////            num +=(int)(rand*Math.pow(10,i));
////        }
////        generatedNumber = num;
////        userinputNumber = -1;
////        timer.start();
//    }

/*
    public void actionBtnNext(ActionEvent actionEvent) {
        // determine whether number matches
        boolean numberMatches = true;
        for (int i: compareNumbers()){
            if (i==0) {
                numberMatches = false;
                break;
            }
        }
        if (numberMatches){
            if(digitOfNumber ==20){
                setCurrScore(digitOfNumber);
                //gameOverPopUp();
            }
            else{
                digitOfNumber ++;
                visible = true;
            }
        }
        else{setCurrScore(digitOfNumber);}

        // generate numbers
        int num = 0;
        double rand;
        for (int i=0; i<digitOfNumber; i++){
            rand = Math.random()*9;
            num+=(int)(rand*Math.pow(10,i));
        }
        generatedNumber = num;
        System.out.println(num);
    }


 */