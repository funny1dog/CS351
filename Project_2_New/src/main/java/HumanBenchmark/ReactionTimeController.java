package HumanBenchmark;

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

    @FXML
    public Rectangle recMain;
    @FXML
    public Label lblMain;
    private Scene scene;
    private boolean gameRunning;
    private boolean clickReady;
    private long startTime;

    public ReactionTimeController(/*String n, String unit, boolean inverse*/) throws Exception {

    }




    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void ActionMenuMainPage(ActionEvent actionEvent) throws IOException {
       FXMLLoader loaderMainMenu = new FXMLLoader(getClass().getResource("HumanBenchmark.fxml"));
        try {
            Pane mainBorderPane = loaderMainMenu.load();
            scene.setRoot(mainBorderPane);
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


    public void mouseClick(MouseEvent mouseEvent)  {
        recMain.setFill(Color.WHITE);

    }
    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    @Override
    public void instructionsPopUp() {

    }
}
