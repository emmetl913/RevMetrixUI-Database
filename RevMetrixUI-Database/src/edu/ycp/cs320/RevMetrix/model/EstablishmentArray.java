package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;
import edu.ycp.cs320.RevMetrix.controller.EstablishmentController;

public class EstablishmentArray {
	private ArrayList<Establishment> establishment;
	
	public EstablishmentArray() {
		
		establishment = EstablishmentController.getAllEstablishmentsForAccount(1);
			
	}
	
	public void addEstablishment(Establishment establishment) {
		this.establishment.add(establishment);
	}
	
	public void removeEstablishment(Establishment establishment) {
		this.establishment.remove(establishment);
	}
	public Establishment getEstablishmentAtIndex(int index) {
		return establishment.get(index);
	}
	public ArrayList<Establishment> getEstablishments(){
		return establishment;
	}
	public int getNumberOfEstablishment() {
		return establishment.size();
	}
}
