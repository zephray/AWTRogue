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
public class Renderer {
    private final Screen screen;
    private final BufferedImage screenImage;
    
    public Renderer(Screen screen) {
        this.screen = screen;
        this.screenImage = screen.getBuffer();
    }
    
    public void draw(int x, int y, BufferedImage image) { 
        for (int ySrc = 0; ySrc < image.getHeight(); ySrc ++) {
            for (int xSrc = 0; xSrc < image.getWidth(); xSrc ++) {
                screenImage.setRGB(x + xSrc, y + ySrc, image.getRGB(xSrc, ySrc));
            }
        }  
    }
}
