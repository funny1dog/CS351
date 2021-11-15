/**
 * This class returns a generator and solver Algorithm
 * @author Jiajun Guo, Steven Chen
 */
package Jia.src.Algorithm;

import Jia.src.Algorithm.MazeGenerationAlgorithm.DepthFirstSearchAlgorithm;
import Jia.src.Algorithm.MazeGenerationAlgorithm.MazeGenerationAlgorithm;
import Jia.src.Algorithm.AStarExtra.Diagonal;
import Jia.src.Algorithm.AStarExtra.Euclidean;
import Jia.src.Algorithm.AStarExtra.Heuristic;
import Jia.src.Algorithm.AStarExtra.Manhattan;
import Jia.src.Algorithm.AStarExtra.Pythagoras;
import Jia.src.Algorithm.MazeGenerationAlgorithm.KruskalAlgorithm;
import Jia.src.Algorithm.MazeGenerationAlgorithm.PrimAlgorithm;
import Jia.src.Algorithm.MazeSolverAlgorithm.AStarAlgorithm;
import Jia.src.Algorithm.MazeSolverAlgorithm.WallFollowerAlgorithm;
import Jia.src.Algorithm.MazeSolverAlgorithm.SolvingAlgorithm;
import Jia.src.Algorithm.MazeSolverAlgorithm.RandomMouseAlgorithm;


public class Algo
{
    // solver Algorithm
    public static SolvingAlgorithm getSolverAlgorithm(SolvingAlgorithm.Algorithms Algorithm, Heuristic heuristic)
    {
        switch(Algorithm)
        {
            case RandomMouse:
                return new WallFollowerAlgorithm();
            case AStar:
                return new AStarAlgorithm(false, heuristic);
            case AStarOptimal:
                return new AStarAlgorithm(true, heuristic);
            case WallFollower:
                return new RandomMouseAlgorithm();
            default:
                throw new IllegalArgumentException("Pathfinding algorithm not found!");
        }
    }
    
    // Heuristic for A*
    public static Heuristic getHeuristicStrategy(AStarAlgorithm.Heuristic Algorithm)
    {
        switch(Algorithm)
        {
            case Pythagoras:
                return new Pythagoras();
            case Manhattan:
                return new Manhattan();
            case Diagonal:
                return new Diagonal();
            case Eudclidean:
                return new Euclidean();
            default:
                throw new IllegalArgumentException("Heuristic strategy not found!");
        }
    }
    
    // Maze generation
    public static MazeGenerationAlgorithm getMazeGenStrategy(MazeGenerationAlgorithm.MazeGen Algorithm)
    {
        switch(Algorithm)
        {
            case DFS:
               return new DepthFirstSearchAlgorithm();
            case Kruskal:
                return new KruskalAlgorithm();
            case Prim:
                return new PrimAlgorithm();

            default:
                throw new IllegalArgumentException("Maze generation algorithm not found!");
        }
    }
}
