package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.Account;

public class AccountController {
	private Account model;
	
	public void setModel(Account model) {
		this.model = model;
	}
	
	public boolean getValidLogin(String enteredUsername, String enteredPassword) {
		if(enteredUsername != model.getUsername() || enteredPassword != model.getPassword()) {
			return false;
		}
		return true;
	}
	public void SignUp(String enteredUsername, String enteredPassword) {
		//check for invalid strings in Servlet
		model.setPassword(enteredPassword);
		model.setUsername(enteredPassword);
	}
	
}
