package fpt.uebung8;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import fpt.uebung8.Car;
import fpt.uebung8.EngineA;
import fpt.uebung8.Seat;
import fpt.uebung8.Color;

public class Civic implements Car {
   final Engine engine = new EngineA();
   final List<Seat> seats;
   final Color color;
   static final int MAX_FUEL = 2000;
   int fuel;

   Civic(Color carColor, Color seatColor) {
      color = carColor;
      seats = new ArrayList();
      for (int i = 0; i < 5; i++) {
         seats.add(new GenericSeat(seatColor));
      }

      fuel = MAX_FUEL;
   }
	public Engine getEngine() { return engine; }
	public List<Seat> getSeats() { return seats; }
	public Color getColor() { return color; }
	public void fuelUp() { fuel = MAX_FUEL; }
	public boolean testDrive() {
      if (fuel < engine.getFuelConsumption()) {
         return false;
      }

      fuel -= engine.getFuelConsumption();
      return true;
   }
}

final class GenericSeat implements Seat {
   final Color color;

   GenericSeat(Color color) {
      this.color = color;
   }

   public Color getColor() { return color; }
}
