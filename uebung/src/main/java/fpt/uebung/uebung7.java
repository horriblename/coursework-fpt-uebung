package fpt.uebung;

import java.beans.XMLEncoder;
import java.util.List;
import java.util.ArrayList;
import fpt.uebung.Car;

public class uebung7 {
	public static void main() {
		var car = new Car();
		System.out.println(car);
		// System.out.println(car);

		try (var out = System.out;
				var encoder = new XMLEncoder(out);) {
			encoder.writeObject(car);
		}
	}
}

class Example {
	public String name;
	Example() {
		name = "Hello";
	}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
}

class UserSettings {
	UserSettings() {
		fieldOne = 1;
		fieldTwo = "two";
		fieldThree = false;
	}

	private Integer fieldOne;
	private String fieldTwo;
	private boolean fieldThree;

	//constructors, setters, getters
}
