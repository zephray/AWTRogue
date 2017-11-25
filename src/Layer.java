/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.awt.Point;

/**
 *
 * @author zephray
 */
public class Layer 
{
    private final int CHUNK_WIDTH = 10;
    private final int CHUNK_HEIGHT = 10;
    private ArrayList<Chunk> chunks;
    
    public Layer() 
    {
        chunks = new ArrayList<>();
    }
    
    public void addTile(Tile tile) 
    {
        int chunkId = -1;
        for (Chunk chunk: chunks) 
        {
            if ((tile.position.x >= chunk.startPosition.x) &&
                (tile.position.x <= chunk.startPosition.x + CHUNK_WIDTH)&&
                (tile.position.y >= chunk.startPosition.y) &&
                (tile.position.y <= chunk.startPosition.y + CHUNK_HEIGHT)) 
            {
                chunkId = chunks.indexOf(chunk);
                break;
            }
        }
        if (chunkId < 0) 
        {
            chunks.add(new Chunk(new Point((tile.position.x / CHUNK_WIDTH) * CHUNK_WIDTH, 
                (tile.position.y / CHUNK_HEIGHT) * CHUNK_HEIGHT)));
            chunkId = chunks.size() - 1;
        }
        chunks.get(chunkId).tiles.add(tile);
    }
    
    public ArrayList<Tile> getTiles(Point cameraStart, Point cameraEnd) 
    {
        ArrayList<Tile> result = new ArrayList<>();
        
        for (Chunk chunk: chunks) 
        {
            if ((cameraEnd.x >= chunk.startPosition.x) && (cameraStart.x <= chunk.startPosition.x + CHUNK_WIDTH) &&
                (cameraEnd.y >= chunk.startPosition.y) && (cameraStart.y <= chunk.startPosition.y + CHUNK_HEIGHT)) 
            {
                result.addAll(chunk.tiles);
            }
        }
        
        return result;
    }
    
    public Tile getTileAt(Point position) 
    {
        for (Chunk chunk: chunks) 
        {
            if ((position.x >= chunk.startPosition.x) &&
                (position.x <= chunk.startPosition.x + CHUNK_WIDTH)&&
                (position.y >= chunk.startPosition.y) &&
                (position.y <= chunk.startPosition.y + CHUNK_HEIGHT)) 
            {
                return chunk.getTileAt(position);
            }
        }
        return null;
    }
}
