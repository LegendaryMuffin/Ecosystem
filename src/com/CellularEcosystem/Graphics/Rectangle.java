package com.CellularEcosystem.Graphics;

import com.CellularEcosystem.Controller.Camera;
import com.CellularEcosystem.Objects.*;
import com.CellularEcosystem.World.World;

import java.awt.*;

public class Rectangle extends Transform
{

    //Rectangle constructs
    public Rectangle(Vector2 position_)
    {
        position = position_;
    }


    public Rectangle(Vector2 position_, Vector2 bounds_)
    {
        position = position_;
        bounds = bounds_;
        scale = new Vector2(1.0,1.0);

        MainCanvas.AddToTransformDrawList(this);
    }


    @Override
    public void Draw(Graphics g)
    {
        Vector2 scaledBounds = Vector2.MultiplyVector(bounds, scale);

        Vector2 worldStart = Vector2.SubtractVectors(position, scaledBounds);
        Vector2Int screenStart = Camera.WorldToScreen(worldStart);

        int xx = (int)(World.worldUnit * bounds.x * 2.0 * scale.x);
        int yy = (int)(World.worldUnit * bounds.y * 2.0 * scale.y);

        if (drawMode == DrawMode.stroke)
        {
            g.drawRect(screenStart.x, screenStart.y, xx , yy) ;

        }
        else
        {
            g.drawRect(screenStart.x, screenStart.y, xx , yy) ;
        }
    }
}
