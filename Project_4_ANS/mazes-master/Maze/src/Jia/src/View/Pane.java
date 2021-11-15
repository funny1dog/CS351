/**
 * This class that javafx visualization
 * @author Jiajun Guo, Steven Chen
 */
package Jia.src.View;

import Jia.src.Cell.Grid;
import Jia.src.Cell.Tile;
import Jia.src.Algorithm.MazeGenerationAlgorithm.MazeGenerationAlgorithm;
import Jia.src.Algorithm.MazeSolverAlgorithm.AStarAlgorithm;
import Jia.src.Algorithm.MazeSolverAlgorithm.SolvingAlgorithm;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Pane implements Observer
{

    // Visualization
    private final TextField txtXTiles;
    private final TextField txtYTiles;
    private final TextField txtTileSize;
    private final Button btnRun;
    private final Button btnClear;
    private final Button btnAddWeights;
    private final Button btnAddWalls;
    private final Button btnMaze;
    private final Button btnCreateGrid;
    private final ComboBox cbAlgorithmBox;
    private final ComboBox cbHeuristicBox;
    private final ComboBox cbMazeGenBox;
    private final ComboBox cbNodeBox;

    
    // Grid

    private final javafx.scene.layout.Pane parentGridPane;
    private javafx.scene.layout.Pane gridPane;
    private final VBox rightPane;

    // View

    private final Grid model;
    private final Scene scene;


    public Pane(Grid model)
    {
        this.model = model;
        this.parentGridPane = new javafx.scene.layout.Pane();
        this.gridPane = null;
        
        // Region: rightPane
        this.rightPane = new VBox();
        // style of the Boxes and Buttons
        int padding = 2;
        this.rightPane.setPadding(new Insets(padding, padding, padding, padding));
        this.rightPane.setSpacing(25);
        Text intro = new Text("Welcome to Maze." +
                "\n\nFirst you must generate a Maze." +
                "\n\nThen add the start and end points. " +
                "\n\nFinally, choose the solver Algorithm." +
                "\n\nPress Solve Button" +
                "\n\n(Hint:)" +
                "\n\nThere will be a prompt" +
                "\n\nwhen you hover over the button");
        Font defaultFont = Font.font("Times new Roma", 14);
        intro.setFont(defaultFont);
        // Create Grid box
        VBox vboxCreateGrid = new VBox(padding);
        var defaultHboxStyle = "-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: " + padding + ";"
                + "-fx-border-radius: 4;" + "-fx-border-color: lightgray;";
        vboxCreateGrid.setStyle(defaultHboxStyle);
        Text gridtext = new Text("Enter the number to change the size");
        gridtext.setFont(defaultFont);
        GridPane createPane = new GridPane();
        createPane.setHgap(5);
        createPane.setPadding(new Insets(padding, padding, padding, padding));
        createPane.add(new Text("X: "), 0, 0);
        var defaultXSize = "50";
        txtXTiles = new TextField(defaultXSize);
        createPane.add(txtXTiles, 1, 0);
        createPane.add(new Text("Y: "), 2, 0);
        var defaultYSize = "50";
        txtYTiles = new TextField(defaultYSize);
        createPane.add(txtYTiles, 3, 0);
        createPane.add(new Text("Grid Size: "), 4, 0);
        var defaultTileSize = "15";
        txtTileSize = new TextField(defaultTileSize);
        createPane.add(txtTileSize, 5, 0);
        HBox hboxCreateBtn = new HBox(padding);
        hboxCreateBtn.setAlignment(Pos.CENTER);
        btnCreateGrid = new Button("Create new grid");
        btnCreateGrid.setTooltip(new Tooltip("Overrides previous grid"));
        hboxCreateBtn.getChildren().addAll(btnCreateGrid);

        vboxCreateGrid.getChildren().addAll(gridtext, createPane, hboxCreateBtn);



        // Maze Generation box
        VBox vboxMaze = new VBox(padding);
        vboxMaze.setAlignment(Pos.CENTER);

        HBox hboxMaze = new HBox(padding);
        hboxMaze.setAlignment(Pos.CENTER);
        var txtMaze = new Text("Choose the Algorithm then press the Generate");

        Font defaultFont1 = Font.font("Times new Roma", 14);
        txtMaze.setFont(defaultFont1);
        hboxMaze.getChildren().add(txtMaze);
        HBox hboxMazeGen = new HBox(padding);
        hboxMazeGen.setAlignment(Pos.CENTER);
        cbMazeGenBox = new ComboBox(FXCollections.observableArrayList(MazeGenerationAlgorithm.MazeGen.values()));
        cbMazeGenBox.getSelectionModel().selectFirst();
        cbMazeGenBox.setTooltip(new Tooltip("Maze generation algorithm"));
        btnMaze = new Button("Generate");
        btnMaze.setTooltip(new Tooltip("Generate a maze"));
        hboxMazeGen.getChildren().addAll(cbMazeGenBox, btnMaze);
        vboxMaze.getChildren().addAll(hboxMaze, hboxMazeGen);

        //Points adder box
        HBox hboxNodeBox = new HBox(padding);
        hboxNodeBox.setAlignment(Pos.CENTER);
        //Text txtNodeBox = new Text("choose the type first \nthen press in the maze");
        Text txtNodeBox1 = new Text("Add a ");
        txtNodeBox1.setFont(defaultFont);
        Text txtNodeBox2 = new Text(" point");
        txtNodeBox2.setFont(defaultFont);
        cbNodeBox = new ComboBox(FXCollections.observableArrayList(Tile.Type.values()));
        cbNodeBox.getItems().remove(Tile.Type.VISITED);
        cbNodeBox.getItems().remove(Tile.Type.PATH);
        cbNodeBox.getItems().remove(Tile.Type.HIGHLIGHT);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_DENSE);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_LIGHT);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_MAX);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_MEDIUM);
        cbNodeBox.getSelectionModel().selectFirst();
        cbNodeBox.setTooltip(new Tooltip("choose the type first then click the maze"));
        hboxNodeBox.getChildren().addAll(txtNodeBox1, cbNodeBox, txtNodeBox2);

        // solver box

        VBox vboxAlgorithmBox = new VBox(padding);
        vboxAlgorithmBox.setAlignment(Pos.CENTER);

        HBox hboxAlgorithmTxt = new HBox(padding);
        hboxAlgorithmTxt.setAlignment(Pos.BASELINE_CENTER);
        Text txtAlgorithms = new Text("Maze Solver Algorithm");
        txtAlgorithms.setFont(defaultFont);



        hboxAlgorithmTxt.getChildren().addAll(txtAlgorithms);
        HBox hboxcbAlgorithmBox = new HBox(padding);
        hboxcbAlgorithmBox.setAlignment(Pos.CENTER);
        cbAlgorithmBox = new ComboBox(FXCollections.observableArrayList(SolvingAlgorithm.Algorithms.values()));
        cbAlgorithmBox.getSelectionModel().selectFirst();
        cbAlgorithmBox.setTooltip(new Tooltip("Algorithm"));
        cbHeuristicBox = new ComboBox(FXCollections.observableArrayList(AStarAlgorithm.Heuristic.values()));
        cbHeuristicBox.getSelectionModel().selectFirst();
        cbHeuristicBox.setTooltip(new Tooltip("for A* algorithm"));
        cbHeuristicBox.setDisable(true);
        hboxcbAlgorithmBox.getChildren().addAll(cbAlgorithmBox);
        vboxAlgorithmBox.getChildren().addAll(hboxAlgorithmTxt, hboxcbAlgorithmBox);
        
        // Add Weights & Walls Pane not finish
        VBox vboxObstacles = new VBox(padding);
        vboxObstacles.setStyle(defaultHboxStyle);
        HBox hboxObstacles = new HBox(padding);
        hboxObstacles.setAlignment(Pos.CENTER);



        HBox hboxAddWeights = new HBox(padding);
        hboxAddWeights.setAlignment(Pos.CENTER);
        btnAddWeights = new Button("WEIGHTS");
        btnAddWeights.setTooltip(new Tooltip("Adds random weights to all tiles"));
        btnAddWalls = new Button("WALLS");
        btnAddWalls.setTooltip(new Tooltip("Adds random walls to the grid"));
        hboxAddWeights.getChildren().addAll(btnAddWalls, btnAddWeights);
        vboxObstacles.getChildren().addAll(hboxObstacles, hboxAddWeights);
        

        // Util buttons Pane  touch to solve the maze
        HBox hboxUtilBtns = new HBox(padding);
        hboxUtilBtns.setAlignment(Pos.CENTER);
        hboxUtilBtns.setStyle(defaultHboxStyle);
        btnClear = new Button("Clear");
        btnClear.setTooltip(new Tooltip("Clean the grid"));

        btnRun = new Button("Solve");
        btnRun.setTooltip(new Tooltip("Run Maze Solver Algorithm"));
        hboxUtilBtns.getChildren().addAll(btnRun, btnClear);
        

        rightPane.getChildren().addAll(intro, vboxCreateGrid, vboxMaze,hboxNodeBox, vboxAlgorithmBox, hboxUtilBtns);
        // EndRegion: RightPane
        
        //  Create scene
        // Window dimensions
        int WIDTH = 1100;
        int HEIGHT = 750;
        this.scene = new Scene(initComponents(), WIDTH, HEIGHT);
    }
    
    public void setTriggers(Controller controller)
    {
        // Changes type of tile on click
        cbNodeBox.setOnAction((event) -> 
        {
            FXCollections.observableArrayList(Tile.Type.values()).stream().filter((item) -> (cbNodeBox.getValue().toString().equals(item.toString()))).forEachOrdered(controller::doChangeClickType);
        });
        

        // Clear button clears the grid
        btnClear.setOnAction((event) ->
        {
            controller.doClearGrid();
        });

        

        // Generates a random maze
        btnMaze.setOnAction((event) ->
        {
            FXCollections.observableArrayList(MazeGenerationAlgorithm.MazeGen.values()).stream().filter((item) -> (cbMazeGenBox.getValue().toString().equals(item.toString()))).forEachOrdered((item) ->
            {
                if(gridPane != null)
                    controller.doGenerateMaze(item);
            });
        });
        
        // Initialized the grid
        btnCreateGrid.setOnAction((event) ->
        {
            // Makes sure maze is only generated with odd x and y
            int x = Integer.parseInt(txtXTiles.getText());
            x = (x % 2 == 0) ? x - 1 : x; 
            int y = Integer.parseInt(txtYTiles.getText());
            y = (y % 2 == 0) ? y - 1 : y;
            int size = Integer.parseInt(txtTileSize.getText());

            parentGridPane.getChildren().remove(gridPane);
            
            // Initializes the grid
            model.gridInit(x, y, size);
            this.fillGrid(model.getGrid());
        });

        
        // Run solver algorithms
        btnRun.setOnAction((event) -> 
        {
            SolvingAlgorithm.Algorithms algorithm = null;
            AStarAlgorithm.Heuristic heuristic = null;
            
            for(SolvingAlgorithm.Algorithms algo : FXCollections.observableArrayList(SolvingAlgorithm.Algorithms.values()))
            {
                if(algo == cbAlgorithmBox.getValue())
                {
                    algorithm = algo;
                }
            }
            
            for(AStarAlgorithm.Heuristic heur : FXCollections.observableArrayList(AStarAlgorithm.Heuristic.values()))
            {
                if(heur == cbHeuristicBox.getValue())
                {
                    heuristic = heur;
                }
            }
            
            boolean success;
            if(algorithm != null && heuristic != null)
            {
                try 
                {
                    success = controller.doShortestPathAlgorithm(algorithm, heuristic);
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(Pane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    /**
     * Returns this view's scene
     * @return Scene
     */
    public Scene getScene()
    {
        return this.scene;
    }
    
    /**
     * Creates a new grid 
     * This method was created to be called in controller constructor,
     * so we don't have to manually create a new grid on load
     */
    public void createGrid()
    {
        btnCreateGrid.fire();
    }
    
    /**
     * Initializes the components of the view. Meant to be called once in constructor
     * @return Pane with components initialized
     */
    private SplitPane initComponents()
    {
        VBox root = new VBox();
        
        //fillGrid(model.getGrid());
        root.getChildren().add(this.parentGridPane);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(root, this.rightPane);

        double rightPanelSize = 0.30;
        this.rightPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(rightPanelSize));
        this.rightPane.minWidthProperty().bind(splitPane.widthProperty().multiply(rightPanelSize));
        
        return splitPane;
    }
    
    /**
     * Build the grid with the model values
     */
    private void fillGrid(Tile[][] tiles)
    {
        this.gridPane = new javafx.scene.layout.Pane();
        for(Tile[] row : tiles)
        {
            for(Tile tile: row)
            {
                gridPane.getChildren().add(tile.getStackPane());
            }
        }
        this.parentGridPane.getChildren().add(gridPane);
    }

    /**
     * Listens for changes in the model
     * @param o Observable object
     * @param arg Object argument
     * not finish just a listener
     */
   @Override
    public void update(Observable o, Object arg)
    {

    }
}
