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

        double dd = Settings.gameTickLength / 4.0;
        double pp = (MainController.elapsedTime % dd) / dd;
        boolean noot = Math.sin(pp) >= 0.0;

        for(int j = 0; j < Settings.worldSize; j++)
        {
            for(int i = 0; i < Settings.worldSize; i++)
            {
                WorldTile tile = world.tiles[i][j];
                boolean even = (i + j) % 2 == 0;

                //Draw base tile
                g.setColor(tile.baseColor);
                g.fillRect(tile.screenPosition.x,tile.screenPosition.y, unit,unit);


                if (tile.lightAmount > Settings.lightCutoff)
                {
                    //Draw light
                    double pc = Math.pow(tile.lightAmount,Settings.lightFalloffMultiplier) * 0.5;
                    int ww = (int)Math.round(pc * unit * 0.5);

                    g.setColor(tile.GetLightColor());
                    g2d.setColor(tile.GetLightColor());
                    BasicStroke stroke = new BasicStroke(ww);
                    g2d.setStroke(stroke);

                    int posX0 = tile.screenPosition.x + ww/2 ;
                    int posY0 = tile.screenPosition.y + ww/2;
                    int posX1 = tile.screenPosition.x + ww;
                    int posY1 = tile.screenPosition.y + ww;
                    int tt0 = (int)Math.ceil(unit - ww);
                    int tt1 =(int) (pc * unit);//(int)Math.ceil(unit * pc * 2.0);


                    if (noot)
                    {
                        if (even)
                            g.drawRect(posX0,posY0,tt0,tt0);
                        else
                            g.fillRect(posX0,posY0,tt1,tt1);
                    }
                    else
                    {
                        if (even)
                            g.fillRect(posX0,posY0,tt0,tt0);
                        else
                            g.drawRect(posX0,posY0,tt1,tt1);
                    }
                }



                //Draw juice
                double amount = world.tiles[i][j].amount;

                if(amount > 0.01)
                {
                    int radius = (int)Math.ceil(unit * amount);
                    g2d.setColor(world.tiles[i][j].juice.mainColor);
                    g2d.fillOval(tile.screenPosition.x, tile.screenPosition.y, radius,radius);
                }
            }
        }
    }
}
