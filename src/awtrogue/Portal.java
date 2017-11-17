package awtrogue;

import java.awt.Point;

/**
 * Portal
 * @author Wenting Zhang Assignment 5
 */
public class Portal
{
    public Point position;
    public Point destination;
    public int id;
    
    public Portal(Point position)
    {
        this.position = position;
    }
    
    public void connect(Point destination)
    {
        this.destination = destination;
    }
}
