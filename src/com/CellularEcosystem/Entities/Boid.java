package com.CellularEcosystem.Entities;


import com.CellularEcosystem.Objects.*;

public class Boid
{
    public enum Shape {rectangle, circle, triangle}
    public Shape shape;

    //Steering
    Vector2 direction = new Vector2();
    Vector2 target = new Vector2();

    double maxSpeed = 3.0; //world units / second


    public Transform transform;


    public Boid(Vector2 spawnPosition)
    {

    }

    public void Update()
    {

    }





}
