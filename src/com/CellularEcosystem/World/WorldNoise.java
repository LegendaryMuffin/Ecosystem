package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.Library;
import com.CellularEcosystem.Controller.MainController;
import com.CellularEcosystem.Controller.Settings;
import com.CellularEcosystem.Objects.Vector2;
import com.CellularEcosystem.Objects.WorldTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class WorldNoise {

    MainController controller;
    World world;

    //Noise texture
    public BufferedImage noiseTexture = null;

    //noise values (0-1)
    double[][] noiseMap;
    int noiseWidth;
    int noiseHeight;

    //Light noise
    double[] lightVectorMagnitudes;
    double[] lightVectorAngles;


    public WorldNoise(MainController controller_, World world_)
    {
        controller = controller_;
        world = world_;

        SetupNoiseMap();

        SetupDensityNoise();
        SetupLightNoise();
    }


    public void Update()
    {
        UpdateLight();
    }

    void UpdateLight()
    {
        int tt = 60 * Settings.gameTickLength / 2; // Time for an entire day

        double cycleMod = (MainController.ticks % tt) * Math.PI * 2.0;
        cycleMod = Math.sin(cycleMod) * Settings.lightCycleAmplitude;

        //Update light according to cycle
        for(int j = 0; j < Settings.worldSize; j++)
        {
            for (int i = 0; i < Settings.worldSize; i++)
            {
                WorldTile tile = world.tiles[i][j];

                double rr = 1.0 - Settings.lightRandomness + tile.random;
                double ll = world.tiles[i][j].baseLightAmount * (1.0 + cycleMod) * rr;
                ll = Library.Clamp(ll,0.0,1.0);
                tile.lightAmount = ll;
            }
        }
    }

    // * * * <  T E X T U R E  > * * *

    void SetupNoiseMap()
    {
        //Get Texture
        try { noiseTexture = ImageIO.read(new File("src/sprites/perlin.png"));}
        catch (IOException e) {e.printStackTrace();}

        noiseWidth = noiseTexture.getWidth();
        noiseHeight = noiseTexture.getHeight();

        //System.out.println(noiseWidth);

        noiseMap = new double[noiseWidth][noiseHeight];

        for(int j = 0; j < noiseHeight; j ++)
        {
            for(int i = 0; i < noiseWidth; i ++)
            {
                //Get color
                Color col = new Color(noiseTexture.getRGB(i,j));

                //value between 0-1
                noiseMap[i][j] = (col.getRed() + col.getGreen() + col.getBlue()) / 765.0;
            }
        }
    }

    // * * * <  D E N S I T Y  > * * *

    void SetupDensityNoise()
    {
        //Random position on noiseMap
        double amp = (1.0 - Settings.densityNoiseSize);
        double noiseX = Math.random() * amp;
        double noiseY = Math.random() * amp;


        for(int j = 0; j < Settings.worldSize; j++)
        {
            for(int i = 0; i < Settings.worldSize; i++)
            {
                //Get noise
                double x = ((double)i / Settings.worldSize + noiseX) * noiseWidth;
                double y = ((double)j / Settings.worldSize + noiseY) * noiseHeight;

                int xx = (int) (x * Settings.densityNoiseSize);
                int yy = (int) (y * Settings.densityNoiseSize);

                xx = Library.ClampInt(xx,0,noiseWidth-1);
                yy = Library.ClampInt(yy,0,noiseHeight-1);

                double density = GetDensity(xx,yy);

                if (i == 0 || j == 0 || i == Settings.worldSize - 1 || j == Settings.worldSize - 1)
                    world.tiles[i][j].density = 0.0;
                else
                    world.tiles[i][j].SetDensity(density);
            }
        }
    }

    double GetDensity(int x, int y)
    {
        double noiseAmount = 0.0;

        double amp = Settings.densityNoiseAmplitude;
        int xx = x;
        int yy = y;

        for(int k = 0; k < Settings.densityNoiseLayers; k++)
        {
            //Get noise amount
            noiseAmount += noiseMap[xx][yy] * amp;

            //Apply modifiers
            amp *= Settings.densityNoisePersistence;
            xx = ((int)(xx * Settings.densityNoiseLacunarity)) % noiseWidth;
            yy = ((int)(yy * Settings.densityNoiseLacunarity)) % noiseHeight;
        }

        //return total final density
        double density = 1.0 - noiseAmount - Settings.densityNoiseGamma;
        return Math.min(Math.max(density, 0.0), 1.0);
    }


    // * * * <  L I G H T  > * * *


    void SetupLightNoise()
    {
        //Random position on noiseMap
        int noiseX = (int)(Math.random() * (noiseWidth-1));
        int noiseY = (int)(Math.random() * (noiseHeight-1));


        //Setup base light map
        for(int j = 0; j < Settings.worldSize; j++)
        {
            for (int i = 0; i < Settings.worldSize; i++)
            {
                WorldTile tile = world.tiles[i][j];

                tile.baseLightAmount = GetLightNoise(noiseX,noiseY,tile.angle, tile.distance) * Math.pow(1.0-tile.distance,Settings.lightFalloffMultiplier);
                tile.lightAmount = tile.baseLightAmount;

            }
        }
    }

    double GetLightNoise(int noiseX, int noiseY, double ang,double distance)
    {
        double lightAmount = 0.0;
        double amp = Settings.lightNoiseAmplitude;

        //Get noise texture coordinates
        double xx = Math.cos(ang) * amp * Settings.lightNoiseSize + noiseX;
        double yy = Math.sin(ang) * amp * Settings.lightNoiseSize + noiseY;


        for(int k = 0; k < Settings.lightNoiseLayers; k++)
        {
            int x = (int)Math.floor(xx * noiseWidth % noiseWidth);
            int y = (int)Math.floor(yy * noiseHeight % noiseHeight);

            //Get noise amount
            lightAmount += noiseMap[x][y] * amp;

            xx *= Settings.lightNoiseLacunarity;
            yy *= Settings.lightNoiseLacunarity;

            //Apply modifiers
            amp *= Settings.lightNoisePersistence;
        }

        //return total final light
        double light = (lightAmount + Settings.lightNoiseGamma) * Settings.lightRadius;


        return Math.min(Math.max(light, 0.0), 1.0) * Math.pow(1.0 - distance,Settings.lightFalloffMultiplier);
    }

}
