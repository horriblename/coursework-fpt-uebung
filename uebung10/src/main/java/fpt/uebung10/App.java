package fpt.uebung10;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Deque;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class App {
	static final int parkingLimit = 20;
	static final List<String> models;
	static final List<CarFactory> factories;
	static Deque<DummyCar> parking;
	static volatile boolean interrupt;

	static {
		models = new ArrayList<String>();
		models.add("Civic");
		models.add("X");
		models.add("Y");
		models.add("Z");

		factories = new ArrayList<CarFactory>();
		for (String model : models) {
			factories.add(new CarFactory(model));
		}
	}

	//
	public static void main(String[] args) {
		parking = new ArrayDeque<DummyCar>();
		var p = new Thread(() -> ProducerLoop());
		var c = new Thread(() -> ConsumerLoop());
		// p.start();
		// c.start();
		//
		// try {
		// Thread.sleep(500);
		// interrupt = true;
		// p.join(500);
		// c.join(500);
		// } catch (InterruptedException e) {
		// System.err.println("Timed out: deadlock?");
		// e.printStackTrace();
		// }

		parking.clear();

		p = new Thread(() -> producerLoopWithConditions());
		c = new Thread(() -> consumerLoopWithConditions());

		// p.start();
		// c.start();
		//
		// try {
		// Thread.sleep(500);
		// interrupt = true;
		// } catch (InterruptedException e) {
		// System.err.println("Timed out: deadlock?");
		// e.printStackTrace();
		// }

	}

	static void ProducerLoop() {
		int i = 0;
		while (!interrupt) {
			i++;
			if (i >= models.size()) {
				i = 0;
			}
			var car = factories.get(i).build();

			synchronized (parking) {
				while (parking.size() == parkingLimit) {
					try {
						parking.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				parking.add(car);
				parking.notifyAll();
			}
		}
	}

	static void ConsumerLoop() {
		int i = 0;
		for (int j = 0; j < 200; j++) {
			i++;
			if (i >= models.size()) {
				i = 0;
			}
			var model = models.get(i);
			synchronized (parking) {
				while (parking.stream().noneMatch((car) -> car.getModel() == model)) {
					try {
						parking.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				var toRemove = parking
						.stream()
						.filter((car) -> car.getModel() == model)
						.findFirst()
						.orElseThrow();
				parking.remove(toRemove);
				System.out.printf("sold %s\n", model);
				parking.notifyAll();
			}
		}
	}

	static Lock lock = new ReentrantLock();
	static Condition notFull = lock.newCondition();
	static Condition notEmpty = lock.newCondition();

	static void produceWithConditions(String model) {
		lock.lock();
		try {
			try {
				while (parking.size() == parkingLimit)
					notFull.await();

				parking.add(new DummyCar(model));
				notEmpty.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			lock.unlock();
		}
	}

	static void producerLoopWithConditions() {
		int i = 0;
		while (!interrupt) {
			i++;
			if (i >= models.size()) {
				i = 0;
			}
			var model = models.get(i);

			produceWithConditions(model);
		}
	}

	// here I just assume it doesn't matter what model I sell, cuz unclear question
	static void consumeWithConditions() {
		lock.lock();
		try {
			try {
				while (parking.size() == 0)
					notEmpty.await();

				var car = parking.removeFirst();
				System.out.printf("sold %s\n", car.getModel());
				notFull.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			lock.unlock();
		}
	}

	static void consumerLoopWithConditions() {
		for (int j = 0; j < 200; j++) {
			consumeWithConditions();
		}
	}
}

// Producer
final class CarFactory {
	final String model;

	public CarFactory(String model) {
		this.model = model;
	}

	protected DummyCar build() {
		return new DummyCar(model);
	}
}
