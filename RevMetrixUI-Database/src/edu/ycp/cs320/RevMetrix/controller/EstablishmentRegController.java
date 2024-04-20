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
	
	public void setModel(EstablishmentArray model) {
		this.model = model;
	}
	
	//adds a ball to the arsenal with the name and color
	public void addEstablishment(String name, String address) {
		Establishment establishment = new Establishment(0, 0, name, address);
		model.addEstablishment(establishment);
	}
	
	public void changeEstablishmentNameAtIndex(int index, String newName){
		model.getEstablishmentAtIndex(index).setEstablishmentName(newName);
	}
	
	//finds the ball that is to be removed, and removes it from the list
	public void removeEstablishment(String name) {
		for(Establishment establishment : model.getEstablishments()) {
			if(establishment.getEstablishmentName().equals(name)) {
				model.removeEstablishment(establishment);
			}
		}
	}
}
