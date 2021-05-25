package com.CellularEcosystem.Objects;

public class Element
{
    public int id;
    public String name;

    public Properties properties;
    public static class Properties
    {
        public Properties(double viscosity_, double spreadRatio_, double volatility_)
        {
            viscosity = viscosity_;
            spreadRatio = spreadRatio_;
            volatility = volatility_;
        }

        public double viscosity; //(0,1) -> min amount for spread
        public double spreadRatio; // (0,1) -> base amount to be transferred to neighbouring cells
        public double volatility; //(0,1) -> Randomness of spread distribution
    }
}
