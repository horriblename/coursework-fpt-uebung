package fpt.uebung;

import java.util.List;
import java.util.ArrayList;
import fpt.uebung.Engine;
import fpt.uebung.Wheel;
import fpt.uebung.Seat;

abstract class Vehicle {
	public Vehicle() {
	}

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

public final class Car extends Vehicle {
	private static final long serialVersionUID = 1L;
	
	public Car() {
		wheels = new ArrayList<Wheel>();
		wheels.add(new Wheel());
		wheels.add(new Wheel());
		wheels.add(new Wheel());
		wheels.add(new Wheel());
		seats = new ArrayList<Seat>();
		seats.add(new Seat());
		seats.add(new Seat());
		seats.add(new Seat());
		seats.add(new Seat());
		seats.add(new Seat());
	}

	private String model = "Generic Car";
	private Engine engine = new Engine();
	private List<Wheel> wheels;
	private List<Seat> seats;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public List<Wheel> getWheels() {
		return wheels;
	}

	public void setWheels(List<Wheel> wheels) {
		this.wheels = wheels;
	}

	public void printSpecs() {
		int i;
		System.out.println("Model: " + model);
		System.out.println("Wheels:");

		for (i = 0; i < wheels.size(); i++) {
			System.out.print("  - Diameter of Wheel ");
			System.out.print(i + 1);
			System.out.print(": ");
			System.out.println(wheels.get(i).getDiameter());
		}

		System.out.print("\nEngine size: ");
		System.out.println(engine.getSize());
		for (i = 0; i < engine.getPistons().size(); i++) {
			System.out.print("  - Thickness of Piston ");
			System.out.print(i + 1);
			System.out.print(": ");
			System.out.println(engine.getPistons().get(i).getThickness());
		}

		System.out.println("\nSeats:");
		for (i = 0; i < seats.size(); i++) {
			System.out.print("  - Color of Seat ");
			System.out.print(i + 1);
			System.out.print(": ");
			System.out.println(seats.get(i).getColor());
		}
	}
}

