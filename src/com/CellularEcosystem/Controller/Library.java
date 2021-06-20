package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Objects.Vector2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Library {

    /* * * < U T I L I T Y   F U N C T I O N S > * * */

    public static Color LerpColor(double pc, Color color0, Color color1)
    {
        int rr = (int)Math.round((color1.getRed() - color0.getRed()) * pc);
        int gg = (int)Math.round((color1.getGreen() - color0.getGreen()) * pc);
        int bb = (int)Math.round((color1.getBlue() - color0.getBlue()) * pc);

        return new Color (color0.getRed() + rr,color0.getGreen() + gg,color0.getBlue() + bb);
    }

    public static double InverseLerp(double num, double min, double max)
    {
        if (max - min == 0.0)
            return 0.0;
        return (num - min) / (max - min);
    }

    public static Vector2 AddVectors(Vector2 vec0, Vector2 vec1)
    {
        return new Vector2(vec0.x + vec1.x, vec0.y + vec1.y);
    }

    public static double Clamp(double num, double min, double max)
    {
        return Math.min(Math.max(num, min),max);
    }

    public static int ClampInt(int num, int min, int max)
    {
        return Math.min(Math.max(num, min),max);
    }
}
