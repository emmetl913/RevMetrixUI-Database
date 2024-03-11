package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.EstablishmentReg;

public class EstablishmentRegController {
	private EstablishmentReg model;
	
	public void setModel(EstablishmentReg model) {
		this.model = model;
	}
	
	public void setStrings(String establishmentName, String email, String address) {
		model.setEstablishmentName(establishmentName);
		model.setEmail(email);
		model.setAddress(address);
	}
	
}
