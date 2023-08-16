package fpt.uebung;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class uebung3 {
	public static void main() {
		var security = new SecuritySystem();

		var adam = new EmployeeID(
				"Adam",
				"Savage",
				new Address(1234, "LA", "Fakestr", 30),
				new Birthday(1980, Month.FEBRUARY, 28),
				SecurityAccessLevel.LEVEL_1);
		var alice = new EmployeeID(
				"Alice", "Smith",
				new Address(34, "LA", "Fakestr", 30),
				new Birthday(1980, Month.MARCH, 31),
				SecurityAccessLevel.LEVEL_3);

		security.addEmployee(adam);
		security.addEmployee(alice);

		security.addArea("HR", SecurityAccessLevel.LEVEL_1);
		security.addArea("Lab", SecurityAccessLevel.LEVEL_3);
		security.addArea("Archive", SecurityAccessLevel.LEVEL_4);

		assertTrue(hasAccess(security, adam, "HR"));
		assertTrue(hasAccess(security, alice, "HR"));
		assertTrue(!hasAccess(security, adam, "Lab"));
		assertTrue(hasAccess(security, alice, "Lab"));
		assertTrue(!hasAccess(security, adam, "Archive"));
		assertTrue(!hasAccess(security, alice, "Archive"));
	}

	private static boolean hasAccess(SecuritySystem security, EmployeeID emp, String area) {
		var acc = security.hasAccess(emp, area);
		if (acc) {
			System.out.printf("%s has access to area %s\n", emp.getLastName(), area);
		} else {
			System.out.printf("%s NOT allowed in area %s\n", emp.getLastName(), area);
		}
		return acc;
	}

	private static void assertTrue(boolean cond) {
		if (!cond) {
			throw new RuntimeException("assertion failed");
		}
	}
}

class EmployeeID {
	static int UIDGenerator = 1;

	final private Birthday birthday;
	final private int uid;

	private Address address;
	private SecurityAccessLevel accessLevel;
	private String firstName;
	private String lastName;

	public Birthday getBirthday() {
		return birthday;
	}

	public int getUID() {
		return uid;
	}

	public Address getAddress() {
		return address;
	}

	public SecurityAccessLevel getAccessLevel() {
		return accessLevel;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setAccessLevel(SecurityAccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	EmployeeID(
			String firstName,
			String lastName,
			Address address,
			Birthday birthday,
			SecurityAccessLevel accessLevel) {
		this.uid = UIDGenerator;
		UIDGenerator++;

		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.birthday = birthday;
		this.accessLevel = accessLevel;
	}
}

record Address(int postCode, String city, String street, int houseNr) {
}

record Birthday(int year, Month month, int day) {
	public Birthday {
		int daysInMonth = 0;
		if (month == Month.FEBRUARY) {
			daysInMonth = 28;
		} else if (month.getValue() % 2 == 0) {
			daysInMonth = 30;
		} else {
			daysInMonth = 31;
		}

		if (day < 1 || day > daysInMonth) {
			throw new IllegalArgumentException();
		}
	}
}

enum Month {
	JANUARY(1),
	FEBRUARY(2),
	MARCH(3),
	APRIL(4),
	MAY(5),
	JUNE(6),
	JULY(7),
	AUGUST(8),
	SEPTEMBER(9),
	OCTOBER(10),
	NOVEMBER(11),
	DECEMBER(12);

	private final int value;

	Month(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}

class SecuritySystem {
	private Collection<EmployeeID> employees = new ArrayList<EmployeeID>();
	private Map<String, SecurityAccessLevel> areaAccessRequirement = new HashMap<String, SecurityAccessLevel>();

	void addEmployee(EmployeeID employee) {
		this.employees.add(employee);
	}

	boolean isEmployed(EmployeeID employee) {
		return this.employees.contains(employee);
	}

	EmployeeID getEmployeeByUID(int UID) {
		return this.employees
				.stream()
				.filter((employee) -> employee.getUID() == UID)
				.findFirst()
				.orElseThrow();
	}

	void addArea(String areaName, SecurityAccessLevel accessLevel) {
		areaAccessRequirement.put(areaName, accessLevel);
	}

	void removeArea(String areaName) {
		areaAccessRequirement.remove(areaName);
	}

	boolean hasAccess(EmployeeID employee, String area) {
		var req = areaAccessRequirement.get(area);
		if (req == null) {
			return false;
		}

		return employee.getAccessLevel().ordinal() >= req.ordinal();
	}
}

enum SecurityAccessLevel {
	LEVEL_1(1),
	LEVEL_2(2),
	LEVEL_3(3),
	LEVEL_4(4);

	private int value;

	SecurityAccessLevel(int value) {
		this.value = value;
	}

	int oridnal() {
		return value;
	}
}
