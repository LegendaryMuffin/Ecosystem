package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Graphics.*;
import com.CellularEcosystem.World.*;

public class MainController implements Runnable
{
    //Time
    public static long currentTime;
    public static int ticks;

    public static int gameTicks = 0;
    int gameTickLength = 90; //ticks per game second -> 1.5s

    //Class references
    public Library library;
    public MouseInput mouse;
    KeyInput keyPress;
    MainCanvas canvas;
    World world;

    //Constructor
    public MainController(Library library_)
    {
        //Instantiate mouse / keyboard listeners
        mouse = new MouseInput();
        keyPress = new KeyInput();

        //Setup main classes
        library = library_;
        world = new World(this);
        canvas = new MainCanvas(this,world);


        //Represent number of horizontal world units in camera view
        Camera.SetCameraSize();

    }


    @Override
    public void run()
    {
        //Setup main Update loop with target 60 fps
        long lastUpdateTime = System.currentTimeMillis();
        double deltaTime = 0.0;

        double targetFrameRate = 1000.0 / 60.0;
        ticks = 0;

        while(true)
        {
            currentTime = System.currentTimeMillis();
            deltaTime += (currentTime - lastUpdateTime);
            lastUpdateTime = currentTime;


            if (deltaTime >= targetFrameRate)
            {

                ticks++;
                deltaTime = 0.0;

                //Update game time
                if (ticks % gameTickLength == 0)
                    gameTicks++;

                Update();
            }
        }
    }

    void Update()
    {
        world.Update();
        canvas.repaint();
    }

    public String GetTimeString()
    {
        int dayLength = 60 * 24;

        int days = gameTicks / (60 * 24);
        int hours = gameTicks % dayLength / 60;
        int minutes = gameTicks - days * dayLength - hours * 60;

        return days + " # " + hours + " : " + minutes;
    }
    public double GetTimeAngle()
    {
        int cycleTicks = gameTickLength * 60;

        double pc = (double)(ticks % cycleTicks) / cycleTicks;

        return Math.PI * 2.0 * pc;
    }

}
