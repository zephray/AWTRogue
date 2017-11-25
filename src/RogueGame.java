import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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
    private int winningPortal;
    private GameStatus gameStatus;
    private Boolean isAnimating;
    private Random rng;
    private Map map;
    private BSP bsp;
    private ArrayList<Room> rooms;
    private ArrayList<Room> bridges;
    //pressed keys
    //rng
    //winning portal
    
    public RogueGame()
    {
        super(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        
        maxFps = 30;
        maxTps = 60;
        
        try
        {
            titleImage = ImageIO.read(getClass().getResource("res/title.png"));
            loseImage = ImageIO.read(getClass().getResource("res/lose.png"));
            winImage = ImageIO.read(getClass().getResource("res/win.png"));
            foregroundImage = ImageIO.read(getClass().getResource("res/foreground.png"));
            backgroundImage = ImageIO.read(getClass().getResource("res/background.jpg"));
            spriteSheet = new SpriteSheet(Constants.TILE_SIZE, Constants.TILE_SIZE, "res/sprite.png");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        gameStatus = GameStatus.TITLE;
    }
    
    @Override
    public void init()
    {
        
    }
    
    @Override
    public void tick()
    {
        switch (gameStatus)
        {
            case TITLE:
                if (getKeyboard().isKeyDown(KeyEvent.VK_ENTER))
                {
                    gameStatus = GameStatus.GAME;
                    generateLevel();
                    getKeyboard().clearKeyDown(KeyEvent.VK_ENTER);
                }
                break;
            case WIN:
            case LOSE:
                if (getKeyboard().isKeyDown(KeyEvent.VK_ENTER))
                {
                    gameStatus = GameStatus.TITLE;
                    getKeyboard().clearKeyDown(KeyEvent.VK_ENTER);
                }
            case GAME:
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
        switch (gameStatus)
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
        
        //Background
        renderer.draw(Constants.WINDOW_WIDTH - backgroundImage.getWidth() / 2 - player.globalPos().x / 2, 
            Constants.WINDOW_HEIGHT - backgroundImage.getHeight() / 2 - player.globalPos().y / 2, backgroundImage);
        
        //Map
        ArrayList<Tile> tiles = map.getTiles(0,
                new Point(offset.x / Constants.TILE_SIZE, offset.y / Constants.TILE_SIZE), 
                new Point((offset.x + Constants.WINDOW_WIDTH) / Constants.TILE_SIZE,
                        (offset.y + Constants.WINDOW_HEIGHT) / Constants.TILE_SIZE));
        for (Tile tile: tiles) 
        {
            renderer.draw(tile.position.x * Constants.TILE_SIZE - offset.x, 
                tile.position.y * Constants.TILE_SIZE - offset.y, 
                spriteSheet.getSprite(tile.id));
        }
        
        //Bodies
        for (Unit deadEnemy: deadEnemies)
        {
            Point deadEnemyPosition = deadEnemy.globalPos();
            renderer.draw(deadEnemyPosition.x - offset.x,
                deadEnemyPosition.y -offset.y,
                spriteSheet.getSprite(Constants.SPRITE_DEAD_ENEMY));
        }
        
        //Portals
        for (Portal portal : portals)
        {
            Point portalPosition = portal.position;
            renderer.draw(portalPosition.x * Constants.TILE_SIZE - offset.x, 
                portalPosition.y * Constants.TILE_SIZE - offset.y,
                spriteSheet.getSprite(Constants.SPRITE_PORTAL));
        }
        
        //Enemy
        for (Unit enemy : enemies)
        {
            Point enemyPosition = enemy.globalPos();
            renderer.draw(enemyPosition.x - offset.x, enemyPosition.y -offset.y,
                spriteSheet.getSprite(Constants.SPRITE_ENEMY));
            renderer.draw(enemyPosition.x - offset.x, enemyPosition.y -offset.y,
                spriteSheet.getSprite(Constants.SPRITE_LIGHT));
        }
        
        //Player
        Point playerPosition = player.globalPos();
        renderer.draw(playerPosition.x - offset.x, playerPosition.y - offset.y,
            spriteSheet.getSprite(player.getDirection() * 4 + ((player.isAnimationPlaying())?(player.getAnimationTick()/4%3):(0))));
        /*renderer.draw(playerPosition.x - offset.x, playerPosition.y - offset.y,
            spriteSheet.getSprite(Constants.SPRITE_PLAYER_DOWN));*/
        renderer.draw(playerPosition.x - offset.x, playerPosition.y - offset.y,
            spriteSheet.getSprite(Constants.SPRITE_LIGHT));
        
        //foreground
        renderer.draw(0, 0, foregroundImage);
        
        //HP and MANA
        renderer.fillRect(10, 10, player.hp * 5 + 1, 20, Color.RED);
        renderer.fillRect(10, 40, portalsPassed * 20 + 1, 20, Color.YELLOW);
        
    }
    
    private void generateLevel()
    {
        map = new Map();
        enemies = new ArrayList<>();
        deadEnemies = new ArrayList<>();
        portals = new ArrayList<>();
        isAnimating = false;
        portalsPassed = 0;
        rng = new Random();
        bsp = new BSP(new Point(0, 0), new Point(Constants.MAP_WIDTH, Constants.MAP_HEIGHT), 0, rng);
        bsp.generateChild(Constants.BSP_DEPTH);
        bsp.generateRoom();
        rooms = bsp.getRoom();
        int startRoom = rng.nextInt(rooms.size());
        int startX = rng.nextInt(rooms.get(startRoom).end.x - rooms.get(startRoom).begin.x) 
                + rooms.get(startRoom).begin.x;
        int startY = rng.nextInt(rooms.get(startRoom).end.y - rooms.get(startRoom).begin.y) 
                + rooms.get(startRoom).begin.y;
        player = new Unit(new Point(startX, startY), 
                Constants.PLAYER_MAX_HP, Constants.PLAYER_INITIAL_ATK);
        
        for (Room room: rooms) 
        {
            Point enemyPosition = new Point(
                rng.nextInt(room.end.x - room.begin.x) + room.begin.x,
                rng.nextInt(room.end.y - room.begin.y) + room.begin.y);
            enemies.add(new Unit(enemyPosition, Constants.ENEMY_MAX_HP, Constants.ENEMY_INITIAL_ATK));
            Point portalPosition = new Point(
                rng.nextInt(room.end.x - room.begin.x) + room.begin.x,
                rng.nextInt(room.end.y - room.begin.y) + room.begin.y);
            portals.add(new Portal(portalPosition));
            for (int x = room.begin.x; x < room.end.x; x++) 
            {
                for (int y = room.begin.y; y < room.end.y; y++) 
                {
                    map.addTile(0, new Tile(new Point(x, y), Constants.TILE_FLOOR_BASE + rng.nextInt(Constants.TILE_FLOOR_COUNT)));
                }
            }
        }
        
        bridges = bsp.getBridge();
        for (Room bridge: bridges) 
        {
            for (int x = bridge.begin.x; x < bridge.end.x; x++) 
            {
                for (int y = bridge.begin.y; y < bridge.end.y; y++) 
                {
                    map.addTile(0, new Tile(new Point(x, y), Constants.TILE_FLOOR_BASE + rng.nextInt(Constants.TILE_FLOOR_COUNT)));
                }
            }
        }
        
        int portalId = 0;
        for (Portal portal: portals) 
        {
            int targetRoom = rng.nextInt(rooms.size());
            portal.destination = new Point(
                rng.nextInt(rooms.get(targetRoom).end.x - rooms.get(targetRoom).begin.x) 
                + rooms.get(targetRoom).begin.x,
                rng.nextInt(rooms.get(targetRoom).end.y - rooms.get(targetRoom).begin.y) 
                + rooms.get(targetRoom).begin.y);
            portal.id = portalId ++;
        }
        
        winningPortal = rng.nextInt(portals.size());

        System.out.println("Room count = " + rooms.size());
        System.out.println("Winning portal = " + winningPortal);
    }
    
    private Point addPoints(Point a, Point b)
    {
        return new Point(a.x + b.x, a.y + b.y);
    }
    
    private void makeMove(Point delta)
    {
        if (isAnimating)
        {
            if ((player.getAnimationType() == Animation.ATTACK)&&player.isAnimationPlaying()) return;
            player.stopAnimation();
            for (Unit enemy: enemies)
            {
                enemy.stopAnimation();
            }
        }
        Point newPos = 
                new Point(player.position.x + delta.x, player.position.y + delta.y);
        if (map.getTileAt(0, newPos) == null) return;
        isAnimating = true;
        
        //Collision check
        int enemyCollideId = -1;
        for (Unit enemy: enemies)
        {
            if ((newPos.x == enemy.position.x)&&(newPos.y == enemy.position.y))
            {
                enemyCollideId = enemies.indexOf(enemy);
            }
        }
        if (enemyCollideId >= 0)
        {
            player.attack(delta, 0);
            if (rng.nextInt(Constants.MISS_RATE) != 0)
            {
                enemies.get(enemyCollideId).takesDamage(player.damage);
                if (!(enemies.get(enemyCollideId).isAlive()))
                {
                    deadEnemies.add(enemies.remove(enemyCollideId));
                }
            }
        } else
        {
            int portalEnterId = -1;
            for (Portal portal: portals) 
            {
                if ((newPos.x == portal.position.x)&&(newPos.y == portal.position.y))
                {
                    portalEnterId = portals.indexOf(portal);
                }
            }
            if (portalEnterId >= 0)
            {
                if (portals.get(portalEnterId).id == winningPortal) 
                {
                    gameStatus = GameStatus.WIN;
                } else 
                {
                    delta.x = portals.get(portalEnterId).destination.x - player.position.x;
                    delta.y = portals.get(portalEnterId).destination.y - player.position.y;
                    portals.remove(portalEnterId);
                    portalsPassed += 1;
                    if (portalsPassed == Constants.PLAYER_MAX_MANA) 
                    {
                        player.hp = Constants.PLAYER_MAX_HP;
                        portalsPassed = 0;
                    }
                }
            }
            player.move(delta, 0);
        }
        
        //Machine turn
        for (Unit enemy: enemies) 
        {
            Point newEnemyDelta = new Point();
            if (enemy.position.x < player.position.x) newEnemyDelta.x = 1;
            else if (enemy.position.x > player.position.x) newEnemyDelta.x = -1;
            else newEnemyDelta.x = 0;
            if (enemy.position.y < player.position.y) newEnemyDelta.y = 1;
            else if (enemy.position.y > player.position.y) newEnemyDelta.y = -1;
            else newEnemyDelta.y = 0;
            if (map.getTileAt(0, addPoints(enemy.position, newEnemyDelta)) == null) 
            {
                if (map.getTileAt(0, addPoints(enemy.position, new Point(newEnemyDelta.x, 0))) != null) newEnemyDelta.y = 0; else
                if (map.getTileAt(0, addPoints(enemy.position, new Point(0, newEnemyDelta.y))) != null) newEnemyDelta.x = 0; else
                    continue;
            } 
            if (((newEnemyDelta.x + enemy.position.x) == player.position.x) && 
                ((newEnemyDelta.y + enemy.position.y) == player.position.y)) 
            {
                enemy.attack(newEnemyDelta, 0);
                player.takesDamage(enemy.damage);
                if (!player.isAlive()) 
                {
                    gameStatus = GameStatus.LOSE;
                }
            } else 
            {
                enemy.move(newEnemyDelta, 0);
            }
        }    
    }
}
