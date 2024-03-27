package edu.ycp.cs320.RevMetrix.model;


public class Event {
	private String eventName, type, standingString, establishmentString;
	private int standing;
	private Establishment establishment;
	
	public Event(String name, String type, int standing, Establishment establishment) {
		this.eventName = name;
		this.type = type;
		this.standing = standing;
		this.establishment = establishment;
		this.standingString = Integer.toString(standing);
		this.establishmentString = establishment.getEstablishmentName();
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
		this.standingString = Integer.toString(standing);
	}
	
	public Establishment getEstablishment() {
		return establishment;
	}
	
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
		this.establishmentString = establishment.getEstablishmentName();

	}
	
	public String getStandingStringName() {
		return standingString;
	}
	
	public String getEstablishmentStringName() {
		return establishmentString;
	}
	
}
