package awtrogue;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * RogueGame
 * @author Wenting Zhang Assignment 5
 */
public class RogueGame extends Game
{
    //Game Resources
    private BufferedImage titleImage;
    private BufferedImage loseImage;
    private BufferedImage winImage;
    private BufferedImage backgroundImage;
    private BufferedImage foregroundImage;
    private SpriteSheet spriteSheet;
    
    //Game internal status
    private Unit player;
    private ArrayList<Unit> enemies;
    private ArrayList<Unit> deadEnemies;
    private ArrayList<Portal> portals;
    private int portalsPassed;
    private GameScreen gameScreen;
    private Boolean isAnimating;
    private Random rand;
    //private static ArrayList<Room> rooms;
    //pressed keys
    //rng
    //winning portal
    
    public RogueGame()
    {
        super(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        
        setMaxFps(60);
        setMaxTps(60);
        
        try
        {
            titleImage = ImageIO.read(new File("res/title.png"));
            loseImage = ImageIO.read(new File("res/lose.png"));
            winImage = ImageIO.read(new File("res/win.png"));
            backgroundImage = ImageIO.read(new File("res/background.png"));
            foregroundImage = ImageIO.read(new File("res/foreground.png"));
            spriteSheet = new SpriteSheet(40, 40, "res/sprite.png");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        gameScreen = GameScreen.TITLE;
        player = new Unit(new Point(0,0), 
                Constants.PLAYER_MAX_HP, Constants.PLAYER_INITIAL_ATK);
        enemies = new ArrayList<Unit>();
        deadEnemies = new ArrayList<Unit>();
        portals = new ArrayList<Portal>();
        //rooms = new ArrayList<Room>();
        isAnimating = false;
        rand = new Random();
        
        enemies.add(new Unit(new Point(5,5), 20, 5));
    }
    
    @Override
    public void init()
    {
        
    }
    
    @Override
    public void tick()
    {
        if (isAnimating)
        {
            isAnimating = false;
            if (player.isAnimationPlaying()) 
            {
                isAnimating = true;
                player.tick();
            } else
            {
                for (Unit enemy : enemies)
                {
                    if (enemy.isAnimationPlaying())
                    {
                        isAnimating = true;
                        enemy.tick();
                    }
                }
            }
        }
        switch (gameScreen)
        {
            case TITLE:
                if (getKeyboard().isKeyDown(KeyEvent.VK_ENTER))
                {
                    gameScreen = GameScreen.GAME;
                    getKeyboard().clearKeyDown(KeyEvent.VK_ENTER);
                }
                break;
            case WIN:
            case LOSE:
                if (getKeyboard().isKeyDown(KeyEvent.VK_ENTER))
                {
                    gameScreen = GameScreen.TITLE;
                    getKeyboard().clearKeyDown(KeyEvent.VK_ENTER);
                }
            case GAME:
                if ((getKeyboard().isKeyDown(KeyEvent.VK_UP))|
                    (getKeyboard().isKeyDown(KeyEvent.VK_J)))
                {
                    makeMove(new Point(0, -1));
                    getKeyboard().clearKeyDown(KeyEvent.VK_UP);
                    getKeyboard().clearKeyDown(KeyEvent.VK_J);
                }
                if ((getKeyboard().isKeyDown(KeyEvent.VK_DOWN))|
                    (getKeyboard().isKeyDown(KeyEvent.VK_K)))
                {
                    makeMove(new Point(0, 1));
                    getKeyboard().clearKeyDown(KeyEvent.VK_DOWN);
                    getKeyboard().clearKeyDown(KeyEvent.VK_K);
                }
                if ((getKeyboard().isKeyDown(KeyEvent.VK_LEFT))|
                    (getKeyboard().isKeyDown(KeyEvent.VK_H)))
                {
                    makeMove(new Point(-1, 0));
                    getKeyboard().clearKeyDown(KeyEvent.VK_LEFT);
                    getKeyboard().clearKeyDown(KeyEvent.VK_H);
                }
                if ((getKeyboard().isKeyDown(KeyEvent.VK_RIGHT))|
                    (getKeyboard().isKeyDown(KeyEvent.VK_L)))
                {
                    makeMove(new Point(1, 0));
                    getKeyboard().clearKeyDown(KeyEvent.VK_RIGHT);
                    getKeyboard().clearKeyDown(KeyEvent.VK_L);
                }
                if ((getKeyboard().isKeyDown(KeyEvent.VK_SPACE))|
                    (getKeyboard().isKeyDown(KeyEvent.VK_PERIOD)))
                {
                    makeMove(new Point(0, 0));
                    getKeyboard().clearKeyDown(KeyEvent.VK_SPACE);
                    getKeyboard().clearKeyDown(KeyEvent.VK_PERIOD);
                }
                if (getKeyboard().isKeyDown(KeyEvent.VK_Y))
                {
                    makeMove(new Point(-1, -1));
                    getKeyboard().clearKeyDown(KeyEvent.VK_Y);
                }
                if (getKeyboard().isKeyDown(KeyEvent.VK_U))
                {
                    makeMove(new Point(1, -1));
                    getKeyboard().clearKeyDown(KeyEvent.VK_U);
                }
                if (getKeyboard().isKeyDown(KeyEvent.VK_B))
                {
                    makeMove(new Point(-1, 1));
                    getKeyboard().clearKeyDown(KeyEvent.VK_B);
                }
                if (getKeyboard().isKeyDown(KeyEvent.VK_N))
                {
                    makeMove(new Point(1, 1));
                    getKeyboard().clearKeyDown(KeyEvent.VK_N);
                }
                break;
        }
    }
    
    @Override
    public void render(double interval)
    {
        renderer.clear();
        switch (gameScreen)
        {
            case TITLE:
                renderer.draw(0,0, titleImage);
                break;
            case GAME:
                drawGame();
                break;
            case LOSE:
                renderer.draw(0, 0, loseImage);
                break;
            case WIN:
                renderer.draw(0, 0, winImage);
                break;
        }
        screen.swapBuffer();
    }
    
    @Override
    public void clean()
    {
        
    }
    
    private void drawGame()
    {
        Point offset = player.globalPos();
        offset.x -= (Constants.WINDOW_WIDTH / 2);
        offset.y -= (Constants.WINDOW_HEIGHT / 2);
        
        //Bodies
        for (Unit deadEnemy: deadEnemies)
        {
            Point deadEnemyPosition = deadEnemy.globalPos();
            renderer.draw(deadEnemyPosition.x - offset.x,
                deadEnemyPosition.y -offset.y,
                spriteSheet.getSprite(1, 
                        Constants.ENEMY_TEXURE_Y, 
                        Constants.TILE_SIZE,  Constants.TILE_SIZE));
        }
        
        //Portals
        for (Portal portal : portals)
        {
            Point portalPosition = portal.tile;
            renderer.draw(portalPosition.x - offset.x, 
                portalPosition.y -offset.y,
                spriteSheet.getSprite(0, 
                        Constants.PORTAL_TEXTURE_Y, 
                        Constants.TILE_SIZE,  Constants.TILE_SIZE));
        }
        
        //Enemy
        for (Unit enemy : enemies)
        {
            Point enemyPosition = enemy.globalPos();
            renderer.draw(enemyPosition.x - offset.x, enemyPosition.y -offset.y,
                spriteSheet.getSprite(0, 
                        Constants.ENEMY_TEXURE_Y, 
                        Constants.TILE_SIZE,  Constants.TILE_SIZE));
            renderer.draw(enemyPosition.x - offset.x, enemyPosition.y -offset.y,
                spriteSheet.getSprite(0, 
                        Constants.LIGHT_TEXTURE_Y, 
                        Constants.TILE_SIZE,  Constants.TILE_SIZE));
        }
        
        //Player
        Point playerPosition = player.globalPos();
        renderer.draw(playerPosition.x - offset.x, playerPosition.y - offset.y,
            spriteSheet.getSprite(0, 
                Constants.PLAYER_TEXTURE_Y,
                Constants.TILE_SIZE,  Constants.TILE_SIZE));
        renderer.draw(playerPosition.x - offset.x, playerPosition.y - offset.y,
            spriteSheet.getSprite(0, 
                Constants.LIGHT_TEXTURE_Y, 
                Constants.TILE_SIZE,  Constants.TILE_SIZE));
    }
    
    private void generateLevel()
    {
        
    }
    
    private void makeMove(Point delta)
    {
        if (isAnimating)
        {
            player.stopAnimation();
            for (Unit enemy: enemies)
            {
                enemy.stopAnimation();
            }
        }
        Point newPos = 
                new Point(player.tile.x + delta.x, player.tile.y + delta.y);
        isAnimating = true;
        
        //Collision check
        int enemyCollideIndicator = -1;
        for (Unit enemy: enemies)
        {
            if ((newPos.x == enemy.tile.x)&&(newPos.y == enemy.tile.y))
            {
                enemyCollideIndicator = enemies.indexOf(enemy);
            }
        }
        if (enemyCollideIndicator >= 0)
        {
            player.attack(delta, 0);
            if (rand.nextInt(Constants.MISS_RATE) != 0)
            {
                enemies.get(enemyCollideIndicator).takesDamage(player.damage);
                if (!(enemies.get(enemyCollideIndicator).isAlive()))
                {
                    deadEnemies.add(enemies.remove(enemyCollideIndicator));
                }
            }
        } else
        {
            //int enterPortalIndicator = -1;
            player.move(delta, 0);
        }
        
    }
}
