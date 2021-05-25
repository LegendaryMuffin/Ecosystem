package com.CellularEcosystem.Objects;

import java.awt.*;

public abstract class Transform
{
    //Draw parameters
    public enum DrawMode {fill, stroke}
    public DrawMode drawMode = DrawMode.stroke;
    public Color color;

    //World presence
    public Vector2 position;
    public Vector2 bounds = new Vector2();
    public Vector2 scale = new Vector2(1.0,1.0);

    public void SetScale(double newScale)
    {
        scale.x = newScale;
        scale.y = newScale;
    }


    //Blank constructor (used for classes that inherit from transform)
    public Transform(){}


    //Method called from *MainCanvas* to draw all world transforms
    public abstract void Draw(Graphics g);

}
