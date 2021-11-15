/**
 * This class that computer A*-Pythagoras.
 * @author Jiajun Guo, Steven Chen
 */
package Jia.src.Algorithm.AStarExtra;

import Jia.src.Cell.Tile;


public class Pythagoras extends Heuristic
{
    public Pythagoras()
    {
        super();
    }
    
    @Override
    public double computeHeuristic(Tile start, Tile end)
    {
        return Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) + (start.getY() - end.getY()) * (start.getY() - end.getY()));
    }
}
