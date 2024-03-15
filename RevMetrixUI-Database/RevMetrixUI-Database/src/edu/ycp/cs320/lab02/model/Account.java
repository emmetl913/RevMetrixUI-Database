package edu.ycp.cs320.lab02.model;


public class Account {
	private String userName, password, email;

	
	public Account(String Username, String Password, String Email) {
		this.userName = Username;
		this.password = Password;
		this.email = Email;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String Email) {
		this.email = Email;
	}
	

	
}