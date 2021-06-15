package com.CellularEcosystem.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Library {

    public static Color[] darkColors = {
            new Color(15,1,65),
            new Color(60,1,20),
            new Color(1,40,20),
            new Color(50,20,50),
            new Color(20,20,20),
    };

    public static Color[] brightColors = {
            new Color(255,160,255),
            new Color(150,255,220),
            new Color(255,255,120),
            new Color(230,230,230),
            new Color(165,255,165),
    };

    public Library()
    {

    }


    public static Color LerpColor(double pc, Color color0, Color color1)
    {
        int rr = (int)Math.round((color1.getRed() - color0.getRed()) * pc);
        int gg = (int)Math.round((color1.getGreen() - color0.getGreen()) * pc);
        int bb = (int)Math.round((color1.getBlue() - color0.getBlue()) * pc);

        return new Color (color0.getRed() + rr,color0.getGreen() + gg,color0.getBlue() + bb);
    }

    //Color palette and stuff
    public static class Style
    {
        public static Color backgroundColor = new Color(20,20,20);
        public static Color gridColor = new Color(40, 40, 40);
    }
}
