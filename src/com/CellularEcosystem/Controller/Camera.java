package com.CellularEcosystem.Controller;

import com.CellularEcosystem.Objects.*;
import com.CellularEcosystem.World.*;

public class Camera
{
    public static Vector2 position;

    public static Vector2Int WorldToScreen(Vector2 worldPos)
    {
        int xx = (int)(worldPos.x * World.worldUnit + Main.Screen.width / 2);
        int yy = (int)(worldPos.y * World.worldUnit + Main.Screen.height /2);

        return new Vector2Int(xx,yy);
    }
    public static Vector2 ScreenToWorld(Vector2Int screenPos)
    {
        double xx = (screenPos.x - Main.Screen.width / 2.0) / World.worldUnit;
        double yy = ((Main.Screen.height - screenPos.y) - Main.Screen.height / 2.0) / World.worldUnit;

        return new Vector2(xx,yy);
    }


}
