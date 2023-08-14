package fpt.uebung8;

import java.util.List;
import fpt.uebung8.Engine;
import fpt.uebung8.Seat;
import fpt.uebung8.Color;

public interface Car {
	Engine getEngine();
	List<Seat> getSeats();
	Color getColor();
	void fuelUp();
	boolean testDrive();
}
