package edu.ycp.cs320.RevMetrix.model;


public class Event {
	private String eventName, type, standingString, establishmentString;
	private int standing, eventID, estbID, time;
	private Establishment establishment;
	
	public Event(int eventID, int estbID, String name, int time, String type, int standing) {
		
		this.eventID = eventID;
		this.estbID = estbID;
		this.eventName = name;
		this.time = time;
		this.type = type;
		this.standing = standing;
		
		this.standingString = Integer.toString(standing);
	}
	
	public int getTime()
	{
		return this.time;
	}
	public void setTime(int time)
	{
		this.time = time;
	}
	
	public int getEstbID()
	{
		return this.estbID;
	}
	public void setEstbID(int id)
	{
		this.estbID = id;
	}
	public int getEventID()
	{
		return this.eventID;
	}
	public void setEventID(int id)
	{
		this.eventID = id;
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
