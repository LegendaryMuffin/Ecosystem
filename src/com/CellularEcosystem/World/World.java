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

    //Subclasses
    WorldNoise worldNoise;
    ColonyManager colonyManager;
    //BoidManager boidManager;


    public static double worldUnit;
    public static Vector2 worldUnitVector;

    public WorldTile[][] tiles;



    public World(MainController controller_)
    {
        //Setup base references
        controller = controller_;
        worldUnit = (double)Main.Screen.height / Settings.worldSize;
        worldUnitVector = new Vector2(worldUnit,worldUnit);

        SetupWorldTiles();
        worldNoise = new WorldNoise(controller, this);
        colonyManager = new ColonyManager(controller, this );

        //boidManager = new BoidManager(controller, this);

    }

    void SetupWorldTiles()
    {
        //Create world tile array
        int size = Settings.worldSize;
        tiles = new WorldTile[size][size];

        //Initialize density
        for(int j = 0; j < size; j++)
        {
            for(int i = 0; i < size; i++)
            {
                //Get tile position
                Vector2Int pos = new Vector2Int(i,j);

                //Instantiate new tile
                tiles[i][j] = new WorldTile(this,colonyManager,pos);
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
