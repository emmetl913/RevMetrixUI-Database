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
	//Ball b = new Ball(1,(float)1, "Basalt",true, "GregBall","Blue");

	
	@Before
	public void setUp() {
		//Kevin has the account of id 1
		//He has two balls in bwoodward's ballArsenal CSV
		//controller.insertBallinDB(1, 123, "BranBall", true, "Richards", "Green");
		//ballList.add(b);
		//System.out.println(ballList.get(0));


	}
	@Test public void testInsertBall() {
		controller.insertBallinDB(2, 1, "BranBall", true, "brand", "color", "color1","color2", "material");
		ballList = controller.getBallByAccountId(2);
		//System.out.println(ballList.get(0).getName());
		assertTrue(ballList.get(0).getName().equals("BranBall"));
	}
	@Test
	public void testGetBallByAccountID() {
		ballList = controller.getBallByAccountId(2); 
		assertTrue(ballList.get(0).getName().equals("BranBall"));
	}
	@Test
	public void testGetBallByName() {
		if(controller.getBallByName("8lbPurpleMary") == null) {
			controller.insertBallinDB(1, 1, "8lbPurpleMary", true, "brand", "color","color","color","material");
		}
		ballList = controller.getBallByName("8lbPurpleMary");
		assertTrue(ballList.get(0).getName().equals("8lbPurpleMary"));
	}
	@Test
	public void testDeleteBall() {
		controller.insertBallinDB(1, 1, "8lbPurpleMary", true, "brand", "color", "color","color","material");
		System.out.println("inserted ball into database");
		int ballExists = 0;
		Boolean ballDeleted = false;
		ballList = controller.getBallByName("8lbPurpleMary"); 

		if(ballList.get(0).getName().equals("8lbPurpleMary") && ballList != null) {
			ballExists++;
			controller.removeBall(1, "8lbPurpleMary");
			System.out.println("ball deleted");

		}
	}
	@Test
	public void testGetBallByBallID() {
	//	if(controller.getBallByBallId(1) == null) {
		//	controller.insertBallinDB(1, 1, "PurpleMary", true, "brand", "color","color","color","material");
		//}
		ballList = controller.getBallByBallId(1);
		assertTrue(ballList.get(0).getName().equals("MagentaMary"));
	}
	
}
