package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Graphics.*;
import com.CellularEcosystem.World.*;

public class MainController implements Runnable
{
    //Time
    static long startTime;
    public static long currentTime;
    public static long elapsedTime;
    public static int ticks;

    public static int gameTicks = 0;
    public static double lightFadeAngle = 0.0;

    //Class references
    public MouseInput mouse;
    KeyInput keyPress;
    public MainCanvas canvas;
    World world;

    //Constructor
    public MainController()
    {
        //Instantiate mouse / keyboard listeners
        mouse = new MouseInput();
        keyPress = new KeyInput();

        //Setup main classes
        world = new World(this);
        canvas = new MainCanvas(this, world);
    }


    @Override
    public void run()
    {
        //Setup main Update loop with target 60 fps
        startTime = System.currentTimeMillis();
        long lastUpdateTime = startTime;
        double deltaTime = 0.0;
        ticks = 0;

        while(true)
        {
            currentTime = System.currentTimeMillis();
            deltaTime += (currentTime - lastUpdateTime);
            lastUpdateTime = currentTime;
            elapsedTime = currentTime - startTime;


            if (deltaTime >= Settings.targetFrameRate)
            {

                ticks++;
                deltaTime = 0.0;

                //Update game time
                if (ticks % Settings.gameTickLength == 0)
                    gameTicks++;

                Update();
            }
        }
    }

    void Update()
    {
        //Update time
        double nn = Settings.lightFadeTime;
        lightFadeAngle = ((currentTime - startTime) / nn % nn) / nn * 2.0 * Math.PI;

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
        int cycleTicks = Settings.gameTickLength * 60;

        double pc = (double)(ticks % cycleTicks) / cycleTicks;

        return Math.PI * 2.0 * pc;
    }
}
