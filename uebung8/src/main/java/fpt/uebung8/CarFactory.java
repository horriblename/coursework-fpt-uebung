// factory:
// 1. customer orders a car in the factory
// 2. factory builds car
// 3. test car
// 4. if fail test: retry

package fpt.uebung8;

import fpt.uebung8.Car;
import fpt.uebung8.Color;

public abstract class CarFactory {
	public Car order(Color carColor, Color seatColor) {
      return build(carColor, seatColor);
   }

	abstract protected Car build(Color carColor, Color seatColor);
}
