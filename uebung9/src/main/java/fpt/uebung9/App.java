package fpt.uebung9;

import java.util.List;
import java.util.ArrayList;
import fpt.uebung8.Car;
import fpt.uebung8.Color;
import fpt.uebung8.Engine;
import fpt.uebung8.EngineA;
import fpt.uebung8.Seat;
import fpt.uebung8.NormalSeat;
import fpt.uebung9.CarPartFactory;
import fpt.uebung9.UniquelyFancyCarPartFactory;

public class App {
	public static void main(String[] args) {
		CarPartFactory partsFactory1 = new CarPartFactory1();
		var dealership = new CarDealerShip(partsFactory1);

		var car = dealership.order(Color.RED, Color.BLACK);

		System.out.println(car.getColor());
		for (int i = 0; i < 10; i++) {
			if (car.testDrive()) {
				System.out.println("Test Driving...");
			} else {
				System.out.println("Not enough fuel: refueling...");
				car.fuelUp();
			}
		}

		// System.out.println("=== Singleton Pattern ===");
		// System.out.println("build engine 1...");
		// var engine1 = UniquelyFancyCarPartFactory.getInstance().buildEngine();
		// System.out.println(engine1.getFuelConsumption());
		// 
		// System.out.println("build engine 2...");
		// var engine2 = UniquelyFancyCarPartFactory.getInstance().buildEngine();
		// System.out.println(engine2.getFuelConsumption());

		System.out.println("\n=== Singleton & Synchronisation ===");
		var threads = new ArrayList<Thread>();
		class N {
			int x = 0;
			void increment() { x += 1; }
		}
		var x = new N();
		for (int i = 0; i < 20; i++) {
			threads.add(new Thread(() -> {
				var engine = UniquelyFancyCarPartFactory.getInstance().buildEngine();
				x.increment();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					//TODO: handle exception
				}
				// System.out.println(engine.getFuelConsumption());
			}));
		}

		for (var i : threads) {
			i.start();
		}

		for (var i : threads) {
			try {
				i.join();
			} catch (InterruptedException e) {
				//TODO: handle exception
			}
		}

		System.out.println(x.x);
	}
}

class GenericCar implements Car {
	final List<Seat> seats;
	final Engine engine;
	final Color color;
	static final int MAX_FUEL = 90;
	int fuel = MAX_FUEL;

	GenericCar(Color carColor, Engine engine, List<Seat> seats) {
		this.color = carColor;
		this.engine = engine;
		this.seats = seats;
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

class CarDealerShip {
	CarPartFactory carPartFactory;

	CarDealerShip(CarPartFactory carPartFactory) {
		this.carPartFactory = carPartFactory;
	}

	public Car order(Color carColor, Color seatColor) {
		var engine = carPartFactory.buildEngine();
		List<Seat> seats = new ArrayList();
		for (int i = 0; i < 5; i++) {
			seats.add(carPartFactory.buildSeat(seatColor));
		}
		return new GenericCar(carColor, engine, seats);
	}
}

class CarPartFactory1 implements CarPartFactory {
	public Engine buildEngine() { return new EngineA(); }
	public Seat buildSeat(Color color) { return new NormalSeat(color); }
}
