import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class sifatAir here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class sifatAir extends Actor
{
    /**
     * Act - do whatever the sifatAir wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        atWorldEdge();
    }    
    public boolean atWorldEdge()  
    {  
        if(getX() < 10 || getX() > getWorld().getWidth() - 10)  
            return true;  
        if(getY() < 10 || getY() > getWorld().getHeight() - 10)  
            return true;  
        else  
            return false;  
    }  
}
