package com.CellularEcosystem.Objects;

import com.CellularEcosystem.Controller.Library;
import com.CellularEcosystem.Controller.MainController;
import com.CellularEcosystem.Controller.Settings;
import com.CellularEcosystem.World.Colony;
import com.CellularEcosystem.World.ColonyManager;
import com.CellularEcosystem.World.World;

import javax.swing.text.Position;
import java.awt.*;

public class WorldTile
{
    //References
    World world;
    ColonyManager colonyManager;


    /*ID****
    0: empty
    1: juice tile
    2: colony tile
     */


    //General
    public int id;
    boolean even;
    public double random;

    //Position
    Vector2Int position;
    public Vector2Int screenPosition;
    public double distance; // from center
    public double angle; //from center

    //Juice
    public Juice juice;
    public double amount = 0.0;

    //Density
    public double density;

    //Light
    public double baseLightAmount = 0;
    public double lightAmount;
    public Vector2Int lightOffset;


    //Style
    public Color baseColor = Color.black;


    // *** I N I T I A L I Z A T I O N

    public WorldTile(World world_, ColonyManager col_, Vector2Int pos, Vector2Int screenPosition_)
    {
        //references
        world = world_;
        colonyManager = col_;

        position = pos;
        screenPosition = screenPosition_;
        even = (position.x + position.y) % 2 == 0;

        //Get distance from center (value between 0-1)
        double xx = pos.x - Settings.worldSize / 2.0;
        double yy = pos.y - Settings.worldSize / 2.0;
        double dist = Math.sqrt(xx*xx+yy*yy);

        distance = Math.min(Settings.worldSize / 2.0, dist) / (Settings.worldSize / 2.0);

        //Angle
        angle = Math.atan2(yy,xx);

        //Calculate light vector ratios
        random = Math.random() * Settings.lightAmountRandomness;

        //Light offset
        int randX = (int)Math.round(Settings.lightPositionRandomness * (Math.random() - 0.5) * World.worldUnit);
        int randY = (int)Math.round(Settings.lightPositionRandomness * (Math.random() - 0.5) * World.worldUnit);

        lightOffset = new Vector2Int(randX,randY);
    }


    public void SetDensity(double density_)
    {
        //System.out.println(density_);

        density = density_;
        baseColor = Library.LerpColor(1.0 - density, Settings.backgroundColors[0], Settings.backgroundColors[1]);
    }




    public void SetLightAmount(double lightAmount_)
    {
        lightAmount = lightAmount_;

    }


    public void AddJuice(double newAmount)
    {
        amount += newAmount;
        amount = Library.Clamp(amount, 0.0, 1.0);

        if (amount == 0.0)
            id = 0;
    }

    public void AddJuice(Juice newJuice, double newAmount)
    {
        juice = newJuice;
        amount += newAmount;
        amount = Library.Clamp(amount, 0.0, 1.0);
        id = 1;
    }


    public void SpreadTile(WorldTile[][] oldTiles)
    {
        //Get tile data
        WorldTile old = oldTiles[position.x][position.y];
        double[][] fractions = GetSpreadFractions(oldTiles);

        //Remove amount from original cell
        double toSpread = old.amount * (1.0 - juice.viscosity);


        //Spread the juice
        for (int j = position.y - 1; j < position.y + 2; j++) {
            for (int i = position.x - 1; i < position.x + 2; i++) {

                //Avoid colony tiles
                if (oldTiles[i][j].id != 2) {
                    //Get current coordinate
                    int xx = i - position.x + 1;
                    int yy = j - position.y + 1;

                    //Get amount for tile based on free space
                    double newAmount = toSpread * fractions[xx][yy];
                    world.tiles[i][j].AddJuice(juice,newAmount);
                }
            }
        }

        world.tiles[position.x][position.y].AddJuice(-toSpread * (1.0 + juice.decay));
    }

    double[][] GetSpreadFractions(WorldTile[][] oldTiles)
    {
        double[][] frac = new double[3][3];
        double totalVolume = 0.0;

        //Check neighbouring tiles
        for (int j = position.y - 1; j < position.y + 2; j++) {
            for (int i = position.x - 1; i < position.x + 2; i++) {

                //Get current index
                int xx = i - position.x + 1;
                int yy = j - position.y + 1;

                //Check if colony tile
                if (oldTiles[i][j].id == 2) {
                    frac[xx][yy] = 0.0;
                    continue;
                }

                double dd = oldTiles[i][j].density;


                //Get free space in tile
                double volume = Math.pow(dd, juice.volatility) - oldTiles[i][j].amount;
                volume = Math.max(volume,0.0001);

                totalVolume += volume;
                frac[xx][yy] = volume;
            }
        }

        if(totalVolume == 0)
            return frac;

        //divide by total volume
        for (int j = 0; j < 3; j++)
        {
            for (int i = 0; i < 3; i++)
            {
                frac[i][j] /= totalVolume;
            }
        }

        return frac;
    }

    public Color GetLightColor()
    {
        double pc = MainController.dayProgress * Math.PI * 2.0;
        double lightMod = (1.0 + Math.cos(pc) * Settings.lightIntensity * 0.5f);

        return Library.LerpColor(lightAmount * Settings.lightIntensity * (lightMod), baseColor, Settings.lightColor);
    }


}
