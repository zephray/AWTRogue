package awtrogue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard
 * @author Wenting Zhang Assignment 5
 */
public class Keyboard 
{
    public static final int NUM_KEYS = 256;
    
    private boolean[] downKeys = new boolean[NUM_KEYS];
    
    public Keyboard(Screen screen)
    {
        KeyboardEventListener keyboardEventListener = new KeyboardEventListener();
        screen.getCanvas().addKeyListener(keyboardEventListener);
    }
    
    public boolean isKeyDown(int keyCode)
    {
        return downKeys[keyCode];
    }
    
    public void clearKeyDown(int keyCode)
    {
        downKeys[keyCode] = false;
    }
    
    private class KeyboardEventListener implements KeyListener 
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            downKeys[e.getKeyCode()] = true;
            e.consume();
        }
        
        @Override
        public void keyPressed(KeyEvent e) 
        {
            downKeys[e.getKeyCode()] = true;
            e.consume();
        }
        
        @Override
        public void keyReleased(KeyEvent e)
        {
            downKeys[e.getKeyCode()] = false;
            e.consume();
        }
    }
}
