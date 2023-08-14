package fpt.uebung8;

import fpt.uebung8.Engine;

public class EngineA implements Engine {
   int pistons = 4;
   int size = 300;
   int fuelConsumption = 20;

   public int getNumberOfPistons() {return pistons;}
   public int getSize() {return size;}
   public int getFuelConsumption() {return fuelConsumption;}
}
