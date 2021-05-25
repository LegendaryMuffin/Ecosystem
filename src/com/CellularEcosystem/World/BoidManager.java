package com.CellularEcosystem.World;

import com.CellularEcosystem.Controller.*;
import com.CellularEcosystem.Entities.*;

import java.util.ArrayList;

public class BoidManager
{
    public MainController controller;
    public World world;


    public ArrayList<Boid> boids;

    //spawning parameters
    double lastBoidSpawn;
    double boidSpawnRate = 0.2;
    int maximumBoids = 5;




    public BoidManager(MainController controller_, World world_)
    {
        controller = controller_;
        world = world_;

        boids = new ArrayList<>();
    }

    public void Update()
    {
        SpawnBoids();


        //Update all boids
        for(Boid bb : boids)
        {
            bb.Update();
        }
    }


    void SpawnBoids()
    {



    }
}
