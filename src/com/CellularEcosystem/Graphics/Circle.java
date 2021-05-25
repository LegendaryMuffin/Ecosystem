package com.CellularEcosystem.Graphics;

import com.CellularEcosystem.Controller.Camera;
import com.CellularEcosystem.Objects.*;
import com.CellularEcosystem.World.*;

import java.awt.*;

public class Circle extends Transform
{

    //Circle constructor
    public Circle(Vector2 position_, double radius)
    {
        position = position_;
        bounds = new Vector2(radius,radius);
        scale = new Vector2(1.0,1.0);

        MainCanvas.AddToTransformDrawList(this);
    }


    @Override
    public void Draw(Graphics g)
    {
        Vector2 scaledBounds = Vector2.MultiplyVector(bounds, scale);
        Vector2Int screenStart = Camera.WorldToScreen(position);

        int xx = (int)(World.worldUnit * bounds.x * 2.0 * scale.x);
        int yy = (int)(World.worldUnit * bounds.y * 2.0 * scale.y);

        if (drawMode == DrawMode.stroke)
        {
            g.drawArc(screenStart.x, screenStart.y, xx,yy, 0, 360);

        }
        else
        {
            g.drawArc(screenStart.x, screenStart.y, xx,yy, 0, 360);
        }
    }
}
