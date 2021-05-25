package com.CellularEcosystem.Objects;

import com.CellularEcosystem.World.World;

import java.awt.*;

public class WorldTile
{
    World world;
    public int id = 0;

    public double density = 0.0;
    public double brightness = 1.0;

    public Color color;
    public Color baseColor;


    public WorldTile(World world_, int id_, double density_, Color baseColor_)
    {
        world = world_;
        id = id_;
        density = density_;
        baseColor = baseColor_;

        int col = (int)((1.0 - density) * 255.0);
        color = new Color(col,col,col);
    }

    public void SetNoise(double noiseAmount)
    {
        double dens = density *  (0.98 + noiseAmount);
        int col = (int)((1.0 - dens) * 255);
        dens = Math.min(Math.max(dens,0.0),1.0);

        color = world.LerpColor(dens);
    }


}
