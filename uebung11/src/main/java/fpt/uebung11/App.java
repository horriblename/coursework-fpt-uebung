package fpt.uebung11;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
	public static void main(String[] args) {
		try (var conn = DriverManager.getConnection("jdbc:sqlite:/tmp/test.db")) {
			DBHelper.createTable(conn);
			try (var stmt = conn.createStatement()) {
				stmt.executeUpdate("DELETE FROM user");
			}
			DBHelper.addUser(conn, "user1", "123", "Bot", "Not");
			System.out.printf("'user1' exists? %b\n", DBHelper.usernameExists(conn, "user1"));
			System.out.printf("'fakeUsername' exists? %b\n", DBHelper.usernameExists(conn, "fakeUsername"));
			System.out.printf("password of user1: %s\n", DBHelper.getPassword(conn, "user1"));
			System.out.printf("password of fakeUsername: %s\n", DBHelper.getPassword(conn, "fakeUsername"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		new Thread(() -> new Server().run()).start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		new Client().mock();
	}
}

class User {
	String userName;
	String pwd;
	String lastName;
	String firstName;

	public User(String userName, String pwd, String lastName, String firstName) {
		this.userName = userName;
		this.pwd = pwd;
		this.lastName = lastName;
		this.firstName = firstName;
	}
}

class Client {
	static final int port = 9000;

	private static void simpleAssert(boolean cond) {
		if (!cond) {
			System.err.println("[client] assertion failed");
		}
	}

	public void mock() {
		var userName = "johnny";
		var pwd = "qwertyuiop";
		var firstName = "John";
		var lastName = "Doe";
		System.out.println("[client] === login before register ===");
		simpleAssert(!login(userName, pwd));
		System.out.println("[client] === register ===");
		simpleAssert(register(userName, pwd, firstName, lastName));
		System.out.println("[client] === login after register ===");
		simpleAssert(login(userName, pwd));
		System.out.println("[client] === Shutdown ===");
		simpleAssert(shutdown());
		System.out.println("[client] mock client ended");
	}

	public boolean login(String userName, String pwd) {
		try (Socket socket = new Socket(InetAddress.getLocalHost(), port);
				var in = socket.getInputStream();
				var out = socket.getOutputStream();
				var oout = new ObjectOutputStream(out)) {

			oout.writeObject(Actions.LOGIN);
			oout.writeUTF(userName);
			oout.writeUTF(pwd);
			oout.flush();

			var oin = new ObjectInputStream(in); // TODO close
			var resp = oin.readObject();
			System.out.printf("[client] received response %s\n", resp);
			return resp == Response.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean register(String userName, String pwd, String firstName, String lastName) {
		try (Socket socket = new Socket(InetAddress.getLocalHost(), port);
				var in = socket.getInputStream();
				var out = socket.getOutputStream();
				var oout = new ObjectOutputStream(out)) {

			oout.writeObject(Actions.REGISTER);
			oout.writeUTF(userName);
			oout.writeUTF(pwd);
			oout.writeUTF(firstName);
			oout.writeUTF(lastName);
			oout.flush();

			var oin = new ObjectInputStream(in);
			var resp = oin.readObject();
			System.out.printf("[client] received response %s\n", resp);
			return resp == Response.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean shutdown() {
		try (Socket socket = new Socket(InetAddress.getLocalHost(), port);
				var in = socket.getInputStream();
				var out = socket.getOutputStream();
				var oout = new ObjectOutputStream(out)) {

			oout.writeObject(Actions.SHUTDOWN);
			oout.flush();

			var oin = new ObjectInputStream(in);
			var resp = oin.readObject();
			System.out.printf("[client] received response %s\n", resp);
			return resp == Response.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

}
