
package Jia.src.Algorithm.MazeSolverAlgorithm;

import Jia.src.Cell.Grid;
import Jia.src.Cell.Tile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author
 */
public class WallFollowerAlgorithm extends SolvingAlgorithm
{
    public WallFollowerAlgorithm()
    {
        super();
    }

    @Override
    public int runPathfinder(Grid grid, List<Tile> path)
    {
        Tile start = grid.getStart();
        Tile end = grid.getEnd();
        
        HashMap<Tile, Tile> parents = new HashMap();
        HashMap<Tile, Integer> walls = new HashMap();
        path.clear();
        
        executeWallFollower(grid, parents, walls);
        
        int cost = walls.get(end);
        
        Tile tile = end;

        /**
         * If cost is infinite, means it didn't find the end
         */

        if(cost != Integer.MAX_VALUE)
        {
            do{
                path.add(0, tile);
                tile = parents.get(tile);
            } while (tile != start);
            
            this.statistics.setPathFound(true, cost);
        }
        
        return cost;
    }
    
    /**
     * Executes wall follower's algorithm on the 'grid'
     * @param grid Model with grid consisting of Tile[][]
     * @param parents empty map to store parents of tiles, to allow path reconstruction
     * @param walls empty map to keep track of weights
     */
    private void executeWallFollower(Grid grid,
                    HashMap<Tile, Tile> parents,
                    HashMap<Tile, Integer> walls)
    {
        Tile start = grid.getStart();
        
        // Init all tiles
        Set<Tile> unvisited = new HashSet();
        
        grid.getTiles().stream().filter((tile) -> !(tile.isWall())).map((tile) ->
        {
            unvisited.add(tile);
            return tile;
        }).map((tile) ->
        {
            walls.put(tile, Integer.MAX_VALUE);
            return tile;
        }).forEachOrdered((tile) ->
        {
            parents.put(tile, null);
        });
        walls.put(start, 0);
        
        // Compute weights
        while(!unvisited.isEmpty())
        {
            Tile lowCostTile = getMinWeight(unvisited, walls);
            
            // If we ever get a lower cost that equals infinity, it means we're stuck
            if(walls.get(lowCostTile) == Integer.MAX_VALUE)
                break;
            
            // Paints current tile and updates statistics for visited tiles
            painter.drawTile(lowCostTile, grid.getEnd(), start, Tile.Type.HIGHLIGHT, 1);
            this.statistics.incrementVisited();
            
            // Remove current tile from unvisited set
            unvisited.remove(lowCostTile);
            
            // Get neighbors
            List<Tile> neighbors = grid.getTileNeighbors(lowCostTile);
            
            for(Tile tile : neighbors)
            {
                if(unvisited.contains(tile))
                {
                    int weight = tile.getWeight() + walls.get(lowCostTile);
                    if(walls.get(tile) > weight)
                    {
                        walls.put(tile, weight);
                        parents.put(tile, lowCostTile);
                    }
                }
            }
            painter.drawTile(lowCostTile, grid.getEnd(), start, Tile.Type.VISITED, 1);
        }
    }
    
    /**
     * Return the min wall Tile in  the unvisited set
     * @param unvisited
     * @param walls
     * @return 
     */
    private Tile getMinWeight(Set<Tile> unvisited, HashMap<Tile, Integer> walls)
    {
        double minWall = Integer.MAX_VALUE;
        Tile minWallTile = null;
        
        for(Tile tile : unvisited)
        {
            if(walls.get(tile) <= minWall)
            {
                minWallTile = tile;
                minWall = walls.get(tile);
            }
        }
        
        return minWallTile;
    }
}