package edu.ycp.cs320.RevMetrix.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class FrameTest{
	private Frame num;
	
	@Before
	public void setUp() {
		num = new Frame(1, 1, 1);
	}
	
	@Test
	public void testGetFrame() {
//		num = new Frame(1, 1, 1);
		assertEquals(1, num.getFrame());
	}
	
	@Test
	public void testSetFrame() {
		num.setFrame(8);
		assertEquals(8, num.getFrame());
	}
	
	@Test
	public void testGetLane() {
		num.setLaneNumber(4);
		assertEquals(4, num.getLaneNumber());
	}
	
	@Test
	public void testGame() {
		num.setGameID(3);
		assertEquals(3, num.getGameID());
	}
	
//	@Test
//	public void setAddShot() {
//		Shot shot = new Shot(1, 1, 1, 1, "0", 1, "2");
//		num.addShot(shot);
//		assertEquals(shot, num.getShot(0));
//	}
	
	@Test
	public void testLaneNumber() {
		num.setLaneNumber(7);
		assertEquals(7, num.getLaneNumber());
	}
}