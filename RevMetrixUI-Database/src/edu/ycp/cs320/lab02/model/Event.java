package edu.ycp.cs320.lab02.model;

import java.util.ArrayList;

public class Event {
	private String eventName, type, standingString, establishmentString;
	private int standing;
	private Establishment establishment;
	
	
	public Event(String name, String type, String standing, String establishment) {
		this.eventName = name;
		this.type = type;
		this.standingString = standing;
		this.establishmentString = establishment;
		
	}
	
	public Event(String name, String type, int standing, Establishment establishment) {
		this.eventName = name;
		this.type = type;
		this.standing = standing;
		this.establishment = establishment;
		
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public void setName(String name) {
		this.eventName = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getStanding() {
		return standing;
	}
	
	public void setStanding(int standing) {
		this.standing = standing;
	}
	
	public Establishment getEstablishment() {
		return establishment;
	}
	
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}
	
	public String getStandingStringName() {
		return standingString;
	}
	
	public void setStandingString(String standingString) {
		this.standingString = standingString;
	}
	
	public String getEstablishmentStringName() {
		return establishmentString;
	}
	
	public void setEstablishmentString(String establishmentString) {
		this.establishmentString = establishmentString;
	}
	
}
