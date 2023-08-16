package fpt.uebung;

public class uebung2 {
	public static void main() {
		var eagle = new Eagle("bob", 3, 5.6f, 30.0f);
		var gull = new Seagull("george", 2, 1.2f, 21.3f);
		var peng = new Penguin("tux", 3, 8.9f, 0f);

		eagle.eatFood();
		gull.eatFood();
		peng.eatFood();
	}
}

abstract class Bird {
	private String name;
	private int age;
	private float weight;
	private float maxAirSpeed;

	public String getName() { return this.name; }
	public void setName(String name) {this.name = name;}
	public int getAge() {return this.age;}
	public void setName(int age) {this.age = age;}
	public float getWeight() {return this.weight;}
	public void setWeight(float weight) {this.weight = weight;}
	public float getMaxAirSpeed() {return this.maxAirSpeed;}
	public void setMaxAirSpeed(float maxAirSpeed) {this.maxAirSpeed = maxAirSpeed;}

	public Bird(String name, int age, float weight, float maxAirSpeed) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.maxAirSpeed = maxAirSpeed;
	}

	public abstract void eatFood();
}

abstract class FlyingBird extends Bird {
	public FlyingBird(String name, int age, float weight, float maxAirSpeed) {
		super(name, age, weight, maxAirSpeed);
	}
	public void fly() {}
	public void liftOff() {}
	public void land() {}
}

final class Eagle extends FlyingBird {
	public Eagle(String name, int age, float weight, float maxAirSpeed) {
		super(name, age, weight, maxAirSpeed);
	}

	@Override
	public void eatFood() {
		System.out.println("Ich esse kleinere Säugetiere");
	}
}

final class Seagull extends FlyingBird {
	public Seagull(String name, int age, float weight, float maxAirSpeed) {
		super(name, age, weight, maxAirSpeed);
	}

	@Override
	public void eatFood() {
		System.out.println("Ich esse Fische");
	}
}

final class Penguin extends Bird {
	public Penguin(String name, int age, float weight, float maxAirSpeed) {
		super(name, age, weight, maxAirSpeed);
	}

	@Override
	public void eatFood() {
		System.out.println("Ich esse Fische");
	}

	void jumpIntoWater() {}
	void leaveWater() {}
	void swim() {}
}
