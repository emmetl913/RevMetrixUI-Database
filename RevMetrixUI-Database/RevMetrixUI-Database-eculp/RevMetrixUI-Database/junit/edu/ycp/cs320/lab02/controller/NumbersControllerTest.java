package edu.ycp.cs320.lab02.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02.controller.NumbersController;
import edu.ycp.cs320.lab02.model.Numbers;

public class NumbersControllerTest {
	private Numbers model;
	private NumbersController controller;
	
	@Before
	public void setUp() {
		model = new Numbers("","");
		controller = new NumbersController();
		
		model.setFirst(2.0);
		model.setSecond(3.0);
		model.setThird(4.0);
		
		controller.setModel(model);
	}
	
	
	@Test
	public void testAdd() {
		double add = model.getFirst() + model.getSecond()+model.getThird();
		assertTrue(controller.add() ==  add);
	}
	
	@Test
	public void testmult() {
		double mult = model.getFirst()*model.getSecond();
		assertTrue(controller.mult() == mult);
	}
}
