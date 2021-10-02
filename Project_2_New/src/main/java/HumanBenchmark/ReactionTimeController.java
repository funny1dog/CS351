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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ReactionTimeController extends MiniGame {

    @FXML
    public Rectangle recMain;
    @FXML
    public Label lblMain;
    @FXML Label RTLabel1;
    @FXML Label RTLabel2;
    private Scene scene;

    public long clickTime;
    public long clockTime;

    public ReactionTimeController(/*String n, String unit, boolean inverse*/) throws Exception {

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
    public void playGame() {}

    public void test() {
        System.out.println("test");
    }

    public void mouseClick(MouseEvent mouseEvent)  {
        mouseClickTime();
        long result = clickTime - clockTime - 3000;
        RTLabel1.setText("Reaction time is:" +  result + "MS");
        gameOverPopUp(result);
    }

    public long newRound(){
        recMain.setFill(Color.DODGERBLUE);
        long clockTime = System.currentTimeMillis();
        System.out.println(clockTime);
        lblMain.setText("Get Ready...");
        PauseTransition pause = new PauseTransition(
                Duration.seconds(3)
        );
        pause.setOnFinished(e -> {recMain.setFill(Color.GREEN);
            lblMain.setText("GO!");
        });
        pause.play();
        this.clockTime = clockTime;
        return clockTime;
    }

    public long mouseClickTime() {
        long clickTime = System.currentTimeMillis();
        this.clickTime = clickTime;
        System.out.println(clickTime);
        return clickTime;
    }

    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    @Override
    public void instructionsPopUp() {}

    public void menuNewGame(ActionEvent actionEvent) {
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Reaction Time Test \n" +
                "When the blue box turns green, \n" +
                "Hit as quickly as you can. \n" +
                "Click anywhere to start");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Reaction Time Instruction");
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

// score variable: long result.
