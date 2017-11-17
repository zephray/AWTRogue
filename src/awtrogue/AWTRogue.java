package awtrogue;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * AWTRogue
 * @author Wenting Zhang Assignment 5
 */
public class AWTRogue 
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Game game = new RogueGame();
        
        JFrame window = new JFrame("Rogue-like Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(game.getScreen().getCanvas());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        game.run(); 
        //game.init();//Use as unit test
    
    }
    
}
