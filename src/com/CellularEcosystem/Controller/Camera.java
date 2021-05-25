package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Objects.*;
import com.CellularEcosystem.World.*;

public class Camera
{
    public static Vector2 position;

    static Vector2 screenOrigin;//pixel offset

    static void SetCameraSize()
    {
        screenOrigin = new Vector2(Main.Screen.width / 2.0, Main.Screen.height /2.0 );
    }

    public static Vector2Int WorldToScreen(Vector2 worldPos)
    {
        int xx = (int)(worldPos.x * World.worldUnit + screenOrigin.x);
        int yy = (int)(worldPos.y * World.worldUnit + screenOrigin.y);

        return new Vector2Int(xx,yy);
    }
    public static Vector2 ScreenToWorld(Vector2Int screenPos)
    {
        double xx = (screenPos.x - screenOrigin.x) / World.worldUnit;
        double yy = ((Main.Screen.height - screenPos.y) - screenOrigin.y) / World.worldUnit;

        return new Vector2(xx,yy);
    }


}
