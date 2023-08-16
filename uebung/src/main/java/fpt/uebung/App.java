package fpt.uebung;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) {
		System.out.println("\n=== uebung 1 ===");
		uebung1.main();

		System.out.println("\n=== uebung 2 ===");
		uebung2.main();

		System.out.println("\n=== uebung 3 ===");
		uebung3.main();

		System.out.println("\n=== uebung 5 ===");
		uebung5.main();

		System.out.println("\n=== uebung 6 ===");
		uebung6.main();

		System.out.println("\n=== uebung 7 ===");
		uebung7.main();

		System.out.println("\n=== deadlock ===");
		deadlock.main();

		MyRunnable.main();
	}
}

// Random notes

// Equality {{{
class Obj1 {
	// usually would use getter/setters, but I'm too lazy
	public int x, y;
	public Obj1(int x, int y) { this.x = x; this.y = y; }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Obj1))
			return false;

		var obj = (Obj1) o;
		return this.x == obj.x && this.y == obj.y;
	}
}
// NOTE: when is `equals` not needed?
// * every instance is unique by nature
// * no need for checking logical equality
// * Super-class already impl'd
// * class is package-private and equals is never called

// }}}

// Copying {{{
// Shallow copy vs. Deep Copy:
// Shallow:
// * new Object created
// * all variables copied
// * nested objects are NOT copied
// Deep:
// * all nested objects also copied

// NOTE: ways to copy:
// * clone()
// * copy constructor
// * serialisation
// * factory-method

