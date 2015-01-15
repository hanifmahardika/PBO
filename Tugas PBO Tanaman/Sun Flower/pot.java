import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class pot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class pot extends Actor
{
    public static final int MAX_BIJI = 1;
    int bijiCounter = 0;
    private Counter counter;

    private boolean SoundPlayed;
    boolean SoundPagiPlayed = false;
    boolean SoundMalamPlayed = false;
    /**
     * Act - do whatever the pot wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        tanamBiji(); // kegiatan menanam biji
        pemupukan(); // kegiatan memupuk tanaman
        getBijiCounter(); // cek apa biji telah ditanam
        menyiram(); // setiap air menyentuh pot akan menambah grow poin
        progress(); // memantau kondisi poin untuk memenuhi kriteria proses tumbuh
    }

    public int getBijiCounter()
    {
        return bijiCounter;
    }

    public pot(Counter pointCounter)
    {
        counter = pointCounter;
    }

    public void tanamBiji()
    {
        Actor tanam = getOneObjectAtOffset(0,0,biji.class);
        if(tanam != null)
        {
            getWorld().removeObject(tanam);
            
            // mengembalaikan lokasi asal biji
            biji newBiji;
            newBiji = new biji();
            World world;
            world = getWorld();
            world.addObject(newBiji, 36, 36);
            // tambah counter biji
            bijiCounter++;
            if(bijiCounter > 1)
            {
                System.err.println("Tidak Dapat Menanam Lebih Dari 1 Biji!!! ");
                bijiCounter = MAX_BIJI;
            }
        }
    }

    public void pemupukan()
    {
        Actor pemupukan = getOneObjectAtOffset(0,0,pupuk.class);
        if(pemupukan != null)
        {
            getWorld().removeObject(pemupukan);
            
            if(bijiCounter == MAX_BIJI)
            {
                counter.add(30);
            }
            else
            {
                System.err.println("Tidak ada efek karena biji belum ditanam!!!");
            }
            // mengembalaikan lokasi asal biji
            pupuk newPupuk;
            newPupuk = new pupuk();
            World world;
            world = getWorld();
            world.addObject(newPupuk, 553, 142);
        }
    }

    public void menyiram()
    {
        Actor sentuh = getOneObjectAtOffset(0, 0, air.class);  
        if(sentuh != null) 
        {   
            getWorld().removeObject(sentuh);  
            
            if(bijiCounter == MAX_BIJI)
            {
                counter.add(10); // tambah poin 10
                // untuk score
            }
            else
            {
                System.err.println("Tidak ada efek karena biji belum ditanam!!!");
            }
        } 
    }   

    public void progress()
    {
        if(counter.getValue() >= 100)
        {
            // tambah Tunas1
            tunas1 newTunas1;
            newTunas1 = new tunas1();
            World world;
            world = getWorld();
            world.addObject(newTunas1, 307, 266);

            // hapus Tunas1
            if(counter.getValue() >= 200)
            {
                getWorld().removeObjects(getWorld().getObjects(tunas1.class));
            }
        }
        
        if(counter.getValue() >= 200)
        {
            // ganti background, temp & matahari
            ((Soil)getWorld()).setBackground("space1.jpg");
            getWorld().removeObjects(getWorld().getObjects(matahari.class));
            bulan newBulan;
            newBulan = new bulan();
            getWorld().addObject(newBulan, 100, 74);
            
            // replace Tunas1
            tunas2 newTunas2;
            newTunas2 = new tunas2();
            World world;
            world = getWorld();
            world.addObject(newTunas2, 310, 210);

            // hapus tunas2
            if(counter.getValue() >= 300)
            {
                getWorld().removeObjects(getWorld().getObjects(tunas2.class));
            }
        }
        
        if(counter.getValue() >= 300)
        {
            // replace Tunas2
            mature newMature;
            newMature = new mature();
            World world;
            world = getWorld();
            world.addObject(newMature, 310, 210);

            // ganti background & bulan
            ((Soil)getWorld()).setBackground("sand2.jpg");
            getWorld().removeObjects(getWorld().getObjects(bulan.class));
            matahari newMatahari;
            newMatahari = new matahari();
            getWorld().addObject(newMatahari, 297, 74);
            
            // keterangan win
            System.out.println("SELAMAT TANAMAN MELATI TELAH TUMBUH SEMPURNA!!!");
            Greenfoot.stop();
        }
    }
}

