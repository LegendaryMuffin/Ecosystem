package com.CellularEcosystem.Objects;

public class Vector2
{
    public double x;
    public double y;

    public Vector2()
    {
        x = 0;
        y = 0;
    }

    public Vector2(double x_, double y_)
    {
        x = x_;
        y = y_;
    }

    // * * * * I N S T A N C E   M E T H O D S * * * * * * * * * * * * * * * * * * * * * * * *

    // * * ARITHMETIC * * * * *

    // norme d'un vecteur
    public double GetMagnitude()
    {
        return Math.sqrt(GetSqrMagnitude());
    }
    
    // norme au carré
    public double GetSqrMagnitude()
    {
        return (x * x + y * y);
    }
    // angle du vecteur
    public double GetAngle()
    {
        return Math.atan2(y,x);
    }


    public void AddVector(Vector2 uu)
    {
        x += uu.x;
        y += uu.y;
    }

    public void SubtractVector(Vector2 uu)
    {
        x -= uu.x;
        y -= uu.y;
    }

    public void Normalize()
    {
        double magn = GetMagnitude();

        x /= magn;
        y /= magn;
    }


    // * * * * S T A T I C   M E T H O D S * * * * * * * * * * * * * * * * * * * * * * * *

    //VECTOR ADDITION
    public static Vector2 AddVectors(Vector2 uu, Vector2 vv)
    {
        return new Vector2(uu.x + vv.x, uu.y + vv.y);
    }

    //VECTOR SUBTRACTION
    public static Vector2 SubtractVectors(Vector2 uu, Vector2 vv)
    {
        return new Vector2(uu.x - vv.x, uu.y - vv.y);
    }

    public static Vector2 MultiplyVector(Vector2 uu, double nn)
    {
        return new Vector2(uu.x * nn, uu.y * nn);
    }

    //Illegal vector multiplication
    public static Vector2 MultiplyVector(Vector2 uu, Vector2 nn)
    {
        return new Vector2(uu.x * nn.x, uu.y * nn.y);
    }


}
