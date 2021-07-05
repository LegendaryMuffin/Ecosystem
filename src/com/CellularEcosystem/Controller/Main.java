package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Objects.ColorPalette;

import javax.swing.JFrame;
import java.awt.*;

public class Main 
{
    //Static -> accessible everywhere

    //screen parameters
    public static class Screen
    {
        public static int width;
        public static int height;

        public static JFrame frame;
    }

    public enum DebugMode  {disabled, basic, advanced}
    public static DebugMode debugMode = DebugMode.disabled;


    //Program entry point -> Setup frame, start main thread
    public static void main(String[] args)
    {
        //Get screen dimensions
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

        Screen.width = screenDimensions.width;
        Screen.height = screenDimensions.height;


        //Create application frame
        Screen.frame = new JFrame("(O _ O)");
        Screen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set fullscreen
        Screen.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//-> Same as setPrefferedSize(width,height)

        if (Settings.fullscreen)
            Screen.frame.setUndecorated(true);

        SetupColors();

        //Create thread with MainController class -> Setup
        Thread mainThread = new Thread(new MainController());
        mainThread.start();

        //Setup colors

        //Initialize frame
        Screen.frame.getContentPane().setBackground(Settings.backgroundColors[0]);
        Screen.frame.pack();
        Screen.frame.setVisible(true);
    }


    static void SetupColors()
    {
        //Background colors
        int ll = ColorPalette.backColors.length;
        ll = (int)Math.floor(Math.random() * ll);

        Color darkBackgroundColor = ColorPalette.backColors[ll];

        int rr = Library.ClampInt((int)(darkBackgroundColor.getRed() * Settings.backgroundContrast),0,255);
        int gg = Library.ClampInt((int)(darkBackgroundColor.getGreen() * Settings.backgroundContrast),0,255);
        int bb = Library.ClampInt((int)(darkBackgroundColor.getBlue() * Settings.backgroundContrast),0,255);

        Color lightBackgroundColor = new Color(rr,gg,bb);

        Settings.backgroundColors = new Color[]{darkBackgroundColor, lightBackgroundColor};

        //Light Color
        ll = ColorPalette.lightColors.length;
        ll = (int)Math.floor(Math.random() * ll);

        Settings.lightColor = ColorPalette.lightColors[ll];



    }
}
