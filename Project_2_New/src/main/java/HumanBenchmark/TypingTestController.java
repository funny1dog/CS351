package HumanBenchmark;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TypingTestController extends MiniGame{

    @FXML
    TextArea wordsOfDisplay;
    @FXML
    TextField typeWriter;
    @FXML
    Label labelTimer;
    @FXML
    Label labelTypedWord;
    @FXML
    VBox vboxText;
    private Scene scene;
    public ArrayList<Character> display = new ArrayList<Character>();
    public ArrayList<Character> typed = new ArrayList<>();
    public boolean noError = false;
    public long startTime;
    public int wordsTyped;
    private int totalChars;
    private boolean gameRunning;
    private boolean timerStarted;
    private long start;
    private HBox line = new HBox();



    public TypingTestController(/*String n, String unit, boolean inverse*/) throws IOException {
        //super("Reaction Time", "ms", true);
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    public void setdisplay(ArrayList display){
        this.display = display;
    }
    public void setTyped(ArrayList typed){
        this.typed = typed;
    }
    public void setNoError(boolean noError){
        this.noError = noError;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public void setWordsTyped(int wordsTyped) {this.wordsTyped = wordsTyped;}
    public void setVboxText(VBox vboxText){this.vboxText = vboxText;}
    public void setLine(HBox line){this.line = line;}

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
    // hit start button
    public void actionBtnStart(ActionEvent actionEvent) {

        AnimationTimer a = new AnimationTimer(){

            @Override
            public void handle(long now) {
                boolean gameOver;
                long elapsedTime;
                double minutesTime;
                if(gameRunning){
                    gameOver = checkTyping();
                    if(gameOver){
                        elapsedTime = System.nanoTime() - start;
                        minutesTime = elapsedTime/(6e+10);
                        getCurrentScore((int)((totalChars/5)/minutesTime));
                        gameRunning = false;
                        int result = (int) ((totalChars/5)/minutesTime);
                        gameOverPopOut(result);

                    }
                }
            }
        };
        a.start();
        char[] charText;
        HBox line = new HBox();
        Label tempLabel;
        int numChars = 0;
        int rand = (int)(Math.random()*5);
        gameRunning = true;
        timerStarted = false;
        charText = choosePassage(rand).toCharArray();
        for(char c:charText){
            if(numChars == 45){
                vboxText.getChildren().add(line);
                line = new HBox();
                numChars = 0;
            }
            numChars ++;
            tempLabel = new Label("" +c);
            line.getChildren().add(tempLabel);
        }
        vboxText.getChildren().add(line);


    }

    public void newRound(){
        setVboxText(vboxText);
        setLine(line);
        vboxText.getChildren().clear();
        line.getChildren().clear();
        AnimationTimer a = new AnimationTimer(){

            @Override
            public void handle(long now) {
                boolean gameOver;
                long elapsedTime;
                double minutesTime;
                if(gameRunning){
                    gameOver = checkTyping();
                    if(gameOver){
                        elapsedTime = System.nanoTime() - start;
                        minutesTime = elapsedTime/(6e+10);
                        getCurrentScore((int)((totalChars/5)/minutesTime));
                        gameRunning = false;
                        int result = (int) ((totalChars/5)/minutesTime);
                        gameOverPopOut(result);

                    }
                }
            }
        };
        a.start();
        char[] charText;

        Label tempLabel;
        int numChars = 0;
        int rand = (int)(Math.random()*5);
        gameRunning = true;
        timerStarted = false;
        charText = choosePassage(rand).toCharArray();
        for(char c:charText){
            if(numChars == 45){
                vboxText.getChildren().add(line);
                line = new HBox();
                numChars = 0;
            }
            numChars ++;
            tempLabel = new Label("" +c);
            line.getChildren().add(tempLabel);
        }
        vboxText.getChildren().add(line);
    }

    private boolean checkTyping(){
        char[] input = typeWriter.getText().toCharArray();
        boolean allCorrect = true;
        int i =0;
        HBox line;
        String tempChar;
        resetTextColor();
        if(input.length ==0) {
            return false;
        }
        else if (!timerStarted){
            start = System.nanoTime();
            timerStarted = true;
        }
        for (Node n : vboxText.getChildren()){
            line = (HBox) n;
            for (Node c: line.getChildren()){
                tempChar = ((Label)c).getText();
                if(i >= input.length){
                    return false;
                }
                if(!tempChar.equals(input[i] + "")){
                    ((Label)c).setTextFill(Color.RED);
                    allCorrect = false;
                }
                else {
                    ((Label)c).setTextFill(Color.GREEN);}
                i++;
                }
            }
        return allCorrect;
        }

    private void resetTextColor() {
        HBox line;
        for(Node n: vboxText.getChildren()){
            line = (HBox)n;
            for (Node c: line.getChildren()){
                ((Label)c).setTextFill(Color.BLACK);
            }
        }
    }

    @Override
    public void playGame() {

    }

    @Override
    public void initializeWindow(Stage primaryStage) {

    }

    private void getCurrentScore(double v) {
    }

    @Override
    public void instructionsPopUp() {

    }

    public void wordOfTypeWriter(KeyEvent keyEvent) {
    }

    private String choosePassage(int i) {
        if (i == 0) {
            this.totalChars = 497;
            return "All the action of Ulysses takes place in and immediately around Dublin on a single day (June 16, 1904). The three central characters—Stephen Dedalus (the hero of Joyce’s earlier Portrait of the Artist as a Young Man); Leopold Bloom, a Jewish advertising canvasser; and his wife, Molly—are intended to be modern counterparts of Telemachus, Ulysses (Odysseus), and Penelope, respectively, and the events of the novel loosely parallel the major events in Odysseus’s journey home after the Trojan War.";
        } else if (i == 1) {
            this.totalChars = 479;
            return "The book begins at 8:00 in the morning in a Martello tower (a Napoleonic-era defensive structure), where Stephen lives with medical student Buck Mulligan and his English friend Haines. They prepare for the day and head out. After teaching at a boys’ school, Stephen receives his pay from the ignorant and anti-Semitic headmaster, Mr. Deasy, and takes a letter from Deasy that he wants to have published in two newspapers. Afterward Stephen wanders along a beach, lost in thought.";
        } else if (i == 2) {
            this.totalChars = 649;
            return "Bloom goes to a newspaper office to negotiate the placement of an advertisement, which the foreman agrees to as long as it is to run for three months. Bloom leaves to talk with the merchant placing the ad. Stephen arrives with Deasy’s letter, and the editor agrees to publish it. When Bloom returns with an agreement to place the ad for two months, the editor rejects it. Bloom walks through Dublin for a while, stopping to chat with Mrs. Breen, who mentions that Mina Purefoy is in labour. He later has a cheese sandwich and a glass of wine at a pub. On his way to the National Library afterward, he spots Boylan and ducks into the National Museum.";
        } else if (i == 3) {
            this.totalChars = 344;
            return "In the National Library, Stephen discusses his theories about Shakespeare and Hamlet with the poet AE, the essayist and librarian John Eglinton, and the librarians Richard Best and Thomas Lyster. Bloom arrives, looking for a copy of an advertisement he had placed, and Buck shows up. Stephen and Buck leave to go to a pub as Bloom also departs.";
        } else {
            this.totalChars = 534;
            return "Simon and Matt Lenehan meet in the bar of the Ormond Hotel, and later Boylan arrives. Leopold had earlier seen Boylan’s car and followed it to the hotel, where he then dines with Richie Goulding. Boylan leaves with Lenehan, on his way to his assignation with Molly. Later, Bloom goes to Barney Kiernan’s boisterous pub, where he is to meet Cunningham in order to help with the Dignam family’s finances. Bloom finds himself being cruelly mocked, largely for his Jewishness. He defends himself, and Cunningham rushes him out of the bar.";
        }
    }

    private void gameOverPopOut(int result) {
        Stage newGameStage = new Stage();
        Label instructions = new Label ("Game Over! \n" +
                "Your RPM is: " + result + " PER MINUTE");
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
        newGameStage.setTitle("Typing Test Instruction");
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
                "Human benchmark version 0.1 \n" +
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


