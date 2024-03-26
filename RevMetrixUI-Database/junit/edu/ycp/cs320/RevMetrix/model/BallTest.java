package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BallTest {
	private Ball model;
	
	@Before
	public void setUp() {
		model = new Ball("ball!");
	}
	
	@Test
	public void testSetBallName() {
		model.setName("ball2!");
		assertTrue(model.getName().equals("ball2!"));
	}
}
