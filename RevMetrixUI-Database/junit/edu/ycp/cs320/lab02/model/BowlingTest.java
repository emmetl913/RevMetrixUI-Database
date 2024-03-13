package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BowlingTest {
	private Bowling model;
	
	@Before
	public void setUp() {
		model = new Bowling();
	}
	
	@Test
	public void testsetEvent() {
		model.setEvent("john's bowling");
		assertEquals("john's bowling", model.getEvent());
	}
	@Test
	public void testsetSession() {
		model.setSession("practice");
		assertEquals("practice", model.getSession());
	}
	@Test
	public void testsetGame() {
		model.setGame("continue");
		assertEquals("continue", model.getGame());
	}
	@Test
	public void testsetFrame() {
		model.setFrame("9");
		assertEquals(9, model.getFrame());
	}
}
