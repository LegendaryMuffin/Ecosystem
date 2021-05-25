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
        JFrame newFrame = new JFrame("(O _ O)");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setPreferredSize(new Dimension(Screen.width, Screen.height));

        Screen.frame = newFrame;

        //Load resources
        Library lib = new Library();

        //Create thread with MainController class -> start
        Thread mainThread = new Thread(new MainController(lib));
        mainThread.start();

        newFrame.pack();
        newFrame.setVisible(true);
    }
}
