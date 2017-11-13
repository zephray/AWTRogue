package awtrogue;

import java.awt.Point;

/**
 * Unit
 * @author Wenting Zhang Assignment 5
 */

public class Unit 
{
    public Point tile;
    public int hp;
    public int damage;
    private Point delta;
    private Animation animation;
    private int animationTick;
    private int animationDelay;
    
    public Unit(Point tile, int hp, int damage) 
    {
        this.tile = tile;
        this.hp = hp;
        this.damage = damage;
        delta = new Point(0, 0);
        animation = Animation.IDLE;
        animationTick = 0;
        animationDelay = 0;
    }
    
    public void move(Point delta, int delay) 
    {
        this.delta = delta;
        this.tile.x += delta.x;
        this.tile.y += delta.y;
        animation = Animation.MOVE;
        animationDelay = delay;
        animationTick = Constants.ANIMATION_LENGTH;
    }
    
    public void attack(Point delta, int delay) 
    {
        this.delta = delta;
        animation = Animation.ATTACK;
        animationDelay = delay;
        animationTick = Constants.ANIMATION_LENGTH;
    }
    
    public void takesDamage(int damage) 
    {
        this.hp -= damage;
    }
    
    public Point globalPos() 
    {
        Point temp = new Point(
                this.tile.x * Constants.TILE_SIZE, 
                this.tile.y * Constants.TILE_SIZE);
        double coefficient = 0;
        switch (animation) 
        {
            case IDLE:
                coefficient = 0;
                break;
            case MOVE:
                coefficient = -((double)animationTick) /
                        ((double)Constants.ANIMATION_LENGTH);
                break;
            case ATTACK:
                if (animationTick > (2 * Constants.ANIMATION_LENGTH / 3)) 
                {
                    coefficient = 
                        -((double)(Constants.ANIMATION_LENGTH - animationTick))/
                        ((double)Constants.ANIMATION_LENGTH);
                } else 
                {
                    coefficient = ((double)animationTick) /
                        ((double)Constants.ANIMATION_LENGTH);
                }
                break;
        }
        temp.x += (int)((double)(delta.x) * (double)(Constants.TILE_SIZE) 
            * coefficient);
        temp.y += (int)((double)(delta.y) * (double)(Constants.TILE_SIZE) 
            * coefficient);
        return temp;
    }
    
    public void tick() 
    {
        if (animationDelay > 0) 
        {
            animationDelay -= 1;
        }
        if (animationTick > 0) 
        {
            animationTick -= 1;
        }
        if (animationTick == 1) 
        {
            animation = Animation.IDLE;
        }
    }
    
    public Boolean isAnimationPlaying() 
    {
        return (animation != Animation.IDLE);
    }
    
    public Boolean isAlive() 
    {
        return (hp > 0);
    }
    
    public void stopAnimation() 
    {
        animation = Animation.IDLE;
    }
}