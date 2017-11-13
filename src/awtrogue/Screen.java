package awtrogue;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Screen
 * @author Wenting Zhang Assignment 5
 */
public class Screen 
{
    private final Canvas canvas;
    
    private final int width, height;
    private final BufferedImage buffer;
    
    public Screen(int width, int height) 
    {
        this.width = width;
        this.height = height;
        
        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] pixels = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
       
        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(true);
    }
    
    public void swapBuffer() 
    {
        if (canvas.isDisplayable()) 
        {
            BufferStrategy bufferStrategy = canvas.getBufferStrategy();
            if (bufferStrategy == null) 
            {
                canvas.createBufferStrategy(2);
                return;
            }
            Graphics2D g = (Graphics2D)bufferStrategy.getDrawGraphics();
            g.drawImage(buffer, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
            g.dispose();
            
            bufferStrategy.show();
        }
    }
    
    protected BufferedImage getBuffer() 
    {
        return buffer;
    }
    
    public Canvas getCanvas() 
    {
        return canvas;
    }
    
    public int getWidth() 
    {
        return width;
    }
    
    public int getHeight() 
    {
        return height;
    }
}


