package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;
import edu.ycp.cs320.RevMetrix.controller.EstablishmentRegController;

public class EstablishmentArray {
	private ArrayList<Establishment> establishment;
	private EstablishmentRegController controller;
	int acc;
	
	public EstablishmentArray(int acc) {
		this.acc = acc;
		controller = new EstablishmentRegController();
		establishment = (ArrayList<Establishment>) controller.getAllEstablishmentsForAccount(this.acc);
	}
	
	public void addEstablishment(Establishment establishment) {
		this.establishment.add(establishment);
	}
	public Establishment getEstablishmentAtIndex(int index) {
		return establishment.get(index);
	}
	public ArrayList<Establishment> getEstablishments(){
		establishment = (ArrayList<Establishment>) controller.getAllEstablishmentsForAccount(acc);
		return establishment;
	}
	public int getNumberOfEstablishment() {
		return establishment.size();
	}
}
