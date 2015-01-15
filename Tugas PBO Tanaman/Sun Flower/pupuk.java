import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class pupuk here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class pupuk extends Actor
{
    private static boolean isGrabbed;
    public static MouseInfo mi;
    /**
     * Act - do whatever the pupuk wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        DragDropPupuk();
    }    
    
    public void DragDropPupuk()
    {
        // menekan mouse  
        if (Greenfoot.mousePressed(this) && !isGrabbed)  
        {  
            // ambil objek  
            isGrabbed = true;  
            // Agar objek yg ditarik melewati depan objek lain
            World world = getWorld();  
            mi = Greenfoot.getMouseInfo();  
            world.removeObject(this);  
            world.addObject(this, mi.getX(), mi.getY());  
            return;  
        }  

        // menarik objek  
        if ((Greenfoot.mouseDragged(this)) && isGrabbed)  
        {  
            // objek mengikuti mouse
            mi = Greenfoot.getMouseInfo();  
            setLocation(mi.getX(), mi.getY());  
            return;
        }  

        // button mouse dilepas 
        if (Greenfoot.mouseDragEnded(this) && isGrabbed)  
        {  
            // lepas objek  
            isGrabbed = false;
            // kembalikan objek ke tempat semula
            setLocation(553, 142);
            return;  
        }  
    }
}
