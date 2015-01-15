import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class air here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class air extends sifatAir
{
    private int x = 0;
    /**
     * Act - do whatever the air wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        manuverAir();
    }

    public void manuverAir()
    {
        move(3);
        setLocation(getX(), getY());
        if(x == 0)
        {
            setRotation(90);
            x++;
        }
        if (atWorldEdge())  
        {
            World world;  
            world = getWorld();  
            world.removeObject(this); 
        }
    }
}
