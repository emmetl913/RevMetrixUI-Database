package edu.ycp.cs320.RevMetrix.controller;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.EstablishmentArray;
import edu.ycp.cs320.RevMetrix.model.Establishment;

import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;


public class EstablishmentRegController {
	private EstablishmentArray model;
	private IDatabase db = null;
	
	public EstablishmentRegController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}
	
	public void removeEstablishment(int accID, String name) {
		db.removeEstablishment(accID, name);
	}

	public List<Establishment> getAllEstablishmentsForAccount(int accountId) {
	
	// get the list of (Author, Book) pairs from DB
	List<Establishment> estaList = db.getEstablishmentsByAccount(accountId);
	
	if (estaList.isEmpty()) {
		System.out.println("Establishments for <" + accountId + "> dont exist");
		return null;
	}
	// return Esta for this title
		return estaList;
	}
	
	public Integer insertNewEstablishment(int account_id, int estb_id, String name, int time, String type, int standing) {
		// get the list of (Author, Book) pairs from DB
		Integer newEsta = db.insertNewEvent(account_id, estb_id, name, time, type, standing);
		
		if (newEsta == null) {
			System.out.println("Establishments for <" + name + "> dont exist");
			return null;
		}
		// return Esta for this title
			return newEsta;
		}
	
	public void setModel(EstablishmentArray model) {
		this.model = model;
	}
	
	//adds a ball to the arsenal with the name and color
	public void addEstablishment(int acc, String name, String address) {
		db.insertNewEstablishment(acc, name, address);
	}
	
	public void changeEstablishmentNameAtIndex(int index, String newName){
		model.getEstablishmentAtIndex(index).setEstablishmentName(newName);
	}
		
}
