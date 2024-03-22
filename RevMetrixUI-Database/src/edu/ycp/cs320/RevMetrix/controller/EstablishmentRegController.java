package edu.ycp.cs320.RevMetrix.controller;
import edu.ycp.cs320.RevMetrix.model.Establishment;

public class EstablishmentRegController {
	private Establishment model;
	
	public void setModel(Establishment model) {
		this.model = model;
	}
	
	public void setStrings(String establishmentName, String email, String address) {
		model.setEstablishmentName(establishmentName);
		model.setEmail(email);
		model.setAddress(address);
	}
	
}
