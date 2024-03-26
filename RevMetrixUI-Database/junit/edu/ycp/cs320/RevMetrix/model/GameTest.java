package edu.ycp.cs320.RevMetrix.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game model;
	
	@Before
	public void setUp() {
		model = new Game(0,0);
	}
	
	@Test
	public void testSetLane() {
		model.setLane(1000);
		assertTrue(model.getLane() == 1000);
	}
	@Test
	public void testSetGameNumber() {
		model.setGameNumber(1001);
		assertTrue(model.getGameNumber() == 1001);
	}
	@Test
	public void testSetScore() {
		model.setScore(100);
		assertTrue(model.getScore() == 100);
	}
	@Test
	public void testSetFrameCount() {
		model.setFrameCount(4);
		assertTrue(model.getFrameCount() == 4);
	}
	
}
