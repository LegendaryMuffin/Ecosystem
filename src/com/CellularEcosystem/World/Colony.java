package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.MainController;
import com.CellularEcosystem.Graphics.Circle;
import com.CellularEcosystem.Objects.*;

import java.awt.*;

public class Colony {

    World world;
    ColonyManager colonyManager;

    //Basic properties
    Vector2Int position;

    public Juice juice;

    //Resource properties
    double consumptionRate = 0.05;
    double productionRate = 1.0;

    public Color color = Color.magenta;


    public Colony(ColonyManager colonyManager_, Vector2Int spawnPosition)
    {
        //References
        colonyManager = colonyManager_;
        world = colonyManager_.world;

        //Setup colony spawn
        position = spawnPosition;
        world.tiles[position.x][position.y].colony = this;
        world.tiles[position.x][position.y].id = 2;

        //Setup juice
        juice = new Juice(Color.cyan, 0.3,0.1,0.0,0.06);
    }

    public void Update()
    {
        if (MainController.ticks % 30 == 0)
            ProduceResource();
    }

    void ProduceResource()
    {
        for(int j = -1; j < 2; j++)
        {
            for(int i = -1; i < 2; i++)
            {
                int xx = position.x + i;
                int yy = position.y + j;

                WorldTile tile = world.tiles[xx][yy];

                //Skip colony tiles
                if (tile.id == 2)
                    continue;

                //Add resource
                tile.resource += productionRate / MainController.targetFrameRate;
                tile.resource = Math.min(tile.resource,tile.density);

                //Manage decay
                tile.resource *= (1.0 + juice.decay);

                if (tile.resource < 0.001)
                {
                    tile.colony = null;
                    tile.id = 0;
                }
                else
                {
                    tile.colony = this;
                    tile.id = 1;
                }

            }
        }
    }
}
