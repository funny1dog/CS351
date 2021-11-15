
package Jia.src.Algorithm.MazeSolverAlgorithm;

import Jia.src.Cell.Grid;
import Jia.src.Cell.Counter;
import Jia.src.Cell.Tile;
import Jia.src.View.Painter;
import java.util.List;

/**
 * Strategy signature to apply a path finding algorithm
 * 
 * @author
 */
public abstract class SolvingAlgorithm
{
    protected Painter painter;
    protected Counter statistics;
    
    // Pathfinding algorithms
    public enum Algorithms{
        RandomMouse,
        AStar,
        AStarOptimal,
        WallFollower
    }
    
    public SolvingAlgorithm()
    {
        this.painter = Painter.getInstance();
    }
    
    /**
     * Template method for the pathfinding Algorithm
     * @param model [in] 
     * @param path [out] output parameter with the resulting shortest path
     * @return int cost of the found path 
     */
    public final int algorithm(Grid model, List<Tile> path)
    {
        // (...) Statistics
        long start = System.nanoTime();
        this.statistics = new Counter(model);
        this.statistics.setWallSize(model.getWallsAmount());
        
        // Runs pathfinding algorithm
        int cost = this.runPathfinder(model, path);

        // (...) Statistics
        long end = System.nanoTime();
        this.statistics.setElapsedTime(end - start);
        this.statistics.updateObservers();
        this.painter.drawPath(path, model);
        
        System.out.println(this.statistics);
        
        return cost;
    }
    
    /**
     * Solving algorithm
     * @param model Grid containing a root Tile and a target Tile
     * @param path path found between tiles
     * @return cost of the path found
     */
    protected abstract int runPathfinder(Grid model, List<Tile> path);
}