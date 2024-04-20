package edu.ycp.cs320.RevMetrix.controller;
import edu.ycp.cs320.RevMetrix.model.Account;


import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.*;

public class AccountController {
	private Account model;
	
	private IDatabase db = null;
	
	public AccountController(){
		//create db instance
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	public List<Account> getAccountByUsernameAndPassword(String username, String password){
		List<Account> accountList = db.getAccountByUsernameAndPassword(username, password);
		if(accountList.isEmpty()) {
			System.out.println("No Matching Account Found for username: "+ username);
			return null;
		}
		else {
			return accountList;
		}
	}
	public List<Account> getAccountByUsername(String username){
		List<Account> accountList = db.getAccountByUsername(username);
		if(accountList.isEmpty()) {
			System.out.println("No Matching Account Found for getAccountByUsername() username: "+ username);
			return null;
		}
		else {
			return accountList;
		}
	}
	public List<Account> getAccountByEmail(String email){
		List<Account> accountList = db.getAccountByEmail(email);
		if(accountList.isEmpty()) {
			System.out.println("No Matching Account Found for getAccountByEmail() email: "+ email);
			return null;
		}
		else {
			return accountList;
		}
	}
	public void setModel(Account model) {
		this.model = model;
	}
	
public boolean insertAccountinDB(String email, String password, String username) {
		
		// insert new book (and possibly new author) into DB
		Integer account_id = db.insertNewAccountinDB(email, password, username);

		// check if the insertion succeeded
		if (account_id > 0)
		{
			System.out.println("New account (ID: " + account_id + ") successfully added to accounts table: <" + username + ">");
			
			return true;
		}
		else
		{
			System.out.println("Failed to insert new account (ID: " + account_id + ") into Books table: <" + username + ">");
			
			return false;
		}
	}
	
	//Modifying the function to try and implement the database check.
	public boolean getValidLogin(String enteredUsername, String enteredPassword) {
		List<Account> accList = db.getAccountByUsernameAndPassword(enteredUsername, enteredPassword);
		if(accList == null) {
			return false;
		}
		//if accList has values then the account must exist therefore the login is valid
		else {
			Account acc = accList.get(0);
			System.out.println("Login of "+acc.getUsername()+" with password: "+acc.getPassword());

			return true;
		}
	}
	
	//Below version was session version of function
	//public boolean getValidLogin(String enteredUsername, String enteredPassword) {
//		if(enteredUsername.equals(model.getUsername()) && enteredPassword.equals(model.getPassword())) {
//			return true;
//		}
//		return false;
//	}
	public void signUp(String enteredUsername, String enteredPassword, String enteredEmail) {
		//check for invalid strings in Servlet
		model.setPassword(enteredPassword);
		model.setUsername(enteredUsername);
		model.setEmail(enteredEmail);
	}
	
}
