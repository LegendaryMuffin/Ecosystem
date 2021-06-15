package com.CellularEcosystem.Objects;

import com.CellularEcosystem.Controller.Library;
import com.CellularEcosystem.World.Colony;
import com.CellularEcosystem.World.World;

import java.awt.*;

public class WorldTile
{
    World world;

    /*ID****
    0: empty
    1: juice tile
    2: colony tile
     */
    public int id = 0;
    public Colony colony;
    public double resource;

    Vector2Int position; // ()
    public double distance;
    public double density;

    public Color baseColor = Color.black;
    //public Color baseColor;


    public WorldTile(World world_, int id_, Vector2Int pos, double dist)
    {
        world = world_;

        id = id_;
        position = pos;
        distance = dist;
        resource = 0.0;
    }

    public void SetDensity(double noiseAmount)
    {
        density = distance *  (0.98 + noiseAmount);
        density = Math.min(Math.max(density,0.0),1.0);

        baseColor = Library.LerpColor(density,world.baseColor ,world.mainColor);
    }

    public void SpreadTile(WorldTile[][] oldTiles)
    {
        int x = position.x;
        int y = position.y;

        double totalVolume = 0.0;
        double[][] fractions = new double[3][3];
        double toSpread = (oldTiles[x][y].resource - oldTiles[x][y].colony.juice.spreadMin) * (1.0 - oldTiles[x][y].colony.juice.viscosity);

        //Get total pressure
        for (int j = y -1; j < y + 2; j++) {
            for (int i = x -1; i < x + 2; i++) {

                //noot
                int xx = i - x + 1;
                int yy = j - y + 1;

                //Check if colony tile
                if(oldTiles[i][j].id == 2)
                {
                    fractions[xx][yy] = 0.0;
                    continue;
                }

                //Get free space in tile
                double volume = oldTiles[i][j].density - oldTiles[i][j].resource;
                totalVolume += volume;
                fractions[xx][yy] = volume;
            }
        }

        //System.out.println(totalVolume);

        world.tiles[x][y].resource -= toSpread;


        //Spread
        for (int j = y -1; j < y + 2; j++) {
            for (int i = x -1; i < x + 2; i++) {

                //Avoid colony tiles
                if(oldTiles[i][j].id != 2)
                {
                    //Get amount for tile based on free space
                    double newAmount = toSpread * fractions[i-x + 1][j-y+1] / totalVolume;
                    world.tiles[i][j].resource += newAmount;

                    if (world.tiles[i][j].id == 0)
                    {
                        world.tiles[i][j].id = 1;
                        world.tiles[i][j].colony = colony;
                    }
                }
            }
        }


    }

    public Color GetColor()
    {
        if (colony == null)
            return baseColor;
        else if (id == 1)
            return Library.LerpColor(resource, baseColor, colony.juice.mainColor);
        else
            return colony.color;
    }


}
