package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.*;
import com.CellularEcosystem.Objects.Vector2;
import com.CellularEcosystem.Objects.Vector2Int;
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


    public int maxColonies = 1;
    public ColonyManager colonyManager;

    //Dumb color lerping and shit
    public Color baseColor;
    public Color mainColor;

    int redBase;
    double redAmount;
    int greenBase;
    double greenAmount;
    int blueBase;
    double blueAmount;



    public WorldTile[][] tiles;



    public World(MainController controller_)
    {
        //Setup base references
        controller = controller_;
        controller.canvas.world = this;
        worldUnit = (Main.Screen.height / World.size); //-> screen world X coordinates range from (-size / 2) to (size / 2)

        SetupColors();

        worldNoise = new WorldNoise(controller, this);
        SetupWorldTiles();
        colonyManager = new ColonyManager(controller, this );

        //boidManager = new BoidManager(controller, this);

    }

    void SetupColors()
    {
        int rng = (int)Math.floor(Math.random() * Library.darkColors.length);
        mainColor = new Color(100,100,100);;//Library.darkColors[rng];
        Main.Screen.frame.getContentPane().setBackground(mainColor);

        rng = (int)Math.floor(Math.random() * Library.brightColors.length);
        baseColor = Color.black;//Library.brightColors[rng];

        redBase = baseColor.getRed();
        greenBase = baseColor.getGreen();
        blueBase = baseColor.getBlue();

        redAmount = mainColor.getRed() - redBase;
        greenAmount = mainColor.getGreen() - greenBase;
        blueAmount = mainColor.getBlue() - blueBase;
    }

    void SetupWorldTiles()
    {
        tiles = new WorldTile[size][size];

        for(int j = 0; j < size; j++)
        {
            for(int i = 0; i < size; i++)
            {
                //Get distance from center
                double xx = Math.abs(i - World.size/2);
                double yy = Math.abs(j - World.size/2);
                Vector2Int pos = new Vector2Int(i,j);
                double dist = Math.sqrt(xx*xx+yy*yy);
                dist = Math.min(size/2.0, dist) / (size / 2.0);

                tiles[i][j] = new WorldTile(this,0,pos, dist);
            }
        }
    }

    public void Update()
    {
        worldNoise.Update();
        colonyManager.Update();

        //boidManager.Update();
    }
}
