package Jia.src.View;

import Jia.src.Cell.Grid;
import Jia.src.Cell.Tile;
import Jia.src.Algorithm.MazeSolverAlgorithm.SolvingAlgorithm;
import Jia.src.Algorithm.Algo;
import Jia.src.Algorithm.AStarExtra.Heuristic;
import Jia.src.Algorithm.MazeGenerationAlgorithm.MazeGenerationAlgorithm;
import Jia.src.Algorithm.MazeSolverAlgorithm.AStarAlgorithm;

/**
 * This class is the control of the View
 * @author Jiajun Guo, Steven Chen
 */
public class Controller
{
    private final Grid model;
    private final Pane pane;
    
    public Controller(Grid model, Pane pane)
    {
        this.model = model;
        this.pane = pane;
        this.pane.setTriggers(this);
        this.pane.createGrid();
        

    }
    
    public void doClearGrid()
    {
        this.model.clearGrid();
    }
    
    public void doChangeClickType(Tile.Type type)
    {   
        this.model.changeClickType(type);
    }
    

    public void doGenerateMaze(MazeGenerationAlgorithm.MazeGen algorithm)
    {
        MazeGenerationAlgorithm mazeGenerationAlgorithm = Algo.getMazeGenStrategy(algorithm);
        this.model.generateRandomMaze(mazeGenerationAlgorithm);
    }
    
    public void doToggleTileCoords(boolean toAdd)
    {
        this.model.toggleCoords(toAdd);
    }
    
    public boolean doShortestPathAlgorithm(SolvingAlgorithm.Algorithms algorithm, AStarAlgorithm.Heuristic heuristic) throws InterruptedException
    {
        Heuristic heuristicStrategy = Algo.getHeuristicStrategy(heuristic);
        SolvingAlgorithm solvingAlgorithm = Algo.getSolverAlgorithm(algorithm, heuristicStrategy);
        return this.model.executePathfinding(solvingAlgorithm);
    }
}
