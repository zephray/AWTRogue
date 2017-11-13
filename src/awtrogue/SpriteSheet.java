/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awtrogue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author ZephRay
 */
public class SpriteSheet {
    private final int gridWidth, gridHeight;
    private BufferedImage sheet;
    
    public SpriteSheet(int gridWidth, int gridHeight, String path) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        try {
            sheet = ImageIO.read(new File(path));  
        } catch (IOException e) {
            System.err.print("Unable to open file " + path );
        }
    }
    
    public BufferedImage getSprite(int xPos, int yPos, int width, int height) {
        int xOffset = xPos * gridWidth;
        int yOffset = yPos * gridHeight;
        
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        for (int ySrc = yOffset, yDst = 0; ySrc < yOffset + height; ySrc++, yDst++) {
            for (int xSrc = xOffset, xDst = 0; xSrc < xOffset + width; xSrc++, xDst++) {
                temp.setRGB(xDst, yDst, sheet.getRGB(xSrc, ySrc));
            }
        }
        
        return temp;
    }
    
}
