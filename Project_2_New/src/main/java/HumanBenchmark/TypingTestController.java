package HumanBenchmark;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
                        //gameOverPopup;

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
//        // create an ArrayList<char> wordsOfDIsplayed
//        String words = "This is just a sample text.";
//        for (int i = 0; i < words.length(); i++) {
//            display.add(words.charAt(i));}
//        //System.out.print(display);
//        for(int i=0;i<display.size();i++){
//            System.out.println(display.get(i));
//        }
//        // create an empty arraylist typedOfDisplay Arraylist<char>
//            // bring text to textarea;
//            // also include the size of the ArrayList<char>
//            // timer start counting;
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
            this.totalChars = 486;
            return "They passed beneath the gatehouse, over the drawbridge, through the outer walls. Summer andGrey Wind came loping beside them, sniffing at the wind. Close behind came Theon Greyjoy, with his longbow and a quiver of broadheads; he had a mind to take a deer, he had told them. He was followed by four guardsmen in mailed shirts and coifs, and Joseth, a stick-thin stableman whom Robb had named master of horse while Hullen was away. Maester Luwin brought up the rear, riding on a donkey. ";
        } else if (i == 1) {
            this.totalChars = 528;
            return "Beyond the castle lay the market square, its wooden stalls deserted now. They rode down the muddy streets of the village, past rows of small neat houses of log and undressed stone. Less than one in five were occupied, thin tendrils of wood smoke curling up from their chimneys. The rest would fill up one by one as it grew colder. When the snow fell and the ice winds howled down out of the north, Old Nan said, farmers left their frozen fields and distant hold fasts, loaded up their wagons, and then the winter town came alive.";
        } else if (i == 2) {
            this.totalChars = 465;
            return "Bran felt a sudden dread. Dark wings, dark words, Old Nan always said, and of late the messenger ravens had been proving the truth of the proverb. When Robb wrote to the Lord Commander of the night's Watch, the bird that came back brought word that Uncle Benjen was still missing. Then a message had arrived from the Eyrie, from Mother, but that had not been good news either. She did not say when she meant to return, only that she had taken the Imp as a prisoner.";
        } else if (i == 3) {
            this.totalChars = 469;
            return "Bran had sort of liked the little man, yet the name Lannister sent cold fingers creeping up his spine. There was something about the Lannisters, something he ought to remember, but when he tried to think what, he felt dizzy and his stomach clenched hard as a stone. Robb spent most of that day locked behind closed doors with Maester Luwin, Theon Greyjoy, and Hallis Mollen. Afterward, riders were sent out on fast horses, carrying Robb’s commands throughout the north.";
        } else {
            this.totalChars = 544;
            return "The stream was running high and fast. Robb dismounted and led his gelding across the ford. In the deepest part of the crossing, the water came up to midthigh. He tied his horse to a tree on the far side, and waded back across for Bran and Dancer. The current foamed around rock and root, and Bran could feel the spray on his face as Robb led him over. It made him smile. For a moment he felt strong again, and whole. He looked up at the trees and dreamed of climbing them, right up to the very top, with the whole forest spread out beneath him.";
        }
    }
}

