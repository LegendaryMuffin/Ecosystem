package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.MainController;
import com.CellularEcosystem.Controller.Settings;
import com.CellularEcosystem.Objects.Vector2Int;
import com.CellularEcosystem.Objects.WorldTile;

import java.util.ArrayList;


public class ColonyManager
{
    //References
    MainController controller;
    World world;

    //Spawn settings
    double spawnDist = 0.85;

    //Colonies
    public ArrayList<Colony> colonies;


    public ColonyManager(MainController controller_, World world_)
    {
        controller = controller_;
        world = world_;

        SetupColonies();
    }

    void SetupColonies()
    {
        colonies = new ArrayList<>();

        //Random spawn;
        double ang = Math.random() * Math.PI * 2.0;

        //Get colony tile coordinate
        int nn = Settings.worldSize / 2;
        int xx = (int)Math.floor((Math.cos(ang) * spawnDist * nn)) + nn;
        int yy = (int)Math.floor((Math.sin(ang) * spawnDist * nn)) + nn;

        Vector2Int spawnPosition = new Vector2Int(xx,yy);

        //Add to list
        colonies.add(new Colony(this,spawnPosition));
    }


    public void Update()
    {
        //Update colonies -> Produce resources
        for (Colony colony : colonies) {
            colony.Update();
        }

        //Spread the juice
        if (MainController.ticks % 8 == 0)
            SpreadResources();
    }



    void SpreadResources()
    {
        WorldTile[][] oldTiles = world.tiles;

        for(int j = 1; j < Settings.worldSize - 1; j++)
        {
            for (int i = 1; i < Settings.worldSize - 1; i++)
            {
                //Check if tile can spread
                if (oldTiles[i][j].id != 1)
                    continue;

                //Get tile/colony
                WorldTile tile = world.tiles[i][j];

                if (tile.amount >= tile.juice.spreadMin)
                {
                    world.tiles[i][j].SpreadTile(oldTiles);
                }
            }
        }
    }
}


