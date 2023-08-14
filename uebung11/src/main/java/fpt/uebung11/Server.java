package fpt.uebung11;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
	private static final int port = 9000;
	private StopFlag stopFlag = new StopFlag();
	private final ReadWriteLock dbLock = new ReentrantReadWriteLock();

	public void run() {
		try (var server = new ServerSocket(port);
				var dbConn = DriverManager.getConnection("jdbc:sqlite:/tmp/server.db")) {
			DBHelper.createTable(dbConn);
			try (var stmt = dbConn.createStatement()) {
				stmt.executeUpdate("DELETE FROM user");
			}
			System.out.println("[server] started");
			while (!stopFlag.stop) {
				System.out.println("waiting...");
				try {
					Socket conn = server.accept();
					new Thread(() -> {
						try {
							ConnectionHandler.service(conn, dbConn, stopFlag, dbLock);
						} finally {
							try {
								conn.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

class ConnectionHandler {
	static void service(Socket socket, Connection dbConn, StopFlag stopFlag, ReadWriteLock dbLock) {
		try (var in = socket.getInputStream();
				var oin = new ObjectInputStream(in);
				var out = socket.getOutputStream();
				var oout = new ObjectOutputStream(out)) {

			var action = (Actions) oin.readObject();
			System.out.printf("[server] received request: action=%s\n", action);
			switch (action) {
				case LOGIN:
					dbLock.readLock();
					login(oin, oout, dbConn);
					break;
				case REGISTER:
					dbLock.writeLock();
					register(oin, oout, dbConn);
					break;
				case SHUTDOWN:
					try {
						oout.writeObject(Response.SUCCESS);
						oout.flush();
						stopFlag.stop = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	static private void login(ObjectInputStream oin, ObjectOutputStream oout, Connection dbConn) {
		try {
			var userName = oin.readUTF();
			var pwd = oin.readUTF();
			try {
				var accepted = DBHelper
						.getPassword(dbConn, userName)
						.map((real) -> real.equals(pwd))
						.orElse(false);

				if (accepted) {
					oout.writeObject(Response.SUCCESS);
					oout.flush();
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.printf("[server] Bad request: %s\n", e);
		}

		try {
			oout.writeObject(Response.FAILURE);
			oout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static private void register(ObjectInputStream oin, ObjectOutputStream oout, Connection dbConn) {
		try {
			var userName = oin.readUTF();
			var pwd = oin.readUTF();
			var firstName = oin.readUTF();
			var lastName = oin.readUTF();

			try {
				var exists = DBHelper.usernameExists(dbConn, userName);
				if (!exists) {
					DBHelper.addUser(dbConn, userName, pwd, firstName, lastName);
					oout.writeObject(Response.SUCCESS);
					oout.flush();
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.printf("[server] Bad request: %s\n", e);
		}

		try {
			oout.writeObject(Response.FAILURE);
			oout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class StopFlag {
	volatile boolean stop = false;
}
