package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.*;
import com.CellularEcosystem.Objects.ColorPalette;
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

                //Get screen coordinates
                Vector2 pp = new Vector2(i - Settings.worldSize / 2.0,j - Settings.worldSize / 2.0);
                Vector2Int screenPos = Camera.WorldToScreen(pp);

                //Instantiate new tile
                tiles[i][j] = new WorldTile(this,colonyManager,pos, screenPos);
            }
        }
    }

    public void Update()
    {
        UpdateLight();

        //worldNoise.Update();
        colonyManager.Update();
        //boidManager.Update();
    }

    void UpdateLight()
    {
        double angMod = (MainController.dayProgress * Math.PI * 2.0 * Settings.lightRotationsPerDay) % (Math.PI * 2.0);

        if(Settings.lightRotationClockwise)
            angMod = Math.PI * 2.0 - angMod;

        double cycleMod = Math.sin(angMod);


        //Update light according to cycle
        for(int j = 0; j < Settings.worldSize; j++)
        {
            for (int i = 0; i < Settings.worldSize; i++)
            {
                WorldTile tile = tiles[i][j];

                double tileBase = worldNoise.GetLightNoise(tile.angle + angMod, tile.distance);

                double randomness = 1.0 - Settings.lightAmountRandomness + tile.random;
                double light = tileBase * (1.0 + cycleMod * Settings.lightCycleAmplitude) * randomness;

                light = Library.Clamp(light * Settings.lightGammaMultiplier,0.0,1.0);

                tile.lightAmount = light;
            }
        }
    }
}
