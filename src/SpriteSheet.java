import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

/**
 * SpriteSheet
 * @author Wenting Zhang Assignment 5
 */
public class SpriteSheet
{
    private BufferedImage sheet;
    private ArrayList<BufferedImage> sprites;
    
    public SpriteSheet(int gridWidth, int gridHeight, String path)
    {
        try
        {
            sheet = ImageIO.read(getClass().getResource(path));  
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        sprites = new ArrayList<>();
        int xCount = sheet.getWidth() / gridWidth;
        int yCount = sheet.getHeight() / gridHeight;
        for (int yPos = 0; yPos < yCount; yPos++) 
        {
            for (int xPos = 0; xPos < xCount; xPos++) 
            {
                int xOffset = xPos * gridWidth;
                int yOffset = yPos * gridHeight;
        
                BufferedImage temp = new BufferedImage(gridWidth, gridHeight, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2 = temp.createGraphics();
                g2.drawImage(sheet, null, -xOffset, -yOffset);
                g2.dispose();
                sprites.add(temp);
            }
        }
    }
    
    public BufferedImage getSprite(int spriteId)
    {
        return sprites.get(spriteId);
    }    
}
