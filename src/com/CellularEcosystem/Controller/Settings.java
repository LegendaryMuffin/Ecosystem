package com.CellularEcosystem.Controller;


import java.awt.*;

public class Settings
{
    //General
    public static boolean fullscreen = false;


    //Debug
    public static boolean displayGrid = true;
    public static boolean displayAxis = true;
    public static boolean showMouseCoordinates = true;
    public static Color debugGridColor = Color.gray;


    //World parameters
    public static int worldSize = 128;


    //Density noise
    public static double densityNoiseAmplitude = 0.6;
    public static double densityNoiseSize = 0.28;
    public static int densityNoiseLayers = 3;
    public static double densityNoisePersistence = 0.26;
    public static double densityNoiseLacunarity = 1.6;
    public static double densityNoiseGamma = -0.3; //adjusts final density to better fit certain range


    //Light parameters
    public static double lightCycleAmplitude = 0.5; //Light variation amount DAY/NIGHT
    public static boolean lightRotationClockwise = true;
    public static int lightResolution = 16;
    public static double lightResolutionRandomness = 0.5;
    public static double lightRadius = 0.6; // 1.0 = worldSize / 2.0
    public static double lightIntensity = 0.2;
    //Light noise
    public static double lightNoiseAmplitude = 0.5;
    public static double lightNoiseSize = 0.2; //radius
    public static int lightNoiseLayers = 1;
    public static double lightNoisePersistence = 0.1; //how much it impacts the radius
    public static double lightNoiseLacunarity = 1.3; // how it changes on the texture
    public static double lightNoiseGamma = 1.0;


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
    public static double volatilyVariation = 1.0;
    public static double baseDecay = 0.01;
    public static double decayVariation = 0.005;

    //style
    public static Color[] colonyColors = new Color[]{Color.green, Color.yellow,Color.magenta,Color.cyan};


    //Color settings
    public static Color darkBackgroundColor = new Color(12,12,12);
    public static Color lightBackgroundColor = new Color(55,50,50);

    public static Color lightColor = new Color(255,220,220);






}
