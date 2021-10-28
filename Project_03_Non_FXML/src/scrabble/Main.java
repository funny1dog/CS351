package scrabble;
/**
 *  Jiajun Guo
 *
 *  This class main
 *
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXMLLoader.load(getClass().getResource("sample.fxml"));
        final int WIDTH = 1200;
        final int HEIGHT = 800;
        final int numberOfPlayers = 2;
        final int spacing = 10;

        buildDictionaryTrie(GameManager.wordChecker);
        ScrollPane root = new ScrollPane(); // highest level container
        root.setPrefSize(WIDTH, HEIGHT);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));

        // holds board, and sideBar
        HBox horizontalOutermostContainer = new HBox();
        // holds tileRacks, player scores, and action buttons
        VBox sideBar = new VBox();
        sideBar.setAlignment(Pos.CENTER_RIGHT);

        LetterBag.createInstance();

        Mutex mutex = new Mutex(0, numberOfPlayers);
        //create AI
        AIPlayer aiPlayer = new AIPlayer();
        AITurn aiTurn = new AITurn(mutex, 0, aiPlayer);
        HumanPlayer humanPlayer = new HumanPlayer();
        HumanTurn humanTurn = new HumanTurn(mutex, 1, humanPlayer);
        //create thread
        Thread aiThread = new Thread(aiTurn);
        Thread humanThread = new Thread(humanTurn);
        aiThread.start();
        humanThread.start();
        //create board
        Board board = new Board();
        GameManager gameManager = new GameManager(board);
        board.setAlignment(Pos.TOP_CENTER);
        horizontalOutermostContainer.getChildren().add(board);
        sideBar.getChildren().addAll(new Label("Player0"), aiPlayer.getLetterRack(),
                new Label("Player1"), humanPlayer.getLetterRack());

        // holds buttons and whoseTurn label
        HBox turnBar = new HBox();
        Button dumpButton = new Button("Dump Letters");
        dumpButton.setOnMouseClicked(e -> {
            humanPlayer.dumpLetters();
            mutex.switchTurns();
        });
        Button endTurnButton = new Button("End Turn");
        Label whoseTurn = new Label(mutex.getWhoseTurnLabel());
        endTurnButton.setOnMouseClicked((MouseEvent event) -> {
            gameManager.commitAllNewlyPopulatedContainers();
            mutex.switchTurns();
            whoseTurn.setText(mutex.getWhoseTurnLabel());
            gameManager.doBestPossibleMove(humanPlayer);
            humanPlayer.fillLetterRack();
        });
        turnBar.getChildren().addAll(endTurnButton, dumpButton, whoseTurn);
        turnBar.setSpacing(spacing);
        sideBar.getChildren().add(turnBar);
        horizontalOutermostContainer.getChildren().add(sideBar);
        root.setContent(horizontalOutermostContainer);
        primaryStage.setTitle("Scrabble Game");
        primaryStage.show();
    }
    //search word from 'wordList'
    private void buildDictionaryTrie(WordChecker wordChecker) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/zhibin/Documents/GitHub/CS351/Project_03_Non_FXML/src/scrabble/wordList"));
        String line = bufferedReader.readLine();
        while (line != null) {
            wordChecker.insert(line);
            line = bufferedReader.readLine();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
