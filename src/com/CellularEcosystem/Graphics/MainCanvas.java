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
    public World world;

    //Secondary canvases
    DebugDraw debugDraw;


    //Transforms to draw (May contain rectangles, triangles...)
    static ArrayList<Transform> drawTransforms;


    public MainCanvas(MainController controller_)
    {
        controller = controller_;

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
        super.paintComponent(g);


        try { debugDraw.DrawDebug(g); }
        catch (NullPointerException e){return;}

        if (world != null)
            DrawWorldTiles(g);


        //Draw transforms
        for(Transform tt : drawTransforms)
        {
            tt.Draw(g);
        }

        g.setColor(new Color(255,255,255));
        g.drawString(controller.GetTimeString(), 20, 20);
    }

    void DrawWorldTiles(Graphics g)
    {
        for(int j = 0; j < World.size; j++)
        {
            for(int i = 0; i < World.size; i++)
            {
                Vector2 pp = new Vector2(i - World.size / 2.0,j - World.size / 2.0);
                Vector2Int screenPos = Camera.WorldToScreen(pp);

                int nn = World.worldUnit;

                g.setColor(world.tiles[i][j].GetColor());
                g.fillRect(screenPos.x, screenPos.y, nn,nn);
            }
        }
    }
}
