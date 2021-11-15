
package Jia.src.Algorithm.MazeSolverAlgorithm;

import Jia.src.Cell.Grid;
import Jia.src.Cell.Tile;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class RandomMouseAlgorithm extends SolvingAlgorithm
{

    public RandomMouseAlgorithm()
    {
        super();
    }
    
    @Override
    protected int runPathfinder(Grid model, List<Tile> path)
    {
        HashMap<Tile, Integer> tileData = new HashMap<>();
        this.executeRandomMouse(model, tileData);
        
        this.buildPath(path, model, tileData);
        int cost = tileData.get(model.getEnd()) - 1;
        
        this.statistics.setPathFound(true, cost);
        this.painter.drawPath(path, model);
        
        return cost;
    }
    
    /**
     * Executes a Random Mouse algorithm to find the path between start and end points
     */
    private void executeRandomMouse(Grid model, HashMap<Tile, Integer> tileData)
    {
        // Keeps track of visited tiles
        Set<Tile> visited = new HashSet<>();
        // List of cells to be processed in the while iteration
        List<Tile> toProcess = new LinkedList<>();
        // List of unprocessed cells (collected neighbors of toProcess)
        List<Tile> unprocessed = new LinkedList<>();
        // Current distance from target node to current iterations
        int iteration = 0;
        
        unprocessed.add(model.getStart());
        visited.add(model.getStart());
        int DEFAULT_DISTANCE = 1;
        tileData.put(model.getStart(), DEFAULT_DISTANCE);
        
        while(!unprocessed.isEmpty())
        {
            iteration++;
            // shifts all elements from unprocced to toProcess 
            toProcess.addAll(unprocessed);
            unprocessed.clear();
            
            // Each iterations processed all neighbors from toProcess
            for(Tile tile : toProcess)
            {
                List<Tile> neighbors = model.getTileNeighbors(tile);
                
                for(Tile elem : neighbors)
                {
                    if(elem == null) continue;
                    if(elem.isWall()) continue;
                    if(visited.contains(elem)) continue;
                    
                    unprocessed.add(elem);
                    visited.add(elem);
                    this.statistics.incrementVisited();
                    tileData.put(elem, DEFAULT_DISTANCE + iteration);
                    
                    this.painter.drawTile(elem, model.getEnd(), model.getStart(), Tile.Type.HIGHLIGHT, 2);
                    this.painter.drawTile(elem, model.getEnd(), model.getStart(), Tile.Type.VISITED, 4);
                }
            }
        }
    }
    
    /**
     * Builds path based on hashmap distances collected from algorithm
     * @param path [out] path list to store the final path
     * @param model grid
     * @param data hashmap mapping tiles to their distances
     */
    private void buildPath(List<Tile> path, Grid model, HashMap<Tile, Integer> data)
    {
        Tile currentTile = model.getEnd();
         
        do{
            path.add(currentTile);
            Tile lowestCost = null;
            
            for(Tile tile : model.getTileNeighbors(currentTile))
            {
                if(tile == null) continue;
                if(tile.isWall()) continue;
                
                if(lowestCost == null)
                {
                    lowestCost = tile;
                    continue;
                }
                
                if(data.get(tile) < data.get(lowestCost))
                    lowestCost = tile;
            }
            currentTile = lowestCost;
        } while (currentTile != model.getStart());
        
        Collections.reverse(path);
    }
}