// clone() {{{
// - Object.clone()
// - creates binary copy of object (shallow copy)
// - only Cloneable classes can be cloned
class Class1 implements Cloneable {
	@Override
	public Class1 clone() {
		try {
			return (Class1) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
// }}}

// Copy Constructor {{{
class Class2 {
	private List<Obj1> list;

	public Class2(Class2 other) {
		list = new ArrayList(other.list); // shallow-copy!
	}
}
// }}}
// }}}

// Serialisation {{{
// Binary Serialisation: {{{
// NOTE:
// * serialisation with java.io.ObjectOutputStream
// * deserialisation with java.io.ObjectInputStream
// * class MUST implement _Serializable_
// * static attributes are not serialised
// * with `transient` we can exclude attributes
// * All none-static/transient attributes MUST be serialisable
//   * Runtime Checked
//   * only non-Null attributes are serialised
// Versioning:
// * every class has a `serialVersionUID`:
//   * either generated by Compiler or specified by user
// * mismatched serialVersionUIDs throws an exception
// * inherited, but not taken into account? -> private
// * [!!] serialVersionUID should be set as generated UID _depends on compiler_
class VersionedSerializableClass implements Serializable {
	public int value = 1;
	transient String ignored = "hello";
	private static final long serialVersionUID = 1l;

	public static void main() {
		var obj = new VersionedSerializableClass();
		obj.value = 3;
		try (var fos = new FileOutputStream("data.ser");
				var oos = new ObjectOutputStream(fos)) {
			oos.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

// }}}

// NOTE: XML Serialisation:
// * serialisation with java.beans.XMLEncoder
// * deserialisation with java.beans.XMLDecoder
// * Java Beans criterion (specification):
//   * Parameterless constructor
//   * all attributes are private
//   * uses public getter and setters
//   * implements the marker interface Serializable
class JavaBeansClass implements Serializable {
	private int size;
	private String name;

	public JavaBeansClass() {}
	public void setSize(int size) {this.size = size;}
	public void setName(String name) {this.name = name;}

	public int getSize() {return size;}
	public String getName() {return name;}

	public static void main() {
		var obj = new JavaBeansClass();
		try (var encoder = new XMLEncoder(System.out)) {
			encoder.writeObject(obj);
		}
	}
}
// }}}

// Composite Pattern {{{
// NOTE:
// implements a `Component` interface, to apply an operation on individual components(Leaf)
// or collection of components (Composite)
// e.g. File(Leaf) and Directory(Composite) implement a Sized interface(Component), which 
//      implements getSize()

// the Component
interface Sized { 
	int getSize();
}

// the Leaf
class File implements Sized {
	private int size = 0;
	public int getSize() {
		return size;
	}
}

// the Composite
class Directory implements Sized {
	private List<Sized> components = new ArrayList<Sized>();

	public int getSize() {
		return components
			.stream()
			.map((comp) -> comp.getSize())
			.reduce(0, Integer::sum);
	}

	public void add(Sized comp) {
		components.add(comp);
	}

	public void remvoe(Sized comp) {
		components.remove(comp);
	}
}
// }}}

// Singleton {{{

// this implementation 
class MySingleton {
	private static MySingleton instance = new MySingleton();
	private MySingleton() {}

	public static MySingleton getInstance() {
		return instance;
	}
	public void someMethod() {}

	public static void main() {
		MySingleton.getInstance().someMethod();
	}
}

enum SingletonWithEnum {
	UNIQUE_INSTANCE;
	public void someMethod() {}

	public static void main() {
		SingletonWithEnum.UNIQUE_INSTANCE.someMethod();
	}
}
// }}}

// Static Factory Method {{{
final class Coord {
	private double x, y;
	private Coord(double x, double y) {this.x = x; this.y = y;}

	public static Coord fromCartesian(double x, double y) {
		return new Coord(x, y);
	}

	public static Coord fromPolar(double distance, double angle) {
		return new Coord(distance * Math.cos(angle), distance * Math.sin(angle));
	}
}
// }}}

// Factory Method {{{
//
// }}}

// Observer Pattern {{{
// NOTE: Push vs. Pull Notification
// * Push: subject transmits all state change info to Observer, regardless of whether they want it.
// * Pull: subject transmits only requested info, e.g. that a change happened. The observer then
//   enquires for more details
interface Observer {
	void update();
}

interface Subject {
	void registerObserver(Observer o);
	void removeObserver(Observer o);

	// calls Observer.update() on all observers
	void notifyObservers();
}
// }}}

// Threads, Runnable and Async, Callables {{{
// Threads with Runnable {{{
class MyThread extends Thread {
	String greeting;
	public MyThread(String greeting) {
		this.greeting = greeting;
	}
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(greeting);
		}
	}

	public static void main() {
		var m1 = new MyThread("hi");
		var m2 = new MyThread("moin");
		m1.start();
		m2.start();
	}
}

class MyRunnable implements Runnable {
	String greeting;
	public MyRunnable(String greeting) {
		this.greeting = greeting;
	}
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(greeting);
		}
	}

	public static void main() {
		var m1 = new MyRunnable("Hi");
		var m2 = new MyRunnable("Moin");
		var t1 = new Thread(m1);
		var t2 = new Thread(m2);
		t1.start();
		t2.start();
		try {
			t1.join(); // XXX: blocks until t1 finishes
		} catch (Exception e) {
			e.printStackTrace();
		}

		// XXX: start threads with anonymous class
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("Hi");
				}
			}
		}).start();

		// XXX: start threads with lambda expr
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("Moin");
			}
		}).start();
	}
}
// }}}

// Synchronisation Basics {{{
// TODO: atomics, volatile, synchronized keyword, Locks
// }}}

// Deadlocks {{{
// TODO
//}}}

// Producer Consumer Problem / wait and notify {{{

// }}}

// Async with Callables {{{
//
// }}}
// }}}

// Database {{{
//
// }}}

// Network Programming {{{
class Server {
	public static final int port = 9000;
	public static void main() {
		try (var socket = new ServerSocket(port)) {
			while (true) {
				try (Socket conn = socket.accept();
						var os = conn.getOutputStream();
						var oos = new ObjectOutputStream(os)) {
					oos.writeObject(new Obj1(3, 4));
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Client {
	public static void main() {
		while (true) {
			try (Socket socket = new Socket(InetAddress.getLocalHost(), Server.port);
					var is = socket.getInputStream();
					var ois = new ObjectInputStream(is)) {
				Obj1 obj = (Obj1) ois.readObject();
				System.out.printf("Obj: (%d, %d)\n", obj.x, obj.y);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class UDPServer {
	public static final int port = 9001;
	public static void main(String[] args) {
		// try (var socket = new DatagramSocket(9001)) {
		// 	while (true) {
		// 		try (Socket conn = socket.receive();
		// 				var os = conn.getInputStream
		// 	}
		// }
	}
}
// }}}
