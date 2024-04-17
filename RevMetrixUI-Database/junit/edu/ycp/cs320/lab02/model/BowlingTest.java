package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BowlingTest {
	private Bowling model;
	
	@Before
	public void setUp() {
		model = new Bowling();
		
		model.setEstab("John's Bowling Bash");
		model.setFrame("19");
		model.setGame("Start a new game");
		model.setSession("Practice");
	}
	
	
	@Test
	public void testEstab()
	{
		assertTrue(model.getEstab() == "John's Bowling Bash");
	}
	@Test
	public void testFrame()
	{
		assertTrue(model.getFrame() == 19);
	}
	@Test
	public void testGame()
	{
		assertTrue(model.getGame() == "Start a new game");
	}
	@Test
	public void testSession()
	{
		assertTrue(model.getSession() == "Practice");
	}
	
}
