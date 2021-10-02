package HumanBenchmark;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class VerbalMemoryController extends MiniGame{
    private Scene scene;
    @FXML
    Label labelScore;
    @FXML Label labelLives;
    @FXML Label word;
    private int lives = 3;
    private int scores = 0;
    private ArrayList<String> listOfWords = new ArrayList<>();
    private ArrayList<String> seenwords = new ArrayList<>();
    private String wordOfDisplay;

    public VerbalMemoryController(/*String n, String unit, boolean inverse*/) throws IOException {
        //super("Reaction Time", "ms", true);

    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public void setScores(int scores) {
        this.scores = scores;
    }
    public void setListOfWords(ArrayList listOfWords){
        this.listOfWords = listOfWords;
    }
    public void setSeenwords (ArrayList seenwords){
        this.seenwords= seenwords;
    }
    public void setWordOfDisplay(String wordOfDisplay){
        this.wordOfDisplay = wordOfDisplay;
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

    // method read file && push file items into dictionary arraylist
    public void readFile() throws IOException {
        FileReader fr = new FileReader("dictionarySimple.txt");
        BufferedReader bufferedReader= new BufferedReader(fr);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            listOfWords.add(line);
        }
        bufferedReader.close();
    }
    // generate a random word from dictionary arraylist, show on label and duplicate the word into seen arraylist
    public void generateWord(){
        setWordOfDisplay(wordOfDisplay);
        setSeenwords(seenwords);
        setListOfWords(listOfWords);


        Random r = new Random();
        int randomNumber = r.nextInt(listOfWords.size());
        wordOfDisplay = listOfWords.get(randomNumber);
        word.setText(wordOfDisplay);

    }
    public void addWord(){
        setWordOfDisplay(wordOfDisplay);
        setSeenwords(seenwords);
        setListOfWords(listOfWords);
        seenwords.add(wordOfDisplay);
    }


    public void actionBtnNew(ActionEvent actionEvent) {
        setLives(lives);
        setScores(scores);
        setSeenwords(seenwords);
        // if word is in arraylist => life--
        if (seenwords.contains(wordOfDisplay)){
            lives --;
            labelLives.setText("Lives: "+lives);
            if (lives == 0){
                gameOverPopOut(scores);
            }
        }
        else {
            scores++;
            addWord();
            labelScore.setText("Scores: "+scores);
        }
        generateWord();
    }

    public void actionBtnSeen(ActionEvent actionEvent) {
        setLives(lives);
        setScores(scores);
        setSeenwords(seenwords);
        // if word is in arraylist => score++
        if (seenwords.contains(wordOfDisplay)){
            scores++;
            System.out.print(scores);
            labelScore.setText("Scores: "+scores);
        }
        // if word is not in arraylist => life--
        else {
            lives --;
            labelLives.setText("Lives: "+lives);
            if (lives == 0){
                gameOverPopOut(scores);
            }
        }
        generateWord();
        if (lives ==0){

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

    public void actionBtnNewGame(ActionEvent actionEvent) throws IOException {
        readFile();
        generateWord();
        setLives(lives);
        setScores(scores);
        setSeenwords(seenwords);

        lives = 3;
        scores = 0;
        seenwords.clear();

        labelLives.setText("Lives: " + lives);
        labelScore.setText("Score: " + scores);
    }
    public void newRound() throws IOException {
        readFile();
        generateWord();
        setLives(lives);
        setScores(scores);
        setSeenwords(seenwords);

        lives = 3;
        scores = 0;
        seenwords.clear();

        labelLives.setText("Lives: " + lives);
        labelScore.setText("Score: " + scores);
    }

    private void gameOverPopOut(int scores) {
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Game Over! \n" +
                "Your score is: " + scores + " points");
        Button startButton = new Button("Try Again!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Game Over");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            newGameStage.close();
            try {
                newRound();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 300, 200);
        newGameStage.setScene(scene);
        newGameStage.show();
    }

    public void menuNewGame(ActionEvent actionEvent) {
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Verbal Memory \n" +
                "You will be shown words, one at a time. \n" +
                "If you have seen a word during the test, click SEEN \n" +
                "If it's a new word, click NEW \n" +
                "Click Start to start");
        Button startButton = new Button("Start!");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        newGameStage.initModality(Modality.APPLICATION_MODAL);
        newGameStage.initOwner(getGameStage());
        newGameStage.setAlwaysOnTop(true);
        newGameStage.setTitle("Number Memory Instruction");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            newGameStage.close();
            try {
                newRound();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}

