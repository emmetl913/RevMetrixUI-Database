package edu.ycp.cs320.lab02.model;


public class Account {
	private String userName, password;

	
	public Account(String Username, String Password) {
		this.userName = Username;
		this.password = Password;
	}
	
	public String getUsername() {
		return userName;
	}
	
	public void setUsername(String Username) {
		this.userName = Username;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String Password) {
		this.password = Password;
	}
	
	
}