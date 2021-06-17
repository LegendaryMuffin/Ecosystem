package com.CellularEcosystem.Controller;

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

        //Create thread with MainController class -> Setup
        Thread mainThread = new Thread(new MainController());
        mainThread.start();

        //Initialize frame
        Screen.frame.getContentPane().setBackground(Settings.darkBackgroundColor);
        Screen.frame.pack();
        Screen.frame.setVisible(true);
    }
}
