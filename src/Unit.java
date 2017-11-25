import java.awt.Point;

/**
 * Unit
 * @author Wenting Zhang Assignment 5
 */

public class Unit 
{
    public Point position;
    public int hp;
    public int damage;
    private Point delta;
    private Animation animation;
    private int animationTick;
    private int animationDelay;
    private int lastDirection;
    
    public Unit(Point position, int hp, int damage) 
    {
        this.position = position;
        this.hp = hp;
        this.damage = damage;
        delta = new Point(0, 0);
        animation = Animation.IDLE;
        animationTick = 0;
        animationDelay = 0;
        lastDirection = 0;
    }
    
    public void move(Point delta, int delay) 
    {
        this.delta = delta;
        this.position.x += delta.x;
        this.position.y += delta.y;
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
    
    public int getAnimationTick()
    {
        return animationTick;
    }
    
    public int getDirection()
    {
        if (delta.x > 0) 
        {
            lastDirection = 1;
        } else if (delta.x < 0) 
        {
            lastDirection = 2;
        } else if (delta.y > 0) 
        {
            lastDirection = 0;
        } else if (delta.y < 0) 
        {
            lastDirection = 3;
        }
        return lastDirection;
    }
    
    public Animation getAnimationType()
    {
        return this.animation;
    }
    
    public void takesDamage(int damage) 
    {
        this.hp -= damage;
    }
    
    public Point globalPos() 
    {
        Point temp = new Point(
                this.position.x * Constants.TILE_SIZE, 
                this.position.y * Constants.TILE_SIZE);
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