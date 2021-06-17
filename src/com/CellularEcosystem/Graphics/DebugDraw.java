package com.CellularEcosystem.Graphics;

import com.CellularEcosystem.Controller.*;
import com.CellularEcosystem.World.*;
import com.CellularEcosystem.Objects.*;

import java.awt.*;

public class DebugDraw
{
    MainController controller;

    public DebugDraw(MainController controller_)
    {
        controller = controller_;
    }



    public void DrawDebug(Graphics g)
    {
        if (Main.debugMode == Main.DebugMode.disabled)
            return;

        Graphics2D g2d = (Graphics2D)g;


        //Basic debug
        if (Settings.displayGrid)
            DrawDebugGrid(g);


        if (Main.debugMode == Main.DebugMode.basic)
            return;


        //Advanced debug
        if (Settings.displayAxis)
            DrawDebugAxis(g);

        if (Settings.showMouseCoordinates)
            DrawMouseCoordinates(g2d);

    }

    void DrawDebugGrid(Graphics g)
    {
        //Draw world grid
        g.setColor(Settings.debugGridColor);

        for(int j = 0; j < Settings.worldSize; j++)
        {
            for(int i = 0; i < Settings.worldSize; i++)
            {
                if ((j + i) % 2 == 0)
                    continue;

                Vector2 pp = new Vector2(i - Settings.worldSize / 2.0,j - Settings.worldSize / 2.0);
                Vector2Int screenStart = Camera.WorldToScreen(pp);

                pp = Library.AddVectors(pp, World.worldUnitVector);
                Vector2Int screenEnd = Camera.WorldToScreen(pp);

                g.fillRect(screenStart.x, screenStart.y, screenEnd.x,screenEnd.y);
            }
        }
    }

    void DrawDebugAxis(Graphics g)
    {
        //Draw x axis
        Vector2Int xx = Camera.WorldToScreen(new Vector2(-Settings.worldSize /2.0,0));
        g.setColor(Color.cyan);
        g.fillRect(xx.x, xx.y - 1, (int)World.worldUnit * Settings.worldSize,2);

        //Draw y axis
        Vector2Int yy = Camera.WorldToScreen(new Vector2(0,-Settings.worldSize /2.0));
        g.setColor(Color.yellow);
        g.fillRect(yy.x - 1, yy.y, 2,(int)World.worldUnit * Settings.worldSize );

        //Draw origin just because I can
        Vector2Int oo = Camera.WorldToScreen(new Vector2(0,0));
        g.setColor(Color.green);
        g.fillRect(oo.x - 1, oo.y - 1, 2,2);
    }

    void DrawMouseCoordinates(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 14));
        Vector2Int pp = controller.mouse.screenPosition;

        String screenPos = "Screen: ( " + pp.x + " , " + pp.y + " )";
        String worldPos = "World: ( " + controller.mouse.position.x + " , " + controller.mouse.position.y + " )";

        g2d.drawString(screenPos, pp.x + 4, pp.y - 7);
        g2d.drawString(worldPos, pp.x + 4, pp.y + 7);

    }



}
