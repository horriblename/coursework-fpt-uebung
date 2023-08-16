package fpt.uebung;

import java.util.List;
import java.util.ArrayList;
import fpt.uebung.Piston;

public class Engine {
	public Engine() {
		size = 20.;
		pistons = new ArrayList<>();
		pistons.add(new Piston());
		pistons.add(new Piston());
	}

	private double size;
	private List<Piston> pistons;

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public List<Piston> getPistons() {
		return pistons;
	}

	public void setPistons(List<Piston> pistons) {
		this.pistons = pistons;
	}
}

