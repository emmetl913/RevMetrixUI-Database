package edu.ycp.cs320.lab02.model;

import java.util.ArrayList;

public class EstablishmentArray {
	private ArrayList<Establishment> establishment;
	
	public EstablishmentArray() {
		establishment = new ArrayList<>();

		Establishment e1 = new Establishment("Colony Park Lanes & Games", "1900 Pennsylvania Ave, York, PA 17404");
		Establishment e2 = new Establishment("Suburban Bowlerama", "1945 S Queen St, York, PA 17403");

		establishment.add(e1);
		establishment.add(e2);
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
