package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;

public class EventArray {
	private ArrayList<Event> events;
	
	public EventArray() {
		events = new ArrayList<>();
		ArrayList<Establishment> establishment = new ArrayList<>();
		
		Establishment e1 = new Establishment(0, 0, "Colony Park Lanes & Games", "1900 Pennsylvania Ave, York, PA 17404");
		Establishment e2 = new Establishment(0, 0, "Suburban Bowlerama", "1945 S Queen St, York, PA 17403");
		
		Event b1 = new Event("Practice", "Practice", 4, e1);
		Event b2 = new Event("Ronan City Tournament", "Tournament", 35, e2);
		events.add(b1);
		events.add(b2);
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
	public Event getEventAtIndex(int index) {
		return events.get(index);
	}
	public ArrayList<Event> getEvents(){
		return events;
	}
	public int getNumberOfEvents() {
		return events.size();
	}
}
