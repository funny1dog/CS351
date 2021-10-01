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
            setCurrScore((int) (millisElapsed / 30));
            System.out.println(getCurrScore());
            //gameOverPopUp();
        }
        else {changeAnchors();}

}

    private void changeAnchors() {
        //centerScreen.getChildren().addAll(target);
        double topRand = Math.random()*100;
        double leftRand = Math.random()*150;
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


}