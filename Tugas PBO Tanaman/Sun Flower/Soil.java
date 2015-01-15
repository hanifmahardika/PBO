import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Soil here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Soil extends World
{
    private boolean SoundPlayed;
    boolean hasIntroPlayed = false;
    /**
     * Constructor for objects of class Soil.
     * 
     */
    public Soil()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        prepare();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        Counter counter = new Counter();
        addObject(counter, 100, 40);

        pot pot = new pot(counter);
        addObject(pot, 331, 287);
        pot.setLocation(331, 300);
        
        biji biji = new biji();
        addObject(biji, 36, 36);
        
        matahari matahari = new matahari();
        addObject(matahari, 297, 74);
        
        penyiram penyiram = new penyiram();
        addObject(penyiram, 552, 43);
        
        pupuk pupuk = new pupuk();
        addObject(pupuk, 558, 154);
        pupuk.setLocation(553, 142);
        
        tunas1 tunas1 = new tunas1();
        addObject(tunas1, 309, 270);
        tunas1.setLocation(307, 266);
        removeObject(tunas1);
        
        tunas2 tunas2 = new tunas2();
        addObject(tunas2, 303, 250);
        tunas2.setLocation( 307, 266);
        removeObject(tunas2);
        
        counter.setLocation( 36, 127);
    }

    public void act()
    {
        if(!hasIntroPlayed)
        {
            playIntro();
            hasIntroPlayed = true;
        }
    }
    
    public void playIntro()
    {
        Greenfoot.playSound("pitik.mp3");
    }
}
