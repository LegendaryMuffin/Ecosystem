package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Graphics.*;
import com.CellularEcosystem.World.*;

import java.util.Set;

public class MainController implements Runnable
{
    //Time
    static long startTime;
    public static long currentTime;
    public static long elapsedTime;

    public static int ticks;
    public static int gameTicks = 0;

    public int totalMinutes = 0;
    public static double dayProgress = 0;
    public static double dayLength; // in ticks

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
        dayLength = Settings.minuteLength * Settings.hourLength * Settings.dayLength * Settings.targetFrameRate;

        while(true)
        {
            currentTime = System.currentTimeMillis();
            deltaTime += (currentTime - lastUpdateTime) * Settings.timeMultiplier;
            lastUpdateTime = currentTime;
            elapsedTime = currentTime - startTime;


            if (deltaTime >= Settings.targetFrameRate)
            {
                ticks++;

                deltaTime = 0.0;

                Update();
            }
        }
    }

    void Update()
    {
        UpdateTime();

        world.Update();
        canvas.repaint();
    }

    void UpdateTime()
    {
        if (ticks % Settings.minuteLength == 0 && ticks != 0)
            totalMinutes++;

        double dayTicks = (totalMinutes * Settings.minuteLength * Settings.targetFrameRate) % dayLength;
        dayProgress = dayTicks / dayLength;

    }

    public String GetTimeString()
    {
        if (totalMinutes == 0)
            return "";

        int days = (totalMinutes / Settings.hourLength / Settings.dayLength);
        int hours = (totalMinutes / Settings.hourLength) % Settings.hourLength;

        return " -< " + days + " >- " + " ( " + hours + " : " +  totalMinutes % Settings.minuteLength + " ) ";

    }
}
