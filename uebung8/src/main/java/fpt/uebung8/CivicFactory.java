package fpt.uebung8;

import fpt.uebung8.CarFactory;
import fpt.uebung8.Civic;

final public class CivicFactory extends CarFactory {
   public CivicFactory() {
      super();
   }

   @Override
	protected Car build(Color carColor, Color seatColor) {
      return new Civic(carColor, seatColor);
   }
}
