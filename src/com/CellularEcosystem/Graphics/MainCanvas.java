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

    BasicStroke colonyStroke;

    DebugDraw debugDraw;

    int margin = 1;

    //Transforms to draw (May contain rectangles, triangles...)
    static ArrayList<Transform> drawTransforms;


    public MainCanvas(MainController controller_, World world_)
    {
        //References
        controller = controller_;
        world = world_;

        colonyStroke = new BasicStroke(2);

        //Set margin
        margin = (int)Math.floor(Main.Screen.height - World.worldUnit * Settings.worldSize);

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
        for(int j = 0; j < Settings.worldSize; j++)
        {
            for(int i = 0; i < Settings.worldSize; i++)
            {
                Vector2 pp = new Vector2(i - Settings.worldSize / 2.0,j - Settings.worldSize / 2.0);
                Vector2Int screenPos = Camera.WorldToScreen(pp);

                int nn = (int)Math.ceil(World.worldUnit);

                //Draw tile
                double amount = world.tiles[i][j].amount;
                int xx = (int)(screenPos.x - World.worldUnit) + margin + 1;
                int yy = (int)(screenPos.y - World.worldUnit) + margin + 1;


                g.setColor(world.tiles[i][j].GetColor());
                g.fillRect(xx,yy, nn,nn);

                //Draw juice
                if(amount > 0.05)
                {
                    int mod = (int)(amount * World.worldUnit);


                    if ((i + j) % 2 == 0)
                        g2d.setColor(world.tiles[i][j].juice.mainColor);
                    else
                        g2d.setColor(world.tiles[i][j].juice.secondaryColor);

                    colonyStroke = new BasicStroke(mod);
                    g2d.setStroke(colonyStroke);
                    g2d.drawRect(xx, yy, nn-2,nn-2);
                }
            }
        }
    }
}
