package edu.ycp.cs320.lab02.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02.model.Bowling;

public class BowlingControllerTest {
	private Bowling model;
	private BowlingController controller;
	
	@Before
	public void setUp() {
		model = new Bowling();
		controller = new BowlingController();
		
		controller.setModel(model);
		controller.setInfo("John's Bowling", "Leauge", "Continue", "15");
	}
	
	@Test
	public void testEstab()
	{
		assertTrue(model.getEstab() == "John's Bowling");
	}
	@Test
	public void testFrame()
	{
		assertTrue(model.getFrame() == 15);
	}
	@Test
	public void testGame()
	{
		assertTrue(model.getGame() == "Continue");
	}
	@Test
	public void testSession()
	{
		assertTrue(model.getSession() == "Leauge");
	}
	
}
