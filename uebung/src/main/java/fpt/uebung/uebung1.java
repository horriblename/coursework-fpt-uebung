package fpt.uebung;

public class uebung1 {
	public static void main() {
		FizzBuzz.main();
		Fibonacci.main();

		Car car = new Car();
		car.printSpecs();
	}
}

// aufgabe 1

class FizzBuzz {
	public static void main() {
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

// Aufgabe 2

class Fibonacci {
	static int fibRec(int n) {
		if (n == 1) {
			return 1;
		}
		if (n == 0) {
			return 0;
		}
		return fibRec(n-1) + fibRec(n-2);
	}

	static int fibIter(int n) {
		int fib_i = 1;
		int fib_prev = 0;
		for (int i = 0; i < n; i++) {
			int prev2 = fib_prev;
			fib_prev = fib_i;
			fib_i = prev2 + fib_prev;
		}

		return fib_i;
	}

	public static void main() {
		System.out.printf("fib(42) = %d = %d \n", fibRec(42), fibIter(42));
	}
}

// Aufgabe 3
// Car.java
