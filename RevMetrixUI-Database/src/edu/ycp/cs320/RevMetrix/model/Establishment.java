package edu.ycp.cs320.RevMetrix.model;


public class Establishment {
	private String establishmentName, address;

	public Establishment() {
		
	}
	
	public Establishment(String establishmentName, String address) {
		this.establishmentName = establishmentName;
		this.address = address;
	}
	
	public void setEstablishmentName(String establishmentName) {
		this.establishmentName = establishmentName;
	}
	
	public String getEstablishmentName() {
		return establishmentName;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
}