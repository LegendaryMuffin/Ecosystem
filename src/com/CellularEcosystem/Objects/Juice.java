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
        secondaryColor = new Color((mainColor.getRed() + 253) / 2, (mainColor.getGreen() + 253) / 2,(mainColor.getBlue() + 253) / 2);

        viscosity = visc;
        spreadMin = min;
        volatility = vol;
        decay = dec;
    }



}
