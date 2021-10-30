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

public class ScrabbleMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXMLLoader.load(getClass().getResource("sample.fxml"));
        final int WIDTH = 680;
        final int HEIGHT = 1300;
        final int numberOfPlayers = 2;
        final int spacing = 10;

        ReadDictionary(ScrabbleMainLogic.spellChecker);
        ScrollPane root = new ScrollPane();
        root.setPrefSize(WIDTH, HEIGHT);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));



        // holds tileRacks, player scores, and action buttons
        VBox VBOXTOP = new VBox();
        VBOXTOP.setAlignment(Pos.BOTTOM_LEFT);


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
        GameBoard board = new GameBoard();
        ScrabbleMainLogic newGame = new ScrabbleMainLogic(board);
        board.setAlignment(Pos.TOP_CENTER);
        //HBOXTOP.getChildren().add(board);

        HBox HBOXMID = new HBox();
//        HBOXMID.getChildren().addAll(new Label("Player0"), aiPlayer.getLetterRack(),
//                new Label("Player1"), humanPlayer.getLetterRack());

        HBox HBoxPlayer1 = new HBox();
        HBoxPlayer1.getChildren().addAll(new Label ("Player 1"), aiPlayer.getLetterRack());

        HBox HBoxPlayer2 = new HBox();
        HBoxPlayer2.getChildren().addAll(new Label ("Player 2"), humanPlayer.getLetterRack());
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
            newGame.commitAllNewlyPopulatedContainers();
            mutex.switchTurns();
            whoseTurn.setText(mutex.getWhoseTurnLabel());
            newGame.doBestPossibleMove(humanPlayer);
            humanPlayer.fillLetterRack();
        });
        turnBar.getChildren().addAll(endTurnButton, dumpButton, whoseTurn);
        turnBar.setSpacing(spacing);
        VBOXTOP.getChildren().add(HBoxPlayer1);
        VBOXTOP.getChildren().add(board);
        VBOXTOP.getChildren().add(HBoxPlayer2);
        VBOXTOP.getChildren().add(turnBar);
        //HBOXTOP.getChildren().add(sideBar);
        //root.setContent(HBOXTOP);

        root.setContent(VBOXTOP);
        primaryStage.setTitle("Scrabble Game");
        primaryStage.show();
    }



    // read file
    private void ReadDictionary (SpellChecker spellChecker) throws IOException {
        BufferedReader br = new BufferedReader (new FileReader("/Users/zhibin/Documents/GitHub/CS351/Project_03_Non_FXML_FINAL/Dictionary/animals.txt"));
        String line = br.readLine();
        while (line != null) {
            spellChecker.insert(line);
            line = br.readLine();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
