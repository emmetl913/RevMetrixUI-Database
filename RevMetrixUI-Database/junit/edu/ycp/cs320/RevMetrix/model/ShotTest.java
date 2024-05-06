package edu.ycp.cs320.RevMetrix.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ShotTest{
	private Shot shot;
	
	@Before
	public void SetUp() {
		shot = new Shot(0, 1, 1, 1, 1, 1, "", "");
	}
	
	@Test
	public void testPins() {
		shot.setPins(7);
		
		assertEquals(7, shot.getPins());
	}
	
	@Test
	public void testBallID() {
		shot.setBallID(0);
		
		assertEquals(0, shot.getBallID());
	}
	
	@Test
	public void testSetSessionID() {
		shot.setSessionID(4);
		
		assertEquals(4, shot.getSessionID());
	}
	
	@Test
	public void testSetGameID() {
		shot.setGameID(3);
		
		assertEquals(3, shot.getGameID());
	}
	
	@Test
	public void testSetFrameID() {
		shot.setFrameID(7);
		
		assertEquals(7, shot.getFrameID());
	}
	
	@Test
	public void testShotNumber() {
		shot.setShotNumber(1);
		assertEquals(1, shot.getShotNumber());
	}
	
	@Test
	public void testCount() {
		shot.setCount(2);
		assertEquals(2, shot.getCount());
	}
	
	@Test
	public void testPinsLeft() {
		shot.setPinsLeft("9");
		assertEquals("9", shot.getPinsLeft());
	}
	
//	@Test
//	public void testShotType() {
//		shot.setType("X");
//		assertEquals("X", shot.getType());
//	}
}