package edu.ycp.cs320.lab02.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.lab02.controller.ShotController;
import edu.ycp.cs320.lab02.model.Shot;

public class ShotControllerTest{
	private ShotController controller;

	@Before
	public void setUp() {
		controller = new ShotController();
	}

	@Test
	public void testCreateShot() {
		Shot shot = controller.createShot("strike", 10);
		assertNotNull(shot);
		assertEquals("strike", shot.getType());
		assertEquals(10, shot.getKnockedOver());
		assertEquals(10, controller.getTotalPins());
	}

	@Test
	public void testGetShots() {
		List<Shot> expected = new ArrayList<Shot>();
		expected.add(controller.createShot("spare", 5));
		expected.add(controller.createShot("strike", 10));
		expected.add(controller.createShot("gutter", 0));

	List<Shot> actual = controller.getShots();
		 assertEquals(expected, actual);
		 assertEquals(15, controller.getTotalPins());
	}

	@Test
	public void testReset() {
		List<Shot> expected = new ArrayList<Shot>();
		expected.add(controller.createShot("strike", 10));
		expected.add(controller.createShot("spare", 5));
		List<Shot> actual = controller.getShots();
		assertEquals(expected, actual);
		controller.reset();
		actual = controller.getShots();
		assertEquals(0, actual.size());
	}

	@Test
	public void testGetTotalPins() {
		controller.createShot("strike", 10);
		controller.createShot("spare", 5);
		int actual = controller.getTotalPins();
		assertEquals(15, actual);
	}

	@Test
	public void testAddShots() {
		Shot shot1 = controller.createShot("strike", 10);
		Shot shot2 = controller.createShot("spare", 5);
		List<Shot> expected = new ArrayList<Shot>();
		expected.add(shot1);
		expected.add(shot2);
		List<Shot> actual = controller.getShots();
		assertEquals(expected, actual);
		assertEquals(15, controller.getTotalPins());
	}
}