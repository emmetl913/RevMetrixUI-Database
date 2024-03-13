package edu.ycp.cs320.lab02.controller;

import static org.junit.Assert.assertEquals;
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
		controller.setInfo("john's bowling", "leauge", "start new game", "6");
	}
	
	
	@Test
	public void testsetEvent() {
		assertEquals("john's bowling", model.getEvent());
	}
	@Test
	public void testsetSession() {
		assertEquals("leauge", model.getSession());
	}
	@Test
	public void testsetGame() {
		assertEquals("start new game", model.getGame());
	}
	@Test
	public void testsetFrame() {
		assertEquals(6, model.getFrame());
	}
}
