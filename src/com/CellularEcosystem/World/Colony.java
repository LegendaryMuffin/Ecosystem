package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.MainController;
import com.CellularEcosystem.Controller.Settings;
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
    double productionRate = 1.0;

    public Color color = Color.magenta;


    public Colony(ColonyManager colonyManager_, Vector2Int spawnPosition)
    {
        //References
        colonyManager = colonyManager_;
        world = colonyManager_.world;

        //Setup juice
        SetupJuice();


        //Setup colony spawn
        position = spawnPosition;
        world.tiles[position.x][position.y].juice = juice;
        world.tiles[position.x][position.y].id = 2;

    }

    void SetupJuice()
    {
        for (int k = 0; k < Settings.maxColonies; k++)
        {
            int randomColor = (int)(Math.random() * Settings.colonyColors.length);
            Color color = Settings.colonyColors[randomColor];

            //Percentage of juice that stays on tile after spread
            double viscosity = Settings.baseViscosity + (Math.random() * 2.0 - 1.0) * Settings.viscosityVariation;
            double minimumSpread = Settings.baseMinimumSpread + (Math.random() * 2.0 - 1.0) * Settings.minimumSpreadVariation;

            //Determines how much juice is impacted by cell density
            double volatility = Settings.baseVolatility + (Math.random() * 2.0 - 1.0) * Settings.volatilyVariation;

            double decay = Settings.baseDecay + (Math.random() * 2.0 - 1.0) * Settings.decayVariation;

            juice = new Juice(color, viscosity,minimumSpread,volatility,decay);


        }

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
                tile.amount += productionRate / MainController.targetFrameRate;
                tile.amount = Math.min(tile.amount,tile.density);

                //Adjust juice reference
                if (tile.amount < 0.001)
                {
                    tile.juice = null;
                    tile.id = 0;
                }
                else if (tile.id == 0)
                {
                    tile.juice = juice;
                    tile.id = 1;
                }

            }
        }
    }
}
