package Jia.src.View;

import Jia.src.Cell.Grid;
import Jia.src.View.Controller;
import Jia.src.View.Pane;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main Class
 * @author Jiajun Guo, Steven Chen
 */
public class Main extends Application
{
    private final String APP_TITLE = "Maze";
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane maze = createApp();
        
        primaryStage.setScene(maze.getScene());
        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();
    }
    
    private Pane createApp()
    {
        // Create Model
        Grid board = new Grid();
        // Create View
        Pane pane = new Pane(board);
        // Create Controller
        Controller controller = new Controller(board, pane);
        
        return pane;
    }
    
}
