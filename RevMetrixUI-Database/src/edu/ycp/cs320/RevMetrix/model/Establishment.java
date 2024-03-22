package edu.ycp.cs320.RevMetrix.model;


public class Establishment {
	private String establishmentName, email, address;

	
	public Establishment(String establishmentName, String email, String address) {
		this.establishmentName = establishmentName;
		this.email = email;
		this.address = address;
	}
	
	public void setEstablishmentName(String establishmentName) {
		this.establishmentName = establishmentName;
	}
	
	public String getEstablishmentName() {
		return establishmentName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	
}