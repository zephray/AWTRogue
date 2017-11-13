package awtrogue;

import java.awt.Point;

/**
 * Portal
 * @author Wenting Zhang Assignment 5
 */
public class Portal
{
    public Point tile;
    public Point destination;
    
    public Portal(Point tile)
    {
        this.tile = tile;
    }
    
    public void connect(Point destination)
    {
        this.destination = destination;
    }
}
