/**
 * Game
 * @author Wenting Zhang Assignment 5
 */
public abstract class Game
{
    protected final Screen screen;
    protected final Keyboard keyboard;
    protected final Renderer renderer;
    
    private final Thread gameThread;
    private final GameLoop gameLoop;
    
    protected int maxTps = 20, maxFps = 60;
    private volatile boolean running = false;
    
    public Game(int width, int height)
    {
        gameLoop = new GameLoop();
        gameThread = new Thread(gameLoop);
        
        screen = new Screen(width, height);
        keyboard = new Keyboard(screen);
        renderer = new Renderer(screen);
    }
    
    public final void run()
    {
        if (!running)
        {
            running = true;
            init();
            gameThread.start();
        }
    }
    
    public abstract void init();
    
    public abstract void tick();
    
    public abstract void render(double interval);
    
    public abstract void clean();
    
    public final void exit()
    {
        if (running)
        {
            running = false;
            clean();
            System.exit(0);
        }
    }
    
    public void setMaxFps(int MaxFps)
    {
        this.maxFps = (maxFps > 0) ? maxFps : 60;
    }
    
    public void setMaxTps(int maxTps)
    {
        this.maxTps = (maxTps > 0) ? maxTps : 60;
    }
    
    public Screen getScreen()
    {
        return screen;
    }
    
    public Keyboard getKeyboard()
    {
        return keyboard;
    }
    
    public boolean isRunning()
    {
        return running;
    }
       
    private class GameLoop implements Runnable
    {
        private final double NS_PER_SEC = 1000000000;
        
        @Override
        public void run()
        {
            double previous = System.nanoTime();
            double start = System.nanoTime();
            double lag = 0.0;
            double timing = 0.0;
            int frameCount = 0;
            int tickCount = 0;
            
            while (running)
            {
                double nsPerTick = NS_PER_SEC / maxTps;
                double nsPerFrame = NS_PER_SEC / maxFps;
                
                double current = System.nanoTime();
                double elapsed = current - previous;
                
                previous = current;
                lag += elapsed;
                timing += elapsed;
                
                while (lag >= nsPerTick)
                {
                    tick();
                    lag -= nsPerTick;
                    tickCount ++;
                }
                
                if (current - start >= nsPerFrame)
                {
                    start = System.nanoTime();
                    render(lag / nsPerTick);
                    frameCount ++;
                }
                
                if (timing >= NS_PER_SEC)
                {
                    timing = 0;
                    System.out.println("fps = " + frameCount + "(" + maxFps + ")" + ", tps = " + tickCount + "(" + maxTps + ")");
                    frameCount = 0;
                    tickCount = 0;
                }
            }
        }
    }
}
