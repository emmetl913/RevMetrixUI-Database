package edu.ycp.cs320.RevMetrix.controller;
import edu.ycp.cs320.RevMetrix.model.Account;

public class AccountController {
	private Account model;
	
	public void setModel(Account model) {
		this.model = model;
	}
	
	public boolean getValidLogin(String enteredUsername, String enteredPassword) {
		if(enteredUsername.equals(model.getUsername()) && enteredPassword.equals(model.getPassword())) {
			return true;
		}
		return false;
	}
	public void signUp(String enteredUsername, String enteredPassword, String enteredEmail) {
		//check for invalid strings in Servlet
		model.setPassword(enteredPassword);
		model.setUsername(enteredUsername);
		model.setEmail(enteredEmail);
	}
	
}
