package fpt.uebung;

/**
 * uebung4
 */
public class uebung4 {

	public static void main() {

	}
}

interface FlyingEntity {
	float getMaxAirSpeed();

	void setMaxAirSpeed(float maxAirSpeed);

	void liftOff();

	void land();

	void fly();
}

interface SwimmingEntity {
	float getMaxWaterSpeed();

	void setMaxWaterSpeed(float maxWaterSpeed);

	void jumpIntoWater();

	void leaveWater();

	void swim();
}

// c) Composition

interface FlyBehaviour {
	float getMaxAirSpeed();

	void setMaxAirSpeed(float maxAirSpeed);

	void liftOff();

	void land();

	void fly();
}

interface SwimBehaviour {
	float getMaxWaterSpeed();

	void setMaxWaterSpeed(float maxWaterSpeed);

	void jumpIntoWater();

	void leaveWater();

	void swim();
}

abstract class BirdWithComposition {
	private FlyBehaviour flyBehaviour;
	private String name;
	private SwimBehaviour swimBehaviour;
	private float weight;
	private int age;

	public BirdWithComposition(String name, int age, float weight, FlyBehaviour fb, SwimBehaviour sb) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.flyBehaviour = fb;
		this.swimBehaviour = sb;
	}

	public FlyBehaviour getFlyBehaviour() {
		return flyBehaviour;
	}

	public String getName() {
		return name;
	}

	public SwimBehaviour getSwimBehaviour() {
		return swimBehaviour;
	}

	public float getWeight() {
		return weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setFlyBehaviour(FlyBehaviour flyBehaviour) {
		this.flyBehaviour = flyBehaviour;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSwimBehaviour(SwimBehaviour swimBehaviour) {
		this.swimBehaviour = swimBehaviour;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
}

// Aufgabe 2

class Calculator {

}
