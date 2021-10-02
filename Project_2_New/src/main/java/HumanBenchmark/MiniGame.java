package HumanBenchmark;
import javafx.stage.Stage;

public abstract class MiniGame{

    //private final String name;
    //private final String scoreUnit;
    //private final boolean inverseScore;
    private int highScore;
    private int currScore;
    private Stage gameStage;

    public MiniGame(/*String n, String unit, boolean inverse*/){

        //name = n;
        //scoreUnit = unit;
        //inverseScore = inverse;
        highScore = 0;
        currScore = 0;
    }

    public abstract void playGame();

    public abstract void initializeWindow(Stage primaryStage);

    public abstract void instructionsPopUp();

    //public String getName() { return name; }

    public int getHighScore(){ return highScore; }

    public int getCurrScore(){ return currScore; }

    public void setCurrScore(int score){ currScore = score; }

    public void setGameStage(Stage gStage) { gameStage = gStage; }

    public Stage getGameStage() { return gameStage; }

}
