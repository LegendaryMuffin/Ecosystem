package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.MainController;
import com.CellularEcosystem.Objects.Vector2Int;
import com.CellularEcosystem.Objects.WorldTile;

import java.util.ArrayList;


public class ColonyManager
{
    MainController controller;
    World world;

    double spawnDist = 0.85;

    double spreadMin = 12.0;

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

        int nn = World.size / 2;
        int xx = (int)Math.floor((Math.cos(ang) * spawnDist * nn)) + nn;
        int yy = (int)Math.floor((Math.sin(ang) * spawnDist * nn)) + nn;

        Vector2Int spawnPosition = new Vector2Int(xx,yy);

        colonies.add(new Colony(this,spawnPosition));
    }


    public void Update()
    {

        //Update colonies -> Produce resources
        for (Colony colony : colonies) {
            colony.Update();
        }

        if (MainController.ticks % 8 == 0)
            SpreadResources();
    }



    void SpreadResources()
    {
        WorldTile[][] oldTiles = world.tiles;

        for(int j = 1; j < World.size - 1; j++)
        {
            for (int i = 1; i < World.size - 1; i++)
            {

                //Check if tile can spread
                if (oldTiles[i][j].colony == null)
                    continue;

                WorldTile tile = world.tiles[i][j];

                //Apply decay
                double dec = 1.0 - tile.colony.juice.decay * (1.0 -tile.density);
                double dens = tile.density;

                tile.resource *= (dec);
                //tile.resource = Math.max(Math.min(1.0,tile.resource), 0.0);

                if (oldTiles[i][j].resource > oldTiles[i][j].colony.juice.spreadMin)
                {
                    world.tiles[i][j].SpreadTile(oldTiles);

                }

            }
        }
    }
}


