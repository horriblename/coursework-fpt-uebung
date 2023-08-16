package fpt.uebung11;

import java.io.Serializable;

public class User implements Serializable {
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
