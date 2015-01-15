import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class penyiram here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class penyiram extends Actor
{
    private boolean isGrabbed;
    public static MouseInfo mi;
    /**
     * Act - do whatever the penyiram wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        DragDropCan();
        siram();
    }    

    public void DragDropCan()
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
            setLocation(552, 36);
            return;  
        }  
    }

    public void siram()
    {  
        if("space".equals(Greenfoot.getKey())) 
        {  
            getWorld().addObject(new air(), getX(), getY());  
        }  
    }
}
