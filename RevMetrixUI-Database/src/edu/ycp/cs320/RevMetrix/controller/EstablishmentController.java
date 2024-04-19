package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;

public class EstablishmentController {

	private IDatabase db = null;

	public EstablishmentController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}

	public ArrayList<Establishment> getAllEstablishmentsForAccount(int accountId) {
		
		// get the list of (Author, Book) pairs from DB
		ArrayList<Establishment> estaList = db.findEstablishmentByAccount(accountId);
		
		if (estaList.isEmpty()) {
			System.out.println("Establishments for <" + accountId + "> dont exist");
			return null;
		}
		
		// return authors for this title
		return estaList;
	}
}

