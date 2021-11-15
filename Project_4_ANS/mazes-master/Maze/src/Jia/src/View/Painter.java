/**
 * This class that handles the coloring of any element in the grid.
 * @author Jiajun Guo
 */
package Jia.src.View;

import Jia.src.Cell.Grid;
import Jia.src.Cell.Tile;
import Jia.src.Algorithm.MazeSolverAlgorithm.SolvingAlgorithm;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class Painter
{
    private static final Painter INSTANCE = new Painter();
    private final Executor executor;
    
    private Painter()
    {
        executor = Executors.newSingleThreadExecutor();
    }
    
    /**
     * Returns the single instance of this class
     */
    public static Painter getInstance()
    {
        return INSTANCE;
    }
    
    /**
     * Draws a path from start to end, given a list of tiles
     */
    public void drawPath(List<Tile> path, Grid model) 
    {
        this.executor.execute(
        () ->
        {
            path.stream().filter((tile) -> !(tile == model.getEnd() || tile == model.getStart())).map((tile) ->
            {
                tile.setAttributes(Tile.Type.PATH, tile.getWeight());
                return tile;                
            }).forEachOrdered((_item) ->
            {
                try
                {
                    Thread.sleep(20);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
    }
    
    /**
     * Returns a runnable object that colors a single Tile and sleeps for 'sleep' ms
     * @param tile Tile to color
     * @param end We pass this to avoid overriding it
     * @param start We pass this to avoid overriding it
     * @param type Color to paint the tile
     * @param sleep time to wait before next task in thread
     */
    public void drawTile(Tile tile, Tile end, Tile start, Tile.Type type, long sleep)
    {
        this.executor.execute(()->
        {
            if(tile != end && tile != start)
                tile.setAttributes(type, tile.getWeight());
           
            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(SolvingAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    /**
     * Highlights a tile for 'this.painterWait' seconds, before turning it back to empty
     * @param tile Tile to highlight
     * @param waitTime time to wait before next step
     */
    public void highlightTile(Tile tile, long waitTime)
    {
        this.drawTile(tile, null, null, Tile.Type.HIGHLIGHT, waitTime);
        this.drawTile(tile, null, null, Tile.Type.EMPTY, waitTime);
    }
    

    
    /**
     * Clears the previously assigned visited and path tiles
     */
    public void clearPath(Grid model)
    {
        this.executor.execute(()->
        {
            Tile tile;
            for(int y = 0; y < model.getYSize(); y++)
            {
                for(int x = 0; x < model.getXSize(); x++)
                {
                    tile = model.getGrid()[x][y]; 
                    if(tile.getType() == Tile.Type.PATH || tile.getType() == Tile.Type.VISITED)
                    {
                        tile.setAttributes(Tile.Type.EMPTY, tile.getWeight());
                    }
                }
            }
        });
    }
}
