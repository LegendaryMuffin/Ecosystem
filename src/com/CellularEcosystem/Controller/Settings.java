package com.CellularEcosystem.Controller;


import java.awt.*;

public class Settings
{
    //General
    public static boolean fullscreen = false;

    //Style
    public static double backgroundContrast = 1.6;
    public static Color[] backgroundColors;
    public static Color lightColor;


    //Debug
    public static boolean displayGrid = true;
    public static boolean displayAxis = true;
    public static boolean showMouseCoordinates = true;
    public static Color debugGridColor = Color.gray;


    //World parameters
    public static int worldSize = 120;

    public static double targetFrameRate = 5.0;
    public static int minuteLength = 60;
    public static int hourLength = 60;
    public static int dayLength = 24;
    public static double timeMultiplier = 1.0;


    //Density noise
    public static double densityNoiseAmplitude = 1.1;
    public static double densityNoiseSize = 0.11;
    public static int densityNoiseLayers = 3;
    public static int densityLayers = 18;
    public static double densityNoisePersistence = 0.5;
    public static double densityNoiseLacunarity = 1.6;
    public static double densityNoiseGamma = -0.1; //adjusts final density to better fit certain range


    //Light parameters
    public static double lightCycleAmplitude = 0.12; //Light variation amount DAY/NIGHT
    public static boolean lightRotationClockwise = true;
    public static int lightRotationsPerDay = 2;
    public static double lightRadius = 1.0; // 1.0 = worldSize / 2.0
    public static double lightIntensity = 0.2;
    public static double lightAmountRandomness = 0.1;
    public static double lightPositionRandomness = 0.3;
    public static double lightFalloffMultiplier = 1.3;
    public static double lightCutoff = 0.08;
    public static double lightGammaMultiplier = 1.0;

    //Light noise
    public static double lightNoiseAmplitude = 0.9;
    public static double lightNoiseSize = 0.08; //radius
    public static int lightNoiseLayers = 5;
    public static double lightNoisePersistence = 0.45; //how much it impacts the radius
    public static double lightNoiseLacunarity = 1.4; // how it changes on the texture
    public static double lightNoiseGamma = 0.0;


    //Colony parameters
    public static int maxColonies = 1;
    public static double colonySpawnDistance = 0.75;
    public static double colonySpawnDistanceVariation = 0.12;
    public static int juiceStages = 5;
    public static double baseProductionRate = 0.0;//per tick

    //Spread
    public static double baseViscosity = 0.4;
    public static double viscosityVariation = 0.35;
    public static double baseMinimumSpread = 0.3;
    public static double minimumSpreadVariation = 0.26;
    public static double baseVolatility = 3;
    public static double volatilityVariation = 1.0;
    public static double baseDecay = 0.0;
    public static double decayVariation = 0.005;

    //style
    public static Color[] colonyColors = new Color[]{Color.green, Color.yellow,Color.magenta,Color.cyan};





}
