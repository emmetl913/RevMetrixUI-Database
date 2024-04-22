package edu.ycp.cs320.RevMetrix.model;


public class Establishment {
	private String establishmentName, address;
	private int EstaId, accountId;
	
	public Establishment() {
	}
	
	public Establishment(int EstaId, int accountId, String establishmentName, String address) {
		this.establishmentName = establishmentName;
		this.address = address;
		this.EstaId = EstaId;
		this.accountId = accountId;
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
	
	public void setEstaId(int EstaId) {
		this.EstaId = EstaId;
	}
	
	public int getEstaId() {
		return EstaId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public int getAccountId() {
		return accountId;
	}
}