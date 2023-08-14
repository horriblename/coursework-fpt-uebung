package fpt.uebung11;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DBHelper {
	// Doesn't check if existing table is of the correct form
	static void createTable(Connection conn) {
		var sql = """
					CREATE TABLE IF NOT EXISTS user (
						userName  VARCHAR(50) PRIMARY KEY,
						pwd       VARCHAR(50) NOT NULL,
						lastName  VARCHAR(50) NOT NULL,
						firstName VARCHAR(50) NOT NULL
					)
				""";
		try (var st = conn.createStatement()) {
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void addUser(Connection conn, String userName, String pwd, String firstName, String lastName) {
		var sql = """
					INSERT INTO user (userName, pwd, firstName, lastName)
					values (?, ?, ?, ?)
				""";
		try (var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userName);
			stmt.setString(2, pwd);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);

			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static boolean usernameExists(Connection conn, String username) throws SQLException {
		var sql = """
					SELECT 1
					FROM user
					WHERE userName = ?
					LIMIT 1
				""";
		try (var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			var res = stmt.executeQuery();
			return res.next();
		}
	}

	/**
	 * Returns `empty` if username is not found
	 *
	 * @param conn
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	static Optional<String> getPassword(Connection conn, String username) throws SQLException {
		var sql = """
					SELECT pwd
					FROM user
					WHERE user.username = ?
				""";
		try (var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			var res = stmt.executeQuery();
			if (!res.next()) {
				return Optional.empty();
			}
			return Optional.of(res.getString("pwd"));
		}
	}
}
