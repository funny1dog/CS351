import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TypingGame extends MiniGame{

    private VBox text;
    private TextField textField;
    private BorderPane border;
    private boolean gameRunning;
    private boolean timerStarted;
    private long start;
    private int totalChars;


    public TypingGame(){ super("Typing", " wpm", false); }


    @Override
    public void initializeWindow(Stage primaryStage) {
        setGameStage(new Stage());
        getGameStage().initModality(Modality.APPLICATION_MODAL);
        getGameStage().initOwner(primaryStage);
        getGameStage().setAlwaysOnTop(true);
        getGameStage().setTitle(getName());

        Label title = new Label("Typing Test");
        title.setFont(new Font(50));
        text = new VBox(5);
        textField = new TextField();

        border = new BorderPane();
        border.setBackground(new Background(new BackgroundFill(
                Color.KHAKI, new CornerRadii(10), new Insets(0))));
        border.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        border.setCenter(text);
        BorderPane.setAlignment(text, Pos.CENTER);
        border.setBottom(textField);
        BorderPane.setAlignment(textField, Pos.CENTER);

        Scene scene = new Scene(border, 685, 645);
        getGameStage().setScene(scene);
        getGameStage().show();

        AnimationTimer a = new AnimationTimer(){
            @Override
            public void handle(long now){
                boolean gameOver;
                long elapsedTime;
                double minutesTime;
                if(gameRunning){
                    gameOver = checkTyping();
                    if(gameOver){
                        elapsedTime = System.nanoTime() - start;
                        minutesTime = elapsedTime/(6e+10);
                        setCurrScore((int)((totalChars/5)/minutesTime));
                        gameRunning = false;
                        gameOverPopUp();
                    }
                }
            }
        };
        a.start();

        instructionsPopUp();
    }


    @Override
    public void playGame(){
        char[] charText;
        HBox line = new HBox();
        Label tempLabel;
        int numChars = 0;
        int rand = (int)(Math.random()*5);
        gameRunning = true;
        timerStarted = false;
        charText = choosePassage(rand).toCharArray();
        for(char c : charText){
            if(numChars == 45){
                text.getChildren().add(line);
                line = new HBox();
                numChars = 0;
            }
            numChars++;
            tempLabel = new Label("" + c);
            tempLabel.setFont(new Font(30));
            line.getChildren().add(tempLabel);
        }
        text.getChildren().add(line);
    }

    private boolean checkTyping(){
        char[] input = textField.getText().toCharArray();
        boolean allCorrect = true;
        int i = 0;
        HBox line;
        String tempChar;
        resetTextColor();
        if(input.length == 0){ return false; }
        else if(!timerStarted){
            start = System.nanoTime();
            timerStarted = true;
        }
        for(Node n : text.getChildren()){
            line = (HBox)n;
            for(Node c : line.getChildren()){
                tempChar = ((Label)c).getText();
                if(i >= input.length){ return false; }
                if(!tempChar.equals(input[i] + "")){
                    ((Label)c).setTextFill(Color.RED);
                    allCorrect = false;
                }
                else{ ((Label)c).setTextFill(Color.LIMEGREEN); }
                i++;
            }
        }
        return allCorrect;
    }


    private void resetTextColor(){
        HBox line;
        for(Node n : text.getChildren()){
            line = (HBox)n;
            for(Node c : line.getChildren()){
                ((Label)c).setTextFill(Color.BLACK);
            }
        }
    }

    @Override
    public void instructionsPopUp(){
        Stage instructionsStage = new Stage();
        Label instructions = new Label(" This game will test how fast you"+
                " type in\n  words per minute. Use the field at the\n bottom "+
                "of the window to copy the text\n                     on the "+
                "screen.\n              Type as fast as you can!");
        Button startButton = new Button("Start Game");
        BorderPane borderPane = new BorderPane();
        Scene scene;
        instructionsStage.initModality(Modality.APPLICATION_MODAL);
        instructionsStage.initOwner(getGameStage());
        instructionsStage.setAlwaysOnTop(true);
        instructionsStage.setTitle("Instructions");
        instructions.setFont(new Font(20));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            instructionsStage.close();
            gameRunning = true;
            playGame();
        });

        text = new VBox(5);
        border.setCenter(text);
        BorderPane.setAlignment(text, Pos.CENTER);
        textField.setText("");

        borderPane.setCenter(instructions);
        borderPane.setBottom(startButton);
        BorderPane.setAlignment(instructions, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        scene = new Scene(borderPane, 400, 200);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }


    private String choosePassage(int i){
        if(i == 0){
            totalChars = 486;
            return "They passed beneath the gatehouse, over the drawbridge, " +
                    "through the outer walls. Summer andGrey Wind came loping " +
                    "beside them, sniffing at the wind. Close behind came " +
                    "Theon Greyjoy, with his longbow and a quiver of broadheads; " +
                    "he had a mind to take a deer, he had told them. He was " +
                    "followed by four guardsmen in mailed shirts and coifs, and " +
                    "Joseth, a stick-thin stableman whom Robb had named master " +
                    "of horse while Hullen was away. Maester Luwin brought up " +
                    "the rear, riding on a donkey. ";
        }
        else if(i == 1){
            totalChars = 528;
            return "Beyond the castle lay the market square, its wooden stalls " +
                    "deserted now. They rode down the muddy streets of the " +
                    "village, past rows of small neat houses of log and undressed " +
                    "stone. Less than one in five were occupied, thin tendrils " +
                    "of wood smoke curling up from their chimneys. The rest would " +
                    "fill up one by one as it grew colder. When the snow fell and " +
                    "the ice winds howled down out of the north, Old Nan said, " +
                    "farmers left their frozen fields and distant hold fasts, loaded " +
                    "up their wagons, and then the winter town came alive.";
        }
        else if(i == 2){
            totalChars = 465;
            return "Bran felt a sudden dread. Dark wings, dark " +
                    "words, Old Nan always said, and of late " +
                    "the messenger ravens had been proving the " +
                    "truth of the proverb. When Robb wrote to the " +
                    "Lord Commander of the night's Watch, the bird " +
                    "that came back brought word that Uncle Benjen " +
                    "was still missing. Then a message had arrived " +
                    "from the Eyrie, from Mother, but that had not " +
                    "been good news either. She did not say when " +
                    "she meant to return, only that she had taken " +
                    "the Imp as a prisoner.";
        }
        else if(i == 3){
            totalChars = 469;
            return "Bran had sort of liked the little man, yet the " +
                    "name Lannister sent cold fingers creeping up his " +
                    "spine. There was something about the Lannisters, " +
                    "something he ought to remember, but when he tried " +
                    "to think what, he felt dizzy and his stomach clenched " +
                    "hard as a stone. Robb spent most of that day locked " +
                    "behind closed doors with Maester Luwin, Theon Greyjoy, " +
                    "and Hallis Mollen. Afterward, riders were sent out on " +
                    "fast horses, carrying Robb’s commands throughout the " +
                    "north.";
        }
        else{
            totalChars = 544;
            return "The stream was running high and fast. Robb dismounted " +
                    "and led his gelding across the ford. In the deepest " +
                    "part of the crossing, the water came up to midthigh. " +
                    "He tied his horse to a tree on the far side, and waded " +
                    "back across for Bran and Dancer. The current foamed " +
                    "around rock and root, and Bran could feel the spray on " +
                    "his face as Robb led him over. It made him smile. For a " +
                    "moment he felt strong again, and whole. He looked up at " +
                    "the trees and dreamed of climbing them, right up to the " +
                    "very top, with the whole forest spread out beneath him.";
        }
    }
}
