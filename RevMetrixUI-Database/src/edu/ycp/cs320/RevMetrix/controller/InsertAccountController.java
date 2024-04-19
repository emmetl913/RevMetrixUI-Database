package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;


public class InsertAccountController {

	private IDatabase db    = null;

	public InsertAccountController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
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
}
