package com.CellularEcosystem.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.CellularEcosystem.Objects.*;

public class KeyInput implements KeyListener
{
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;

    public KeyInput()
    {
        Main.Screen.frame.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyChar())
        {
            case 'a': left = true; break;
            case 'd': right = true; break;
            case 'w': up = true; break;
            case 's': down = true; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyChar())
        {
            case 'a': left = false; break;
            case 'd': right = false; break;
            case 'w': up = false; break;
            case 's': down = false; break;
        }
    }


    public Vector2 GetInputVector()
    {
        int xx = 0;
        int yy = 0;

        xx += left ? -1 : 0;
        xx += right ? 1 : 0;
        yy += up ? 1 : 0;
        yy += down ? -1 : 0;

        Vector2 newVec = new Vector2(xx,yy);

        if (xx != 0 && yy != 0)
            newVec.Normalize();

        return newVec;
    }
}
