/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awtrogue;

/**
 *
 * @author ZephRay
 */
public class Game {
    protected final Screen screen;
    protected final Keyboard keyboard;
    
    private final Thread gameThread;
    private final GameLoop gameLoop;
    
    private int MaxTps = 20, maxFps = 60;
    private volatile boolean running = false;
    
    public Game(int width, int height) {
        gameLoop = new GameLoop();
        gameThread = new Thread(gameLoop);
        
        screen = new Screen(width, height);
        keyboard = new Keyboard(screen);
    }
    
    public final void run() {
        if (!running) {
            running = true;
            init();
            gameThread.start();
        }
    }
    
    public abstract void init();
    
    public abstract void tick();
    
    public abstract void render(double interval);
    
    public abstract void clean();
    
    public final void exit() {
        if (running) {
            running = false;
            clean();
            System.exit(0);
        }
    }
    
    private class GameLoop implements Runnable {
        
    }
}
