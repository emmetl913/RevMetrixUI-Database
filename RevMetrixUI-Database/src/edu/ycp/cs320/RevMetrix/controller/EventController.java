package edu.ycp.cs320.RevMetrix.controller;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.BowlingModels.Event;
import edu.ycp.cs320.RevMetrix.model.BowlingModels.EventArray;


public class EventController {
	private EventArray event;

	public void setModel(EventArray model) {
		this.event = model;
	}
	//adds a ball to the arsenal with the name and color
	public void addEvent(String name, String type, int standing, Establishment establishment) {
		Event event = new Event(name, type, standing, establishment);
		this.event.addEvent(event);
	}
	public void changeEventNameAtIndex(int index, String newName){
		event.getEventAtIndex(index).setName(newName);
	}
	//finds the ball that is to be removed, and removes it from the list
	public void removeEvent(String name) {
		for(Event event : this.event.getEvents()) {
			if(event.getEventName().equals(name)) {
				this.event.removeEvent(event);
			}
		}
	}
	
}
