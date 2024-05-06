package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.model.Session;

public class SessionControllerTest {
	private SessionController controller;
	
	@Before 
	public void setUp()
	{
		Session model = new Session();
		controller = new SessionController();
		controller.setModel(model);
		controller.insertNewSession(1, "time", "oppType", "name", 1);
	}
	
	@Test
	public void testGetSessionByEventID()
	{
		List<Session> testSession = controller.getSessionByEventID(1);
		
		System.out.print("Test get Sessions: ");
		for(Session session: testSession)
		{
			System.out.print(session.getSessionID()+ " ");
		}
		assertTrue(testSession.get(0).getSessionID() == 1);
	}
	
	@Test
	public void testInsertNewSession()
	{
		Integer testInt = controller.insertNewSession(10, "", "", "", 10);
	
		System.out.println("Result integer for session: "+testInt);
		
		assertTrue(testInt == 5);
	}
}
