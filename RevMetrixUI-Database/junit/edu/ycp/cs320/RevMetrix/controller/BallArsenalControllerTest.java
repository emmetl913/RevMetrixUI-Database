package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.controller.BallArsenalController;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;

public class BallArsenalControllerTest {
	private BallArsenal model;
	private BallArsenalController controller = new BallArsenalController();
	List<Ball> ballList = new ArrayList<Ball>();
	Ball b = new Ball(1,(float)1, "Basalt",true, "GregBall","Blue");

	
	@Before
	public void setUp() {
		//Kevin has the account of id 1
		//He has two balls in bwoodward's ballArsenal CSV
		//controller.insertBallinDB(1, 123, "BranBall", true, "Richards", "Green");
		//ballList.add(b);
		//System.out.println(ballList.get(0));


	}
	@Test public void testInsertBall() {
		controller.insertBallinDB(2, 1, "BranBall", true, "brand", "color");
		ballList = controller.getBallByAccountId(2);
		//System.out.println(ballList.get(0).getName());
		assertTrue(ballList.get(0).getName().equals("BranBall"));
	}
	@Test
	public void testGetBallByAccountID() {
		ballList = controller.getBallByAccountId(1); 
		assertTrue(ballList.get(0).getName().equals("8lbPurpleMary"));
	}
	@Test
	public void testGetBallByName() {
		ballList = controller.getBallByName("8lbPurpleMary");
		assertTrue(ballList.get(0).getName().equals("8lbPurpleMary"));
	}
	
	
	
}
