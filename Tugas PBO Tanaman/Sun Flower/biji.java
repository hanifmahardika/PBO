import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class biji here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class biji extends Actor
{
    private boolean isGrabbed;
    public static MouseInfo mi;
    /**
     * Act - do whatever the biji wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        /* add an instance boolean field to the class of the Actor object to be dragged */  
        /* in the 'act' method in the class of the Actor object to be dragged */  
        DragDropBiji();
    } 
    
    public void DragDropBiji()
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
            setLocation(36, 36);
            return;  
        }  
    }
}

