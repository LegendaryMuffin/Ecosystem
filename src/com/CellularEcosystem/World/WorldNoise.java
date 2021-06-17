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
    Vector2[] lightVectors;
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

    // * * * <  T E X T U R E  > * * *

    void SetupNoiseMap()
    {
        //Get Texture
        try { noiseTexture = ImageIO.read(new File("src/sprites/noiseTexture.png"));}
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
        lightVectors = new Vector2[Settings.lightResolution];
        lightVectorAngles = new double[Settings.lightResolution];

        //Random position on noiseMap
        double amp = (1.0 - Settings.lightNoiseSize * 2.0);
        int noiseX = (int)(Math.random() * amp + Settings.lightNoiseSize);
        int noiseY = (int)(Math.random() * amp + Settings.lightNoiseSize);

        for(int k = 0; k < Settings.lightResolution; k++)
        {
            //Get angle
            double slice = Math.random() * Math.PI * 2.0 / Settings.lightResolution;
            double rng = Math.random() * Settings.lightResolutionRandomness;
            double ang = slice * k + rng;

            //Get vector noise positions
            double lightNoise = GetLightNoise(noiseX, noiseY, ang);

            double xx = Math.cos(ang) * Settings.lightRadius * lightNoise;
            double yy = Math.sin(ang) * Settings.lightRadius * lightNoise;

            lightVectors[k] = new Vector2(xx,yy);
            lightVectorAngles[k] = ang;
        }
    }

    double GetLightNoise(int noiseX, int noiseY, double ang)
    {
        double lightAmount = 0.0;
        double amp = Settings.lightNoiseAmplitude;

        //Get noise texture coordinates
        double xx = Math.cos(ang) * Settings.lightNoiseSize;
        double yy = Math.sin(ang) * Settings.lightNoiseSize;


        for(int k = 0; k < Settings.lightNoiseLayers; k++)
        {
            xx *= Settings.lightNoiseLacunarity;
            yy *= Settings.lightNoiseLacunarity;

            int x = (int)Math.min(xx,noiseWidth-1) + noiseX;
            int y = (int)Math.min(yy,noiseHeight-1) + noiseY;

            //Get noise amount
            lightAmount += noiseMap[x][y] * amp;

            //Apply modifiers
            amp *= Settings.lightNoisePersistence;
        }

        //return total final light
        double light = lightAmount + Settings.lightNoiseGamma;
        return Math.min(Math.max(light, 0.0), 1.0);
    }


    void UpdateLight()
    {
        //Update light according to cycle
        for(int j = 0; j < Settings.worldSize; j++)
        {
            for (int i = 0; i < Settings.worldSize; i++)
            {
                WorldTile tile = world.tiles[i][j];




                tile.lightAmount = GetLightAmount(tile.angle, tile.distance);
            }
        }
    }

    double GetLightAmount(double angle, double dist)
    {
        double lightMod = 1.0;
        double currentAng = 0.0;

        for(int k = 0; k < Settings.lightResolution - 1; k++)
        {
            currentAng += lightVectorAngles[k];

            if (angle <= currentAng)
            {
                double pp = (currentAng - angle);
                double pc = 0.0;

                if (k == 0)
                {
                    pc =  angle / currentAng;

                }
                else
                {
                    pc =  (angle - lightVectorAngles[k-1]) / (currentAng - lightVectorAngles[k-1]);
                }

                double xx = lightVectors[k].x * pc + lightVectors[k+1].x * (1.0 - pc);
                double yy = lightVectors[k].y * pc + lightVectors[k+1].y * (1.0 - pc);

                double magn = Math.sqrt(xx * xx + yy * yy);


                return (1.0 - dist) ;//;* magn;
            }

        }



        return lightMod;
    }

}
