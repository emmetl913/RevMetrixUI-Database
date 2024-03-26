package edu.ycp.cs320.RevMetrix.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.model.Session;

public class SessionTest {
	private Session model;
	
	@Before
	public void setUp()
	{
		model = new Session();
		
		// Setting up values for getters
		model.setTime("9:00pm");
		model.setName("Speed Lanez");
		model.setOpp("Zach Cox");
		model.setScore(298);
	}
	
	
	// Generic Method Tests
	@Test
	public void testTime()
	{
		assertTrue(model.getTime() == "9:00pm");
	}
	
	@Test
	public void testName()
	{
		assertTrue(model.getName() == "Speed Lanez");
	}
	
	@Test
	public void testOpp()
	{
		assertTrue(model.getOpponent() == "Zach Cox");
	}
	
	@Test
	public void testScore()
	{
		assertTrue(model.getScore() == 298);
	}

}
