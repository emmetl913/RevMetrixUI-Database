package edu.ycp.cs320.RevMetrix.controller;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.EventArray;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;


public class EventController {
	private EventArray event;
	private IDatabase db = null;
	
	public EventController() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}
	
	public List<Event> getAllEventsForAccount(int accountId) {
		
		// get the list of (Author, Book) pairs from DB
		List<Event> eventList = db.getEventsByAccount(accountId);
		
		if (eventList.isEmpty()) {
			System.out.println("Establishments for <" + accountId + "> dont exist");
			return null;
		}
		// return Esta for this title
			return eventList;
		}
		
	public Integer insertNewEvent(int acc_id, int estb_id, String name, int time, String type, int standing) {
					
			//acc_id, estb_id, name, time, type, standing
			
			// get the list of (Author, Book) pairs from DB
			Integer newEvent = db.insertNewEvent(acc_id, estb_id, name, time, type, standing);
			
			if (newEvent == null) {
				System.out.println("Establishments for <" + name + "> dont exist");
				return null;
			}
			// return Esta for this title
				return newEvent;
	}

	public void setModel(EventArray model) {
		this.event = model;
	}
	//adds a ball to the arsenal with the name and color
	public void addEvent(int acc_id, int estb_id, int time, String name, String type, int standing) {
		Event event = new Event(db.insertNewEvent(acc_id, estb_id, name, time, type, standing),acc_id, estb_id, name, time, type, standing);
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
