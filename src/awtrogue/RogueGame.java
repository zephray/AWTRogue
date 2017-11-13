/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awtrogue;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ZephRay
 */
public class RogueGame extends Game {
   // public static final SpriteSheet SPRITE_SHEET = new SpriteSheet(8,8, "");
    public static BufferedImage splashScreen;
    
    
    public RogueGame() {
        super(800, 600);
        
        setMaxFps(60);
        setMaxTps(10);
        
        try {
            splashScreen = ImageIO.read(new File("res/start.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void init() {
        
    }
    
    @Override
    public void tick() {
        if (getKeyboard().isKeyDown(KeyEvent.VK_W)) {
            //pressed W
        }
    }
    
    @Override
    public void render(double interval) {
        renderer.draw(0, 0, splashScreen);
        screen.swapBuffer();
    }
    
    @Override
    public void clean() {
        
    }
}
