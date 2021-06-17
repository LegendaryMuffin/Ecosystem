package com.CellularEcosystem.Objects;

import java.awt.*;

public class Juice
{
    public Color mainColor;
    public Color secondaryColor;

    public double viscosity;
    public double spreadMin;
    public double volatility;
    public double decay;


    public Juice(Color mainColor_, double visc,double min, double vol, double dec)
    {
        mainColor = mainColor_;
        secondaryColor = new Color((int)(mainColor.getRed() * 0.87), (int)(mainColor.getGreen() * 0.85),(int)(mainColor.getBlue() * 0.87));

        viscosity = visc;
        spreadMin = min;
        volatility = vol;
        decay = dec;
    }



}
