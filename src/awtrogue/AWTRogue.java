/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awtrogue;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author ZephRay
 */
public class AWTRogue {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Game game = new RogueGame();
        
        JFrame window = new JFrame("Rogue-like Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(game.getScreen().getCanvas());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        game.run();
    
    }
    
}
