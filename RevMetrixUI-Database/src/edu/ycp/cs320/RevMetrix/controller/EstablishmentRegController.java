package edu.ycp.cs320.RevMetrix.controller;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Establishment;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;


public class EstablishmentRegController {
	private Establishment model;
	private ArrayList<Establishment> establishments;
	private IDatabase db = null;
	private int acc;
	
	public EstablishmentRegController(int acc) {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();	
		
		this.acc = acc;
		establishments = (ArrayList<Establishment>)getAllEstablishmentsForAccount(this.acc);
		
		// creating DB instance here	
	}
	
	public void removeEstablishment(int accID, String name) {
		db.removeEstablishment(accID, name);
	}
	
	public Establishment getEstablishmentByAccountAndEstablishmentID(int accID, int estaID){
		return (Establishment) db.getEstablishmentByAccountAndEstablishmentID(accID, estaID);
	}

	public List<Establishment> getAllEstablishmentsForAccount(int accountId) {
	
	// get the list of (Author, Book) pairs from DB
		List<Establishment> estaList = new ArrayList<Establishment>();
		try
		{
			estaList = db.getEstablishmentsByAccount(accountId);
			// return Esta for this title
				return estaList;
		}catch(NullPointerException e) {
			
		}
		
		if (estaList.isEmpty()) {
			System.out.println("Establishments for <" + accountId + "> dont exist");
			return null;
		}
	
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
	
	public void setModel(Establishment model) {
		this.model = model;
	}
	
	//adds a ball to the arsenal with the name and color
	public void addEstablishment(int acc, String name, String address) {
		db.insertNewEstablishment(acc, name, address);
	}
	
	public ArrayList<Establishment> getEstablishments(){
		establishments = (ArrayList<Establishment>) getAllEstablishmentsForAccount(acc);
		return establishments;
	}
		
}
