package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.controller.BallArsenalController;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;

public class BallArsenalControllerTest {
	private BallArsenal model;
	private BallArsenalController controller;
	
	List<Ball> ballList = null;
	
	@Before
	public void setUp() {
		//Kevin has the account of id 1
		//He has two balls in bwoodward's ballArsenal CSV
		
		ballList = controller.getBallByAccountId(1); 
		//System.out.println(ballList.get(0));


	}
	
	@Test
	public void testGetBallByAccountID() {
		assertTrue(ballList.get(0).getName().equals("8lbPurpleMary"));
	}
	
	
}
