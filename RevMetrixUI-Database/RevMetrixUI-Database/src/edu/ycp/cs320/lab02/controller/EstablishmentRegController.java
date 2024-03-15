package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.Establishment;

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
