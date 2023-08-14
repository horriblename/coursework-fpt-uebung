class main {
    public static void main(String[] args) {
        Car car = new Car();
        car.printSpecs();
    }
}

class FizzBuzz {
	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			if (i % 15 == 0) {
				System.out.println("FizzBuzz");
				continue;
			}
			if (i % 3 == 0) {
				System.out.println("Fizz");
				continue;
			}
			if (i % 5 == 0) {
				System.out.println("Buzz");
				continue;
			}
			System.out.println(i);
		}
	}
}

enum Color{
    RED,
    GREEN,
    BLUE,
};

abstract class Vehicle {
    private int ID;

    public int  getID()         { return ID; }
    public void setID(int id)   { this.ID = id; }
}

class Wheel {
    private double diameter;
    public Wheel(double diameter) {
        this.diameter = diameter;
    }

    public double   getDiameter()               { return diameter; }
    public void     setDiameter(double diameter){ this.diameter = diameter; }
}

class Engine {
    private double size;
    private Piston[] pistons;
    
    public Engine(double size, int num_of_pistons, float piston_thickness) {
        this.size = size;
        pistons = new Piston[num_of_pistons];
        for (int i = 0; i < num_of_pistons; i++){
            pistons[i] = new Piston(piston_thickness);
        }
    }
    
    public double   getSize() { return size; }
    public void     setSize(double size) { this.size = size; }
    public Piston[] getPistons() { return pistons; }
}

class Piston {
    private float thickness;
    
    public Piston(float thickness) {
        this.thickness = thickness;
    }

    public float getThickness() { return thickness; }
    public void  setThickness(float thickness) { this.thickness = thickness; }
}

class Seat {
    private Color color;

    public Seat(Color color) {
        this.color = color;
    }
    public Color getColor() { return color; }
    public void  setColor(Color color) { this.color = color; }

    
}

class Car extends Vehicle {
    private String  model;
    private Wheel[] wheels;
    private Engine  engine;
    private Seat[]  seats;

    public Car() {
        model = "Mk 1";
        wheels = new Wheel[4];
        wheels[0] = new Wheel(12.5);
        wheels[1] = new Wheel(12.5);
        wheels[2] = new Wheel(15);
        wheels[3] = new Wheel(15);
        engine = new Engine(50, 4, (float) 10.2);
        int num_of_seats = 5;
        seats = new Seat[num_of_seats];
        for (int i = 0; i < num_of_seats; i++){
            seats[i] = new Seat(Color.RED);
        }

        System.out.println("Car instance created\n");
    }
    
    public void printSpecs() {
        int i;
        System.out.println("Model: " + model);
        System.out.println("Wheels:");

        for (i = 0; i < wheels.length; i++) {
            System.out.print("  - Diameter of Wheel ");
            System.out.print(i + 1);
            System.out.print(": ");
            System.out.println(wheels[i].getDiameter());
        }

        System.out.print  ("\nEngine size: ");
        System.out.println(engine.getSize());
        for (i = 0; i < engine.getPistons().length; i++) {
            System.out.print("  - Thickness of Piston ");
            System.out.print(i + 1);
            System.out.print(": ");
            System.out.println(engine.getPistons()[i].getThickness());
        }
        
        System.out.println("\nSeats:");
        for (i = 0; i < seats.length; i++) {
            System.out.print("  - Color of Seat ");
            System.out.print(i + 1);
            System.out.print(": ");
            System.out.println(seats[i].getColor());
        }
    }
}
