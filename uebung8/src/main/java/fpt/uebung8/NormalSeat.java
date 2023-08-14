package fpt.uebung8;

import fpt.uebung8.Seat;
import fpt.uebung8.Color;

public class NormalSeat implements Seat {
	final Color color;
	public NormalSeat(Color color) {
		this.color = color;
	}

	public Color getColor() { return color; }
}
