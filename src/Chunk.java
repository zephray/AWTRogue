import java.util.ArrayList;
import java.awt.Point;

/**
 * Chunk
 * @author Wenting Zhang Assignment 5
 */
public class Chunk 
{
    public Point startPosition;
    public ArrayList<Tile> tiles;
    
    public Chunk(Point startPosition) 
    {
        this.startPosition = startPosition;
        tiles = new ArrayList<>();
    }
    
    public Tile getTileAt(Point position) 
    {
        for (Tile tile: tiles) 
        {
            if ((tile.position.x == position.x)&&(tile.position.y == position.y)) 
            {
                return tile;
            }
        }
        return null;
    }
}
