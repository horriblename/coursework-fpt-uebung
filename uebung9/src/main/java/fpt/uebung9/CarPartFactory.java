package fpt.uebung9;

import fpt.uebung8.Engine;
import fpt.uebung8.Seat;
import fpt.uebung8.Color;

interface CarPartFactory {
	Engine buildEngine();
	Seat buildSeat(Color color);
}
