package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02.model.Numbers;

public class NumbersTest {
	private Numbers model;
	
	@Before
	public void setUp() {
		model = new Numbers("","","");
	}
	
	
	@Test
	public void testFirst() {
		model.setFirst(1.0);
		assertTrue(model.getFirst() == 1.0);
	}
	
	@Test
	public void testSecond() {
		model.setSecond(2.0);
		assertTrue(model.getSecond() == 2.0);
	}
	
	@Test
	public void testThird() {
		model.setThird(3.0);
		assertTrue(model.getThird() == 3.0);
	}
}
