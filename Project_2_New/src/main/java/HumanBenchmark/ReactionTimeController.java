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
    private boolean gameRunning;
    private boolean clickReady;
    private long startTime;
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
    public void playGame() {

        Thread timer = new Thread(() -> {
            Object o = new Object();
            int rand = (int) (Math.random() * 5000) + 1500;
            synchronized (o) {
                try {
                    o.wait(rand);
                    if (gameRunning) {
                        clickReady = true;
                        startTime = System.nanoTime();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        clickReady = false;
        timer.start();
    }

    public void test() {
        System.out.println("test");
    }

    public void mouseClick(MouseEvent mouseEvent)  {
        mouseClickTime();
        long result = clickTime - clockTime - 3000;
        RTLabel1.setText("Reaction time is:" +  result + "MS");
    }

    public void ActionRestart(ActionEvent actionEvent) {
        newRound();
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
        //recMain.setFill(Color.WHITE);
        return clickTime;
    }


    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    public void InstructionPop(ActionEvent actionEvent){
        Stage instructionStage = new Stage();
        Label instructions = new Label ("test");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        instructionStage.initModality(Modality.APPLICATION_MODAL);
        instructionStage.initOwner(getGameStage());
        instructionStage.setAlwaysOnTop(true);
        instructionStage.setTitle("Reaction Time Instruction");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            instructionStage.close();
            gameRunning = true;
            playGame();
        });

        gameRunning = false;
        clickReady = false;

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 200);
        instructionStage.setScene(scene);
        instructionStage.show();

    }

    @Override
    public void instructionsPopUp() {
        Stage instructionStage = new Stage();
        Label instructions = new Label ("test");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        instructionStage.initModality(Modality.APPLICATION_MODAL);
        instructionStage.initOwner(getGameStage());
        instructionStage.setAlwaysOnTop(true);
        instructionStage.setTitle("Reaction Time Instruction");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            instructionStage.close();
            gameRunning = true;
            playGame();
        });

        gameRunning = false;
        clickReady = false;

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 200);
        instructionStage.setScene(scene);
        instructionStage.show();

    }
}
