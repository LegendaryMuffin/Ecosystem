package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Objects.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseMotionListener
{
    public Vector2Int screenPosition;
    public Vector2 position;

    public MouseInput()
    {
        Main.Screen.frame.addMouseMotionListener(this);

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        Point mousePoint = e.getPoint();

        screenPosition = new Vector2Int(mousePoint.x, mousePoint.y);
        position = Camera.ScreenToWorld(screenPosition);
    }
}
