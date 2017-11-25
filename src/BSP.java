import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;

/**
 * BSP 
 * @author Wenting Zhang Assignment 5
 */
public class BSP
{
    //Binary Space Partitioning
    public final int MIN_SEG_WIDTH = 10;
    public final int MIN_SEG_HEIGHT = 10;
    
    //Segmentation Dimension
    public Point dimensionBegin;
    public Point dimensionEnd;
    
    //Room Dimension
    public Room room;
    
    //Child
    public BSP left, right;
    
    //Other status and internal data
    private int depth;
    private Random rng;
    
    public BSP(Point dimensionBegin, Point dimensionEnd, int depth, Random rng) 
    {
        this.dimensionBegin = dimensionBegin;
        this.dimensionEnd = dimensionEnd;
        this.depth = depth;
        this.rng = rng;
    }
    
    public void generateChild(int maximumDepth) 
    {
        //recursive generate
        if (depth < maximumDepth) 
        {
            //See what direction is possible
            int x0 = dimensionBegin.x + MIN_SEG_WIDTH;
            int x1 = dimensionEnd.x - MIN_SEG_WIDTH;
            int y0 = dimensionBegin.y + MIN_SEG_HEIGHT;
            int y1 = dimensionEnd.y - MIN_SEG_HEIGHT;
            int direction;
            if (x1 > x0) 
            {
                if (y1 > y0) 
                {
                    direction = rng.nextInt(2);
                } else 
                {
                    direction = 1;
                }
            } else 
            {
                if (y1 > y0) 
                {
                    direction = 0;
                } else {
                    return;
                }
            }
            if (direction == 1) 
            { //divide horizontally
                int x = rng.nextInt(x1 - x0) + x0;
                left = new BSP(new Point(dimensionBegin.x, dimensionBegin.y),
                    new Point(x, dimensionEnd.y), depth + 1, rng);
                left.generateChild(maximumDepth);
                right = new BSP(new Point(x, dimensionBegin.y), 
                    new Point(dimensionEnd.x, dimensionEnd.y), depth + 1, rng);
                right.generateChild(maximumDepth);
            } else 
            {
                int y = rng.nextInt(y1 - y0) + y0;
                left = new BSP(new Point(dimensionBegin.x, dimensionBegin.y),
                    new Point(dimensionEnd.x, y), depth + 1, rng);
                left.generateChild(maximumDepth);
                right = new BSP(new Point(dimensionBegin.x, y), 
                    new Point(dimensionEnd.x, dimensionEnd.y), depth + 1, rng);
                right.generateChild(maximumDepth);
            }
        } 
    }
    
    public void generateRoom() 
    {
        if ((left != null)&&(right != null)) 
        {
            left.generateRoom();
            right.generateRoom();
        } else {
            int x0 = dimensionBegin.x + 1 + rng.nextInt(3);
            int x1 = dimensionEnd.x - rng.nextInt(4);
            int y0 = dimensionBegin.y + 1 + rng.nextInt(3);
            int y1 = dimensionEnd.y - rng.nextInt(4);
            room = new Room(new Point(x0, y0), new Point(x1, y1));
        }
    }
    
    public ArrayList<Room> getRoom() 
    {
        ArrayList<Room> rooms = new ArrayList<>();
        
        if ((left != null)&&(right != null)) 
        {
            rooms.addAll(left.getRoom());
            rooms.addAll(right.getRoom());
        } else {
            rooms.add(room);
        }
        
        return rooms;
    }
    
    public ArrayList<Room> getBridge() 
    {
        ArrayList<Room> bridges = new ArrayList<>();
        
        if ((left != null)&&(right != null)) 
        {
            try 
            {
                bridges.addAll(left.getBridge());
            } catch (NullPointerException e) 
            {
                
            }
            
            try 
            {
                bridges.addAll(right.getBridge());
            } catch (NullPointerException e) 
            {
                
            }
            
            //figure out the relation
            if (left.dimensionBegin.x == right.dimensionBegin.x) 
            {
                //vertical split
                System.out.println("Vertical Split");
                ArrayList<Room> roomsLeft = left.getRoom();
                ArrayList<Room> roomsRight = right.getRoom();
                Traverse:
                for (Room roomLeft: roomsLeft) 
                {
                    for (Room roomRight: roomsRight) 
                    {
                        int xl0 = roomLeft.begin.x;
                        int xl1 = roomLeft.end.x;
                        int xr0 = roomRight.begin.x;
                        int xr1 = roomRight.end.x;
                        int xb;
                        if ((xl1 >= xr0)&&(xl1 <= xr1)) 
                        {
                            System.out.println("Match found");
                            xb = xr0 + rng.nextInt(xl1 - xr0);
                            int yb0 = (roomLeft.begin.y > roomRight.begin.y)?(roomRight.end.y):(roomLeft.end.y);
                            int yb1 = (roomLeft.begin.y > roomRight.begin.y)?(roomLeft.begin.y):(roomRight.begin.y);
                            bridges.add(new Room(new Point(xb - 1, yb0), new Point(xb + 1, yb1)));
                            break Traverse;
                        } else if ((xl0 >= xr0)&&(xl0 <= xr1)) 
                        {
                            System.out.println("Match found");
                            xb = xl0 + rng.nextInt(xr1 - xl0); 
                            int yb0 = (roomLeft.begin.y > roomRight.begin.y)?(roomRight.end.y):(roomLeft.end.y);
                            int yb1 = (roomLeft.begin.y > roomRight.begin.y)?(roomLeft.begin.y):(roomRight.begin.y);
                            bridges.add(new Room(new Point(xb - 1, yb0), new Point(xb + 1, yb1)));
                            break Traverse;
                        }
                    }
                }
            } else 
            {
                System.out.println("Horizontal Split");
                //horizontal split
                ArrayList<Room> roomsLeft = left.getRoom();
                ArrayList<Room> roomsRight = right.getRoom();
                Traverse:
                for (Room roomLeft: roomsLeft)
                {
                    for (Room roomRight: roomsRight) 
                    {
                        int yl0 = roomLeft.begin.y;
                        int yl1 = roomLeft.end.y;
                        int yr0 = roomRight.begin.y;
                        int yr1 = roomRight.end.y;
                        int yb;
                        if ((yl1 >= yr0)&&(yl1 <= yr1)) 
                        {
                            System.out.println("Match found");
                            yb = yr0 + rng.nextInt(yl1 - yr0 + 1);
                            int xb0 = (roomLeft.begin.x > roomRight.begin.x)?(roomRight.end.x):(roomLeft.end.x);
                            int xb1 = (roomLeft.begin.x > roomRight.begin.x)?(roomLeft.begin.x):(roomRight.begin.x);
                            bridges.add(new Room(new Point(xb0, yb - 1), new Point(xb1, yb + 1)));
                            break Traverse;
                        } else if ((yl0 >= yr0)&&(yl0 <= yr1)) 
                        {
                            System.out.println("Match found");
                            yb = yl0 + rng.nextInt(yr1 - yl0 + 1); 
                            int xb0 = (roomLeft.begin.x > roomRight.begin.x)?(roomRight.end.x):(roomLeft.end.x);
                            int xb1 = (roomLeft.begin.x > roomRight.begin.x)?(roomLeft.begin.x):(roomRight.begin.x);
                            bridges.add(new Room(new Point(xb0, yb - 1), new Point(xb1, yb + 1)));
                            break Traverse;
                        }
                    }
                }
            }
        } else 
        {
            //We are at bottom level
            return null;
        }
        return bridges;
    }
}
