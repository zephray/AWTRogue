package awtrogue;

/**
 * Constants
 * @author Wenting Zhang Assignment 5
 */
public class Constants
{
    private Constants()
    {
        
    }
    
    public static final int ANIMATION_LENGTH = 16;
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int MAP_WIDTH = 60;
    public static final int MAP_HEIGHT = 40;
    public static final int BSP_DEPTH = 4;
    public static final int PLAYER_MAX_HP = 50;
    public static final int PLAYER_MAX_MANA = 4;
    public static final int PLAYER_INITIAL_ATK = 5;
    public static final int ENEMY_MAX_HP = 10;
    public static final int ENEMY_INITIAL_ATK = 5;
    public static final int MISS_RATE = 10;
    
    public static final int PLAYER_TEXTURE_Y = 0;
    public static final int BRICK_TEXTURE_Y = 1;
    public static final int ENEMY_TEXURE_Y = 2;
    public static final int LIGHT_TEXTURE_Y = 3;
    public static final int HP_TEXTURE_Y = 4;
    public static final int BAR_TEXTURE_Y = 5;
    public static final int PORTAL_TEXTURE_Y = 6;
    public static final int MANA_TEXTURE_Y = 7;
    
    public static final int TILE_SIZE = 40;
    
    public static final int TILE_FLOOR_BASE = 4;
    public static final int TILE_FLOOR_COUNT = 4;
    
    public static final int SPRITE_PLAYER_DOWN = 0;
    public static final int SPRITE_ENEMY = 8;
    public static final int SPRITE_DEAD_ENEMY = 9;
    public static final int SPRITE_LIGHT = 12;
    public static final int SPRITE_PORTAL = 20;
}
