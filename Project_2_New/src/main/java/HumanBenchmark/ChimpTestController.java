package HumanBenchmark;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class ChimpTestController extends MiniGame {

    private Scene scene;
    @FXML
    Button
            btn0, btn1, btn2, btn3, btn4, btn5,
            btn6, btn7, btn8, btn9, btn10, btn11,
            btn12, btn13, btn14, btn15, btn16, btn17,
            btn18, btn19, btn20, btn21, btn22, btn23,
            btn24, btn25, btn26, btn27, btn28, btn29,
            btn30, btn31, btn32, btn33, btn34, btn35;
    @FXML
    Button btnStart;
    @FXML
    Label lblClickCount;
    @FXML
    Label lbllives;
    @FXML
    Label lblstreaks;
    @FXML
    Label lblLvl;

    private int totalMouseClick = 0;
    private ArrayList<Button> allButtons = new ArrayList<Button>();
    private ArrayList<Button> newButtons =new ArrayList<Button>();
    private ArrayList<Button> userButtons =new ArrayList<Button>();
    private int lives;
    private int streaks;
    private int lvl;


    public ChimpTestController(/*String n, String unit, boolean inverse*/) throws Exception {
        //super(n, unit, inverse);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setTotalMouseClick(int totalMouseClick) {
        this.totalMouseClick = totalMouseClick;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setlvl(int lvl) {
        this.lvl = lvl;
    }

    public void setStreaks(int streaks) {
        this.streaks = streaks;
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

    public void gameStart(ActionEvent actionEvent) {

        // push all buttons into allButtons arraylist;
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
        allButtons.add(btn9);
        allButtons.add(btn10);
        allButtons.add(btn11);

        allButtons.add(btn12);
        allButtons.add(btn13);
        allButtons.add(btn14);
        allButtons.add(btn15);
        allButtons.add(btn16);
        allButtons.add(btn17);

        allButtons.add(btn18);
        allButtons.add(btn19);
        allButtons.add(btn20);
        allButtons.add(btn21);
        allButtons.add(btn22);
        allButtons.add(btn23);

        allButtons.add(btn24);
        allButtons.add(btn25);
        allButtons.add(btn26);
        allButtons.add(btn27);
        allButtons.add(btn28);
        allButtons.add(btn29);

        allButtons.add(btn30);
        allButtons.add(btn31);
        allButtons.add(btn32);
        allButtons.add(btn33);
        allButtons.add(btn34);
        allButtons.add(btn35);

        // instantiate objects and variables;
        lvl = 1;
        lives = 3;
        streaks = 0;
        setlvl(lvl);
        setLives(lives);
        setStreaks(streaks);
        setNewButtons(newButtons);
        setUserButtons(userButtons);
        setTotalMouseClick(totalMouseClick);

        lblClickCount.setText("0");
        lbllives.setText("Lives: " + lives);
        lblstreaks.setText("Streaks: " + streaks);
        lblLvl.setText("Level: " + lvl);

        setUpCanvas(lvl);
    }
    public void gameStart(int lvl, int lives, int streaks){
        setlvl(lvl);
        setLives(lives);
        setStreaks(streaks);
        System.out.println("Lvl in gameStart: "+ lvl);
        // push all buttons into allButtons arraylist;
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
        allButtons.add(btn9);
        allButtons.add(btn10);
        allButtons.add(btn11);

        allButtons.add(btn12);
        allButtons.add(btn13);
        allButtons.add(btn14);
        allButtons.add(btn15);
        allButtons.add(btn16);
        allButtons.add(btn17);

        allButtons.add(btn18);
        allButtons.add(btn19);
        allButtons.add(btn20);
        allButtons.add(btn21);
        allButtons.add(btn22);
        allButtons.add(btn23);

        allButtons.add(btn24);
        allButtons.add(btn25);
        allButtons.add(btn26);
        allButtons.add(btn27);
        allButtons.add(btn28);
        allButtons.add(btn29);

        allButtons.add(btn30);
        allButtons.add(btn31);
        allButtons.add(btn32);
        allButtons.add(btn33);
        allButtons.add(btn34);
        allButtons.add(btn35);

        // instantiate objects and variables;
        setNewButtons(newButtons);
        setUserButtons(userButtons);
        setTotalMouseClick(totalMouseClick);

        lblClickCount.setText("0");
        lbllives.setText("Lives: " + lives);
        lblstreaks.setText("Streaks: " + streaks);
        lblLvl.setText("Level: " + lvl);

        setUpCanvas(lvl);
    }

    public void setUpCanvas (int lvl){
        //setlvl(lvl);
        System.out.print("lvl in SetupCanva: "+ lvl);
        Collections.shuffle(allButtons);
        setUserButtons(userButtons);
        newButtons = new ArrayList<Button>(allButtons.subList(0, lvl));
        userButtons = new ArrayList<Button>(newButtons.size());
        System.out.println("gamecreated" + newButtons);
        System.out.println("userinput" + userButtons);

        for (int i = 0; i < lvl; i++) {
            newButtons.get(i).setStyle("-fx-base: #0096FF");
            newButtons.get(i).setText(String.valueOf(i + 1));
            PauseTransition pause = new PauseTransition(
                    Duration.seconds(3)
            );
            int finalI = i;
            pause.setOnFinished(e -> {
                newButtons.get(finalI).setText("");
            });
            pause.play();
        }

    }

    public void resetColor(){
        setAllButtons(allButtons);
        for (int i = 0; i < 36; i++) {
            allButtons.get(i).setStyle(null);
            allButtons.get(i).setText("");
        }
        setNewButtons(newButtons);
        setUserButtons(userButtons);
        setTotalMouseClick(totalMouseClick);
        totalMouseClick = 0;
        allButtons = new ArrayList<Button>();
        newButtons = new ArrayList<Button>();
        userButtons = new ArrayList<Button>();
        System.out.println("all:" + allButtons);
        System.out.println("new:" + newButtons);
        System.out.println("user" + userButtons);

    }

    public void resetColor(ActionEvent actionEvent) {
        setAllButtons(allButtons);
        for (int i = 0; i < 36; i++) {
            allButtons.get(i).setStyle(null);
        }
        setNewButtons(newButtons);
        setUserButtons(userButtons);
        setTotalMouseClick(totalMouseClick);
        totalMouseClick = 0;
        allButtons = new ArrayList<Button>();
        newButtons = new ArrayList<Button>();
        userButtons = new ArrayList<Button>();
        System.out.println("all:" + allButtons);
        System.out.println("new:" + newButtons);
        System.out.println("user" + userButtons);


    }
    public void match (){

        setlvl(lvl);
        setLives(lives);
        setStreaks(streaks);
        setTotalMouseClick(totalMouseClick);

        System.out.println("level: " + lvl);
        System.out.println("total mouse click: " + totalMouseClick);

        if (totalMouseClick==1 && lvl==1){
            if (userButtons.equals(newButtons)){
                System.out.println("lvl1 Passed");
                lvl++;
                lblLvl.setText(String.valueOf(lvl));
                streaks++;
                lblstreaks.setText(String.valueOf(streaks));
                totalMouseClick=0;
                resetColor();
                System.out.print(lvl);
                gameStart(lvl, lives, streaks);
            }
            else {System.out.println("lvl1 failed");
                lives--;
                lbllives.setText(String.valueOf(lives));
                totalMouseClick = 0;
                if (lives>0) {
                    //game reset
                    resetColor();
                    gameStart(lvl, lives, streaks);
                }
                else {
                    System.out.print("GameOver");
                }
            }
        }
        else if (totalMouseClick >1 && lvl>1 && totalMouseClick ==lvl){
            if (userButtons.equals(newButtons)){
                System.out.println("lvl1+ passed");
                lvl++;
                lblLvl.setText(String.valueOf(lvl));
                streaks++;
                lblstreaks.setText(String.valueOf(streaks));
                totalMouseClick = 0;
                resetColor();
                gameStart(lvl, lives, streaks);
            }
            else {System.out.println("lvl1+ failed");
                lives--;
                System.out.print ("life reamian: " + lives);
                lbllives.setText(String.valueOf(lives));
                totalMouseClick = 0;
                if (lives>0) {
                    //game reset
                    resetColor();
                    gameStart(lvl, lives, streaks);
                }
                else {
                    //game over
                    System.out.println("Game Over");
                }
            }
        }
    }

    public void totalClickCount(int t){
        totalMouseClick++;
        lblClickCount.setText(String.valueOf(totalMouseClick));
    }

    public void mouseClickCount0(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn0.setStyle("-fx-base: #008000");
        userButtons.add(btn0);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount1(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn1.setStyle("-fx-base: #008000");
        userButtons.add(btn1);
        System.out.println("User input: "+userButtons);
        System.out.println("User input: "+userButtons.get(0));
        match();
    }

    public void mouseClickCount2(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn2.setStyle("-fx-base: #008000");
        userButtons.add(btn2);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount3(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn3.setStyle("-fx-base: #008000");
        userButtons.add(btn3);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount4(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn4.setStyle("-fx-base: #008000");
        userButtons.add(btn4);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount5(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn5.setStyle("-fx-base: #008000");
        userButtons.add(btn5);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount6(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn6.setStyle("-fx-base: #008000");
        userButtons.add(btn6);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount7(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn7.setStyle("-fx-base: #008000");
        userButtons.add(btn7);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount8(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn8.setStyle("-fx-base: #008000");
        userButtons.add(btn8);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount9(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn9.setStyle("-fx-base: #008000");
        userButtons.add(btn9);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount10(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn10.setStyle("-fx-base: #008000");
        userButtons.add(btn10);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount11(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn11.setStyle("-fx-base: #008000");
        userButtons.add(btn11);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount12(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn12.setStyle("-fx-base: #008000");
        userButtons.add(btn12);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount13(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn13.setStyle("-fx-base: #008000");
        userButtons.add(btn13);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount14(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn14.setStyle("-fx-base: #008000");
        userButtons.add(btn14);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount15(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn15.setStyle("-fx-base: #008000");
        userButtons.add(btn15);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount16(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn16.setStyle("-fx-base: #008000");
        userButtons.add(btn16);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount17(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn17.setStyle("-fx-base: #008000");
        userButtons.add(btn17);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount18(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn18.setStyle("-fx-base: #008000");
        userButtons.add(btn18);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount19(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn19.setStyle("-fx-base: #008000");
        userButtons.add(btn19);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount20(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn20.setStyle("-fx-base: #008000");
        userButtons.add(btn20);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount21(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn21.setStyle("-fx-base: #008000");
        userButtons.add(btn21);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount22(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn22.setStyle("-fx-base: #008000");
        userButtons.add(btn22);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount23(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn23.setStyle("-fx-base: #008000");
        userButtons.add(btn23);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount24(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn24.setStyle("-fx-base: #008000");
        userButtons.add(btn24);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount25(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn25.setStyle("-fx-base: #008000");
        userButtons.add(btn25);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount26(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn26.setStyle("-fx-base: #008000");
        userButtons.add(btn26);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount27(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn27.setStyle("-fx-base: #008000");
        userButtons.add(btn27);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount28(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn28.setStyle("-fx-base: #008000");
        userButtons.add(btn28);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount29(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn29.setStyle("-fx-base: #008000");
        userButtons.add(btn29);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount30(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn30.setStyle("-fx-base: #008000");
        userButtons.add(btn30);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount31(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn31.setStyle("-fx-base: #008000");
        userButtons.add(btn31);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount32(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn32.setStyle("-fx-base: #008000");
        userButtons.add(btn32);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount33(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn33.setStyle("-fx-base: #008000");
        userButtons.add(btn33);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount34(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn34.setStyle("-fx-base: #008000");
        userButtons.add(btn34);
        System.out.println("User input: "+userButtons);
        match();
    }

    public void mouseClickCount35(MouseEvent mouseEvent) {
        setTotalMouseClick(totalMouseClick);
        totalClickCount(totalMouseClick);
        setUserButtons(userButtons);
        btn35.setStyle("-fx-base: #008000");
        userButtons.add(btn35);
        System.out.println("User input: "+userButtons);
        match();
    }
}


