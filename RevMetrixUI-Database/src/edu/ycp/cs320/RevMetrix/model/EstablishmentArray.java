package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;
import edu.ycp.cs320.RevMetrix.controller.EstablishmentRegController;

public class EstablishmentArray {
	private ArrayList<Establishment> establishment;
	private EstablishmentRegController controller;
	
	public EstablishmentArray(int acc) {
		controller = new EstablishmentRegController();
		establishment = (ArrayList<Establishment>) controller.getAllEstablishmentsForAccount(acc);

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
