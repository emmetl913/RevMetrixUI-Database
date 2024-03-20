package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class FrameTest{
	private Frame num;
	
	@Before
	public void setUp() {
		num = new Frame(0);
	}
	
	@Test
	public void testGetFrame() {
		num = new Frame(4);
		assertEquals(4, num.getFrame());
	}
	
	@Test
	public void testSetFrame() {
		num.setFrame(8);
		assertEquals(8, num.getFrame());
	}
}