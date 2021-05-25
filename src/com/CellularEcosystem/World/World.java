package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.*;
import com.CellularEcosystem.Objects.Vector2;
import com.CellularEcosystem.Objects.WorldTile;

import java.awt.*;

public class World
{
    //Reference
    MainController controller;

    //subclasses
    //BoidManager boidManager;


    //World parameters
    public static int size = 128;
    public static int worldUnit; //number of pixels / world unit

    WorldNoise worldNoise;

    Color baseColor;
    Color mainColor;

    int redBase;
    double redAmount;
    int greenBase;
    double greenAmount;
    int blueBase;
    double blueAmount;

    Color[] darkColors = {
            new Color(15,1,65),
            new Color(60,1,20),
            new Color(1,40,20),
            new Color(50,20,50),
            new Color(20,20,20),
    };

    Color[] brightColors = {
            new Color(255,160,255),
            new Color(150,255,220),
            new Color(255,255,120),
            new Color(230,230,230),
            new Color(165,255,165),
    };

    public WorldTile[][] tiles;



    public World(MainController controller_)
    {
        controller = controller_;
        worldUnit = (Main.Screen.height / World.size); //-> screen world X coordinates range from (-size / 2) to (size / 2)

        //baseColor = new Color(254,254,120);
        //mainColor = new Color(100,20,150);

        int rng = (int)Math.floor(Math.random() * darkColors.length);
        mainColor = darkColors[rng];
        Main.Screen.frame.getContentPane().setBackground(mainColor);

        rng = (int)Math.floor(Math.random() * brightColors.length);
        baseColor = brightColors[rng];

        redBase = baseColor.getRed();
        greenBase = baseColor.getGreen();
        blueBase = baseColor.getBlue();

        redAmount = mainColor.getRed() - redBase;
        greenAmount = mainColor.getGreen() - greenBase;
        blueAmount = mainColor.getBlue() - blueBase;

        worldNoise = new WorldNoise(controller, this);
        SetupWorldData();
        //boidManager = new BoidManager(controller, this);

    }

    public Color LerpColor(double pc)
    {
        int rr = (int)Math.round(redAmount * pc);
        int gg = (int)Math.round(greenAmount * pc);
        int bb = (int)Math.round(blueAmount * pc);

        System.out.println(rr);
        return new Color (redBase + rr,greenBase + gg,blueBase + bb);
    }

    void SetupWorldData()
    {
        tiles = new WorldTile[size][size];

        for(int j = 0; j < size; j++)
        {
            for(int i = 0; i < size; i++)
            {
                //Get distance from center
                double xx = Math.abs(i - size/2);
                double yy = Math.abs(j - size/2);
                double dist = Math.sqrt(xx*xx+yy*yy);
                dist = Math.min(size/2.0, dist) / (size / 2.0);

                tiles[i][j] = new WorldTile(this,0,dist,baseColor);
            }
        }
    }

    public void Update()
    {
        worldNoise.Update();
        //boidManager.Update();
    }


}
