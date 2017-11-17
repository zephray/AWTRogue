package awtrogue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;

/**
 * Renderer
 * @author Wenting Zhang Assignment 5
 */
public class Renderer 
{
    private final Screen screen;
    private final BufferedImage screenImage;
    private final Graphics2D g2;
    
    public Renderer(Screen screen) 
    {
        this.screen = screen;
        this.screenImage = screen.getBuffer();
        g2 = screenImage.createGraphics();
    }
    
    public void clear() 
    {
        g2.clearRect(0, 0, screenImage.getWidth(), screenImage.getHeight());
    }
    
    public void fillRect(int x, int y, int w, int h, Color c) 
    {
        g2.setColor(c);
        g2.fillRect(x, y, w, h);
    }
    
    public void draw(int x, int y, BufferedImage image) 
    { 
        g2.drawImage(image, null, x, y);
    }
}
