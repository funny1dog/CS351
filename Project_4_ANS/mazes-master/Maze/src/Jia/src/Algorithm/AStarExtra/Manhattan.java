/**
 * This class that computer A*-Euclidean.
 * Standard Heuristic for a square grid.
 * @author Jiajun Guo, Steven Chen
 */
package Jia.src.Algorithm.AStarExtra;

import Jia.src.Cell.Tile;


public class Manhattan extends Heuristic
{

    public Manhattan()
    {
        super();
    }
    
    @Override
    public double computeHeuristic(Tile start, Tile end)
    {
        double D = 1.0;
        double dx = Math.abs(start.getX() - end.getX());
        double dy = Math.abs(start.getY() - end.getY());
        
        return D * (dx + dy);
    }
}
