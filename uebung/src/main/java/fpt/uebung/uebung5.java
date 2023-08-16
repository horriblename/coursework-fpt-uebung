package fpt.uebung;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class uebung5 {
	public static void main() {
		// requires stdin
		// task1();
		task2();
		Task3.main();
	}

	static void task1() {
		Scanner in = new Scanner(System.in);
		boolean quit = false;

		while (!quit) {
			System.out.print("Enter file path: ");
			String command = in.nextLine();
			if (command.strip().equals("quit") || command.equals("")) {
				System.err.println("\nReceived 'quit' command...");
				quit = true;
				break;
			}

			System.err.printf("Reading file %s\n", command);
			Path path = Path.of(command);

			try {
				String data = Files.readString(path);
				System.out.println(data);
			} catch (IOException err) {
				System.err.printf("Error reading file: %s\n", err);
			}
		}

		in.close();
	}

	static void task2() {
		try {
			Person fetus = new Person("Not", "Born", -1, 1);
			System.out.printf("Created a fetus lol?");
		} catch (IllegalArgumentException e) {
			System.out.println("fetus is not born yet!");
		}

		try {
			Person nameless = new Person(null, null, 0, 0);
			System.out.println("Created a nameless person?");
		} catch (NullPointerException e) {
			System.out.println("person with null name rejected");
		}

		Person john = new Person("John", "Smith", 14, 170);
		printPerson(john);

		// d)
		List<Person> people = new ArrayList<Person>();
		people.add(john);
		people.add(new Person("Joe", "Schmoe", 3, 70));
		people.add(new Person("Zane", "Ol", 76, 150));

		System.out.println("Sorting by age...");
		people.sort(new PersonAgeComparator());

		for (Person person : people) {
			printPerson(person);
		}

		System.out.println("Sorting by height...");
		// NOTE `Comparator<T>` interface is a _Functional Interface_; it has only one
		// abstract
		// method. It can be implemented as a lambda.
		people.sort((p1, p2) -> p1.getHeight() == p2.getHeight() ? 0 : p1.getHeight() > p2.getHeight() ? 1 : -1);

		for (Person person : people) {
			printPerson(person);
		}

	}

	static void printPerson(Person person) {
		System.out.printf(
				"%s, %s: Age %d, Height %.2f\n",
				person.getLastName(),
				person.getFirstName(),
				person.getAge(),
				person.getHeight());
	}
}

class Person {
	String lastName, firstName;
	int age;
	double height;

	Person(String last, String first, int a, double h) throws IllegalArgumentException {
		Objects.requireNonNull(last, "last name must not be null");
		Objects.requireNonNull(first, "first name must not be null");
		if (a < 0 || h < 0) {
			throw new IllegalArgumentException();
		}

		lastName = last;
		firstName = first;
		age = a;
		height = h;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getAge() {
		return age;
	}

	public double getHeight() {
		return height;
	}
}

class PersonAgeComparator implements Comparator<Person> {
	@Override
	public int compare(Person o1, Person o2) {
		if (o1.getAge() == o2.getAge())
			return 0;
		if (o1.getAge() > o2.getAge())
			return 1;
		return -1;
	}
}

class Task3 {
	public static void main() {
		var primes = new HashSet<Integer>();
		var odds = new HashSet<Integer>();
		var fibs = new HashSet<Integer>();

		primes.add(2);
		primes.add(3);
		primes.add(5);
		primes.add(7);

		for (int i = 1; i < 10; i += 2) {
			odds.add(i);
		}

		fibs.add(1);
		fibs.add(2);
		fibs.add(3);
		fibs.add(5);
		fibs.add(8);

		var u = SetUtils.union(primes, odds, fibs);
		System.out.println(u);

		var i = SetUtils.intersection(primes, odds, fibs);
		System.out.println(i);

		var diff = SetUtils.difference(primes, odds);
		System.out.println(diff);
	}
}

class SetUtils {
	static <T> Set<T> union(Set<T>... sets) {
		var set = new HashSet<T>();
		for (final var s : sets) {
			set.addAll(s);
		}
		return set;
	}

	static <T> Set<T> intersection(Set<T> left, Set<T>... sets) {
		Set<T> set = new HashSet<>(left);

		for (final var s : sets) {
			set.retainAll(s);
		}

		return set;
	}

	static <T> Set<T> difference(Set<T> left, Set<T> right) {
		Set<T> set = new HashSet<>(left);

		set.removeAll(right);

		return set;
	}
}
