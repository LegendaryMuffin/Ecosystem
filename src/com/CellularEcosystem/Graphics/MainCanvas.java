package com.CellularEcosystem.Graphics;

import com.CellularEcosystem.Controller.*;
import com.CellularEcosystem.Objects.*;
import com.CellularEcosystem.World.World;

import javax.swing.*;
import javax.swing.plaf.synth.SynthStyleFactory;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class MainCanvas extends JComponent
{
    MainController controller;
    World world;

    DebugDraw debugDraw;

    //Transforms to draw (May contain rectangles, triangles...)
    static ArrayList<Transform> drawTransforms;


    public MainCanvas(MainController controller_, World world_)
    {
        //References
        controller = controller_;
        world = world_;

        debugDraw = new DebugDraw(controller);

        //Adds itself to frame
        Main.Screen.frame.add(this);

        drawTransforms = new ArrayList<>();


    }

    public static void AddToTransformDrawList(Transform newTransform)
    {
        try
        {
            drawTransforms.add(newTransform);
        }
        catch (ConcurrentModificationException e)
        {
            System.out.println("Calm yourself");
        }
    }



    @Override
    public void paintComponent(Graphics g)
    {
        //Wait until initialization
        if (world == null || debugDraw == null)
            return;

        super.paintComponent(g);

        //Update world tiles
        DrawWorldTiles(g);

        //Draw transforms
        for(Transform tt : drawTransforms)
        {
            tt.Draw(g);
        }

        //Update time
        g.setColor(new Color(255,255,255));
        g.drawString(controller.GetTimeString(), 20, 20);
    }


    void DrawWorldTiles(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        int unit = (int)(Math.ceil(World.worldUnit));

        for(int j = 0; j < Settings.worldSize; j++)
        {
            for(int i = 0; i < Settings.worldSize; i++)
            {
                WorldTile tile = world.tiles[i][j];
                boolean even = (i + j) % 2 == 0;

                //Draw base tile
                g2d.setColor(tile.baseColor);
                g2d.fillRect(tile.screenPosition.x,tile.screenPosition.y, unit,unit);


                if (tile.lightAmount > Settings.lightCutoff)
                {
                    //Draw light
                    double pc = Math.pow(tile.lightAmount,Settings.lightFalloffMultiplier);
                    int ww = (int)Math.round((unit + 1) * pc * 0.5);

                    g2d.setColor(tile.GetLightColor());

                    int posX0 = (tile.screenPosition.x + tile.lightOffset.x) + unit / 2 - ww - 1;
                    int posY0 = (tile.screenPosition.y + tile.lightOffset.y) + unit /2 - ww - 1;

                    g2d.fillRect(posX0,posY0,ww * 2,ww * 2);
                }




                //Draw juice
                double amount = world.tiles[i][j].amount;

                if(amount > 0.01)
                {
                    //Get level
                    for (int k = 1; k < Settings.juiceStages; k++)
                    {
                        if (amount > ((double)k / Settings.juiceStages))
                        {
                            float tt = (float)Math.ceil((double)k / Settings.juiceStages) * 0.5f;
                            g2d.setStroke(new BasicStroke(tt));

                        }

                    }


                    g2d.setColor(world.tiles[i][j].juice.mainColor);
                    g2d.drawRect(tile.screenPosition.x, tile.screenPosition.y, unit-1,unit-1);
                }
            }
        }
    }
}
