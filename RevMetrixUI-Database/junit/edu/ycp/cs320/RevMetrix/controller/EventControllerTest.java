package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.model.Event;
import java.util.List;

public class EventControllerTest{
	private EventController controller;

	@Before
	public void setUp() {
		Event model = new Event();
		controller = new EventController(1);
		controller.setModel(model);
	}
	
	@Test
	public void testGetEstaFromAccount() {
		List<Event> testEvent = controller.getAllEventsForAccount();
		
		
		System.out.println("Test get Establishments:");
		for(Event esta : testEvent) {
			System.out.println(esta.getEventName());
		}
		assertTrue(testEvent.get(0).getEventName().equals("Gregs Tournament"));
	}
	
	@Test
	public void testAddEventFromAccount() {
		//controller.insertNewEvent(  1,  "name", 9, "test type", 3);
		
		List<Event> testEvent = controller.getAllEventsForAccount();
		
		System.out.println("Test Add Establishment:");
		for(Event esta : testEvent) {
			System.out.println(esta.getEventName());
		}
		
		assertTrue(testEvent.get(testEvent.size()-1).getEventName().equals("name"));

		
	}
	
//	@Test
//	public void testRemoveEstaFromAccount() {
//		controller.removeEstablishment(1, "name");
//
//		List<Establishment> testEsta = controller.getAllEstablishmentsForAccount(1);
//		
//		System.out.println("Test Add Establishment:");
//		for(Establishment esta : testEsta) {
//			System.out.println(esta.getEstablishmentName());
//		}
//		
//		assertFalse(testEsta.get(testEsta.size()-1).getEstablishmentName().equals("name"));
//		
//	}

}