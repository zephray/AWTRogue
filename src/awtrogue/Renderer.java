package awtrogue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

/**
 * Renderer
 * @author Wenting Zhang Assignment 5
 */
public class Renderer 
{
    private final Screen screen;
    private final BufferedImage screenImage;
    
    public Renderer(Screen screen) 
    {
        this.screen = screen;
        this.screenImage = screen.getBuffer();
    }
    
    public void clear() 
    {
        for (int y = 0; y < screenImage.getHeight(); y++) 
        {
            for (int x = 0; x < screenImage.getWidth(); x++) 
            {
                screenImage.setRGB(x, y, 0xff000000);
            }
        }
    }
    
    public void draw(int x, int y, BufferedImage image) 
    { 
        Graphics2D g2 = screenImage.createGraphics();
        g2.drawImage(image, null, x, y);
        g2.dispose();
    }
}
