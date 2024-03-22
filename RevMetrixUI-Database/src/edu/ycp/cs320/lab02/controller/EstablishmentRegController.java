package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.EstablishmentArray;
import edu.ycp.cs320.lab02.model.Establishment;


public class EstablishmentRegController {
	private EstablishmentArray model;
	
	public void setModel(EstablishmentArray model) {
		this.model = model;
	}
	//adds a ball to the arsenal with the name and color
	public void addEstablishment(String name, String address) {
		Establishment establishment = new Establishment(name, address);
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
