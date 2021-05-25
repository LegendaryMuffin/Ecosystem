package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.MainController;
import com.CellularEcosystem.Objects.Vector2;

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
    int noiseWidth;
    int noiseHeight;
    double[][] noiseMap;

    boolean noiseLoaded = false;

    //Base parameters
    double baseScale = 0.111; //% of noiseMap covered with worldTiles
    double scaleVariationAmount = 0.41;
    Vector2 scaleModifier;
    double rotationAmount = 0.07;
    boolean clockwise = true;

    Vector2 noisePos;



    public WorldNoise(MainController controller_, World world_)
    {
        controller = controller_;
        world = world_;

        SetupNoiseMap();


        //Random position on noiseMap
        double amp = (1.0 - baseScale * 2.0);
        double x = Math.random() * amp + baseScale;
        double y = Math.random() * amp + baseScale;
        noisePos = new Vector2(x,y);

        //Random scale modifier -> leads to elliptical orbit on noiseMap
        double cc = 1.0 - scaleVariationAmount / 2.0;
        scaleModifier = new Vector2(cc,cc);
        scaleModifier.x += (Math.random() * scaleVariationAmount);
        scaleModifier.y += (Math.random() * scaleVariationAmount);

        noiseLoaded = true;
    }

    void SetupNoiseMap()
    {
        //Get Texture
        try { noiseTexture = ImageIO.read(new File("src/sprites/noiseTexture.png"));}
        catch (IOException e) {e.printStackTrace();}

        int noiseWidth = noiseTexture.getWidth();
        int noiseHeight = noiseTexture.getHeight();

        //System.out.println(noiseWidth);

        noiseMap = new double[noiseWidth][noiseHeight];

        for(int j = 0; j < noiseHeight; j ++)
        {
            for(int i = 0; i < noiseWidth; i ++)
            {
                Color col = new Color(noiseTexture.getRGB(i,j));

                noiseMap[i][j] = (col.getRed() + col.getGreen() + col.getBlue()) / 765.0;

            }
        }

    }

    public void Update()
    {
        if (!noiseLoaded)
            return;

        UpdateDensity();
    }

    Vector2 GetCurrentNoiseVector()
    {

        double ang = controller.GetTimeAngle();

        if (clockwise)
            ang = Math.PI * 2.0 - ang;

        double xx = Math.cos(ang) * scaleModifier.y * rotationAmount + noisePos.x;
        double yy = Math.sin(ang) * scaleModifier.x * rotationAmount + noisePos.y;

         return new Vector2(xx, yy);
    }

    void UpdateDensity()
    {
        Vector2 noiseVector = GetCurrentNoiseVector();

        for(int j = 0; j < World.size; j++)
        {
            for(int i = 0; i < World.size; i++)
            {
                if (MainController.ticks % 2 == i % 2)
                   continue;


                //Get noise
                double x = ((double)i / World.size - 0.5) * baseScale * scaleModifier.x + noiseVector.x;
                double y = ((double)j / World.size - 0.5) * baseScale * scaleModifier.y + noiseVector.y;


                int noiseX = (int) Math.floor(x * 512);
                int noiseY = (int) Math.floor(y * 512);
                noiseX = ClampInt(noiseX,0,511);
                noiseY = ClampInt(noiseY,0,511);

                world.tiles[i][j].SetNoise(noiseMap[noiseX][noiseY] * 0.85 + noiseMap[(noiseX * 2) % 511][(noiseY * 2) % 511] * 0.3255);
            }
        }
    }


    public int ClampInt(int num, int min, int max)
    {
        return Math.min(Math.max(num, min), max);
    }
}
