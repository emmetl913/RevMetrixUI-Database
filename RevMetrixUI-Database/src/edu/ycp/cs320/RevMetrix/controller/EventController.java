package edu.ycp.cs320.RevMetrix.controller;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;


public class EventController {
	private Event event;
	private ArrayList<Event> events;
	private IDatabase db = null;
	private int acc;
	
	public EventController(int acc) {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();	
		
		this.acc = acc;
		events = (ArrayList<Event>)getAllEventsForAccount(acc);
		
		// creating DB instance here
		
	}
	
	
	
	public List<Event> getAllEventsForAccount(int accountId) {
		
		List<Event> eventList = new ArrayList<Event>();
		
		try
		{
			eventList = db.getEventsByAccount(accountId);
			return eventList;
		}catch(NullPointerException e) {
			
		}
		if (eventList.isEmpty()) {
			System.out.println("Establishments for <" + accountId + "> dont exist");
			return null;
		}
		// return Esta for this title
			return eventList;
		}
		
	public Integer insertNewEvent(int acc_id, int estb_id, String name, String time, String type, int standing) {
					
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

	public void setModel(Event model) {
		this.event = model;
	}
	public ArrayList<Event> getEvents(){
		events = (ArrayList<Event>)getAllEventsForAccount(acc);
		return events;
	}
	//adds a ball to the arsenal with the name and color
	public Integer addEvent(int acc_id, int estb_id, String time, String name, String type, int standing) {
		return db.insertNewEvent(acc_id, estb_id, name, time, type, standing);
	}
	
	//finds the ball that is to be removed, and removes it from the list
//	public void removeEvent(String name) {
//		for(Event event : this.event.getEvents()) {
//			if(event.getEventName().equals(name)) {
//				this.event.removeEvent(event);
//			}
//		}
	
}
