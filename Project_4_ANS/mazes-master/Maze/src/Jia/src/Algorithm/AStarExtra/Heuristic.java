/**
 * Heuristic to be used in the A* algorithm
 *
 * @author
 */
package Jia.src.Algorithm.AStarExtra;

import Jia.src.Cell.Tile;


public abstract class Heuristic
{
    //protected final boolean breakTies;
            
    public Heuristic()
    {
        
    }
    
    /**
     * Computes an heuristic
     * @param start
     * @param end
     * @return 
     */
    public abstract double computeHeuristic(Tile start, Tile end);
}
