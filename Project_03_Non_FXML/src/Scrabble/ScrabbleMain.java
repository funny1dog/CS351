package Scrabble;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScrabbleMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int WIDTH = 680;
        final int HEIGHT = 1350;
        final int numberOfPlayers = 2;
        final int spacing = 10;

        buildDictionaryTrie(ScrabbleGameLogic.spellChecker);
        ScrollPane root = new ScrollPane(); // highest level container
        root.setPrefSize(WIDTH, HEIGHT);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));

        // holds board, and sideBar
        VBox topContainer = new VBox();
        HBox player1box = new HBox();
        HBox player2box = new HBox();

        LetterBag.createInstance();
        GameSync gameSync = new GameSync(0, numberOfPlayers);

        //create AI
        AIPlayer aiPlayer = new AIPlayer();
        AISwapTurn aiTurn = new AISwapTurn(gameSync, 0, aiPlayer);
        HumanPlayer humanPlayer = new HumanPlayer();
        HumanSwapTurn humanTurn = new HumanSwapTurn(gameSync, 1, humanPlayer);

        //create thread
        Thread aiThread = new Thread(aiTurn);
        Thread humanThread = new Thread(humanTurn);
        aiThread.start();
        humanThread.start();

        //create board
        GameBoard gameBoard = new GameBoard();
        ScrabbleGameLogic scrabbleGameLogic = new ScrabbleGameLogic(gameBoard);

        Label label1 = new Label("Player 1");
        label1.setFont(new Font("Arial", 18));
        player1box.getChildren().addAll(label1 , aiPlayer.getLetterRack());
        player1box.setAlignment(Pos.CENTER);

        Label label2 = new Label("Player 2");
        label2.setFont(new Font("Arial", 18));
        player2box.getChildren().addAll(label2, humanPlayer.getLetterRack());
        player2box.setAlignment(Pos.CENTER);

        // holds buttons and whoseTurn label
        HBox turnBar = new HBox();
        turnBar.setAlignment(Pos.CENTER);
        Button dumpButton = new Button("New Letters");
        dumpButton.setOnMouseClicked(e -> {
            humanPlayer.randomLetters();
            gameSync.switchTurns();
        });
        Button endTurnButton = new Button("End Turn");
        Label whoseTurn = new Label(gameSync.getWhoseTurnLabel());
        endTurnButton.setOnMouseClicked((MouseEvent event) -> {
            scrabbleGameLogic.commitAllNewlyPopulatedContainers();
            gameSync.switchTurns();
            whoseTurn.setText(gameSync.getWhoseTurnLabel());
            scrabbleGameLogic.doBestPossibleMove(humanPlayer);
            humanPlayer.fillLetterRack();
        });
        turnBar.getChildren().addAll(endTurnButton, dumpButton, whoseTurn);
        turnBar.setSpacing(spacing);
        player1box.setSpacing(spacing);
        player2box.setSpacing(spacing);

        topContainer.getChildren().add(player1box);
        topContainer.getChildren().add(gameBoard);
        topContainer.getChildren().add(player2box);
        topContainer.getChildren().add(turnBar);
        root.setContent(topContainer);
        primaryStage.setTitle("Scrabble Game");
        primaryStage.show();
    }
    //search word from 'wordList'
    private void buildDictionaryTrie(SpellChecker spellChecker) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/zhibin/Documents/GitHub/CS351/Project_03_Non_FXML/src/scrabble/wordList"));
        String line = bufferedReader.readLine();
        while (line != null) {
            spellChecker.insert(line);
            line = bufferedReader.readLine();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
