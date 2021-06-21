package com.CellularEcosystem.Controller;


import java.awt.*;

public class Settings
{
    //General
    public static boolean fullscreen = false;
    public static int gameTickLength = 90;
    public static double targetFrameRate = 1000.0 / 60.0;


    //Debug
    public static boolean displayGrid = true;
    public static boolean displayAxis = true;
    public static boolean showMouseCoordinates = true;
    public static Color debugGridColor = Color.gray;


    //World parameters
    public static int worldSize = 129;


    //Density noise
    public static double densityNoiseAmplitude = 0.6;
    public static double densityNoiseSize = 0.1;
    public static int densityNoiseLayers = 3;
    public static double densityNoisePersistence = 0.22;
    public static double densityNoiseLacunarity = 1.6;
    public static double densityNoiseGamma = -0.3; //adjusts final density to better fit certain range


    //Light parameters
    public static double lightCycleAmplitude = 0.9; //Light variation amount DAY/NIGHT
    public static boolean lightRotationClockwise = true;
    public static double lightRadius = 1.0; // 1.0 = worldSize / 2.0
    public static double lightIntensity = 0.4;
    public static double lightFadeTime = 200.0;
    public static double lightRandomness = 0.0;
    public static double lightFalloffMultiplier = 1.0;
    public static double lightCutoff = 0.0;

    //Light noise
    public static double lightNoiseAmplitude = 0.9;
    public static double lightNoiseSize = 0.03; //radius
    public static int lightNoiseLayers = 3;
    public static double lightNoisePersistence = 0.24; //how much it impacts the radius
    public static double lightNoiseLacunarity = 1.4; // how it changes on the texture
    public static double lightNoiseGamma = 0.0;


    //Colony parameters
    public static int maxColonies = 1;
    public static double colonySpawnDistance = 0.75;
    public static double colonySpawnDistanceVariation = 0.12;

    //spread
    public static double baseViscosity = 0.4;
    public static double viscosityVariation = 0.35;
    public static double baseMinimumSpread = 0.3;
    public static double minimumSpreadVariation = 0.26;
    public static double baseVolatility = 3;
    public static double volatilityVariation = 1.0;
    public static double baseDecay = 0.01;
    public static double decayVariation = 0.005;

    //style
    public static Color[] colonyColors = new Color[]{Color.green, Color.yellow,Color.magenta,Color.cyan};


    //Color settings
    public static Color darkBackgroundColor = new Color(0,20,20);
    public static Color lightBackgroundColor = new Color(16,60,40);

    public static Color lightColor = new Color(255,240,190);



}
