/**
 * This heuristic is best used if you can
 * move at any angle,instead of just following grid directions
 *
 * @author Jiajun Guo
 */
package Jia.src.Algorithm.AStarExtra;

import Jia.src.Cell.Tile;


public class Euclidean extends Heuristic
{

    public Euclidean()
    {
        super();
    }
    
    @Override
    public double computeHeuristic(Tile start, Tile end)
    {
        double D = 1.0;
        double dx = Math.abs(start.getX() - end.getX());
        double dy = Math.abs(start.getY() - end.getY());
        
        return D * Math.sqrt(dx * dx + dy * dy);
    }
    
}
