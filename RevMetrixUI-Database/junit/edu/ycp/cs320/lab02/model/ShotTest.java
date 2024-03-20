package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ShotTest{
	private Shot shot;
	
	@Before
	public void SetUp() {
		shot = new Shot(0);
	}
	
	@Test
	public void testGetKnockedOver() {
		shot = new Shot(4);
		
		assertEquals(4, shot.getKnockedOver());
	}
	
	@Test
	public void setKnockedOver() {
		shot.setKnockedOver(7);
		
		assertEquals(7, shot.getKnockedOver());
	}
	
	@Test
	public void testGetType() {
		shot.setType("X");
		assertEquals("X", shot.getType());
		
		shot.setType("F");
		assertEquals("F", shot.getType());
		
		shot.setType("-");
		assertEquals("-", shot.getType());
		
		shot.setType("/");
		assertEquals("/", shot.getType());
	}
}