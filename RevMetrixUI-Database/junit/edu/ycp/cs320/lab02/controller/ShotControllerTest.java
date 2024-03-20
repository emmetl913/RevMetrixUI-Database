package edu.ycp.cs320.lab02.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02.controller.ShotController;
import edu.ycp.cs320.lab02.model.Shot;

public class ShotControllerTest{
	private Shot shot;
	private ShotController controller;
	
	@Before
	public void setUp() {
		shot = new Shot(3);
		controller = new ShotController();
		controller.setModel(shot);
	}
	
	@Test
	public void testKnockedOver() {
		shot.setKnockedOver(8);
		assertEquals(2, controller.KnockedOver(shot));
	}
	
	@Test
	public void testTypes() {
		shot.setType("F");
		assertEquals(0, controller.Types());
	}
}