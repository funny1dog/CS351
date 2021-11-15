/**
 * This class that computer A*-Diagonal.
 * the grid allows for diagonal movement
 * D2 = 1.0, this is called the Chebyshev distance
 * D2 = sqrt(2), this is called called Octile distance
 *
 * @author Jiajun Guo, Steven Chen
 */
package Jia.src.Algorithm.AStarExtra;

import Jia.src.Cell.Tile;


public class Diagonal extends Heuristic
{

    public Diagonal()
    {
        super();
    }
    
    @Override
    public double computeHeuristic(Tile start, Tile end)
    {

        double D1 = 1.0;
        double D2 = 1.0; 
        
        double dx = Math.abs(start.getX() - end.getX());
        double dy = Math.abs(end.getY() - start.getY());
        
        return D1 * (dx + dy) + (D2 - 2 * D1) * Math.min(dx, dy);
    }
    
}
