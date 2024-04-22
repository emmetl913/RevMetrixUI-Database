package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;

public class EventArray {
	private ArrayList<Event> events;
	
	public EventArray() {
		events = new ArrayList<>();
		ArrayList<Establishment> establishment = new ArrayList<>();
		
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
