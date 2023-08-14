package fpt.uebung9;

import fpt.uebung8.Engine;
import fpt.uebung8.EngineA;
import fpt.uebung8.Seat;
import fpt.uebung8.NormalSeat;
import fpt.uebung8.Color;

import fpt.uebung9.CarPartFactory;

public class UniquelyFancyCarPartFactory implements CarPartFactory {
	private UniquelyFancyCarPartFactory(String msg) {
		System.out.printf("singleton instance initialized: %s\n", msg);
	}

	// # Singleton with Eager Instantiation
	//
	// instantiated whenever the _class_ is instantiated: e.g. accessing UniquelyFancyCarPartFactory.x
	// would trigger instantiation of eagerInstance
	private static UniquelyFancyCarPartFactory eagerInstance = new UniquelyFancyCarPartFactory("eager");
	static int x = 0; // dummy data
	static UniquelyFancyCarPartFactory getEagerInstance() {
		return eagerInstance;
	}

	// # Singleton with Lazy Instantiation
	//
	// lazy initialization: only initializes when getLazyInstance() is actually called
	private static UniquelyFancyCarPartFactory lazyInstance;
	static UniquelyFancyCarPartFactory getLazyInstance() {
		if (lazyInstance == null) {
			lazyInstance = new UniquelyFancyCarPartFactory("lazy");
		}
		return lazyInstance;
	}

	// # Lazy Initialization + Double-check synchronisation
	//
	// thread-safe and performant (only first/first few calls need synchronization)
	private static volatile UniquelyFancyCarPartFactory volatileInstance;
	static UniquelyFancyCarPartFactory getInstance() {
		if (volatileInstance == null) {
			synchronized (UniquelyFancyCarPartFactory.class) {
				if (volatileInstance == null) {
					volatileInstance = new UniquelyFancyCarPartFactory("double-checked synchronisation");
				}
			}
		}
		return volatileInstance;
	}

	public Engine buildEngine() { return new EngineA(); }
	public Seat buildSeat(Color color) { return new NormalSeat(Color.BLACK); }
}
