import java.util.*;
import java.awt.Point;

/**
 * Map
 * @author Wenting Zhang Assignment 5
 */
public class Map 
{
    private final int CHUNK_WIDTH = 20;
    private final int CHUNK_HEIGHT = 20;
    
    private ArrayList<Layer> layers;
    
    public Map() 
    {
        layers = new ArrayList<>();
    }
    
    public void addTile(int layerId, Tile tile) 
    {
        try 
        {
            Layer layer = layers.get(layerId);
            layer.addTile(tile);
        } catch(IndexOutOfBoundsException e) 
        {
            Layer layer = new Layer();
            layer.addTile(tile);
            layers.add(layer);
        } 
    }
    
    public ArrayList<Tile> getTiles(int layerId, Point cameraStart, Point cameraEnd) 
    {
        return layers.get(layerId).getTiles(cameraStart, cameraEnd);
    }
    
    public Layer getLayer(int layerId) 
    {
        return layers.get(layerId);
    }
    
    public Tile getTileAt(int layerId, Point position) 
    {
        return layers.get(layerId).getTileAt(position);
    }
}
