package edu.ycp.cs320.RevMetrix.controller;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.*;

public class BallArsenalController {
	private BallArsenal arsenal;
	
	private IDatabase db = null;
	
	public BallArsenalController(){
		//create db instance
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	public void setModel(BallArsenal model) {
		this.arsenal = model;
	}
	public void setBalls(ArrayList<Ball> newBalls) {
		arsenal.setBalls(newBalls);
	}
	//adds a ball to the arsenal with the name and color
	public void addBall(String name) {
		Ball ball = new Ball(0, 0, name, false, name, name, "color2", "color3", "material");
		arsenal.addBall(ball);
	}
	public void changeBallNameAtIndex(int index, String newName){
		arsenal.getBallAtIndex(index).setName(newName);
	}
	
	public List<Ball> getBallByAccountId(int accountId){
		List<Ball> ballList = db.getBallsByAccountID(accountId);
		if(ballList.isEmpty()) {
			System.out.println("Aint no balls in your database bro for acc id: "+accountId);
			return null;
		}
		else {
			return ballList;
		}
	}
	public List<Ball> getBallByBallId(int ballID){
		List<Ball> ballList = db.getBallByBallID(ballID);
		if(ballList.isEmpty()) {
			System.out.println("Aint no balls in your database bro for acc id: "+ballID);
			return null;
		}
		else {
			return ballList;
		}
	}
	public Boolean removeBall(int accID, String name) {
		if(db.removeBall(accID, name) == 1)
			return true;
		else
			return false;
	}
	public List<Ball> getBallByName(String ballName){
		List<Ball> ballList = db.getBallByName(ballName);
		if(ballList.isEmpty()) {
			System.out.println("Aint no balls in your account bro with name: "+ballName);
			return null;
		}
		else {
			return ballList;
		}
	}
	
	public boolean insertBallinDB(int account_id, float weight, String name, Boolean righthand, String brand, String color, String color2, String color3, String material) {
		
		// insert new book (and possibly new author) into DB
		Integer ball_id = db.insertNewBallInDB(account_id, weight, name, righthand, brand, color, color2, color3, material);
		//check if the insertion succeeded
		if (account_id > 0){
			System.out.println("New ball (Ball ID: " + ball_id + ", Ball Name: "+name+") successfully added to balls table");
			return true;
		}
		else{
			System.out.println("Failed to insert (Ball ID: " + ball_id + ", Ball Name: "+name+") to balls table");
			return false;
		}
	}
	
	//finds the ball that is to be removed, and removes it from the list
//	public void removeBall(String name) {
//		for(Ball ball : arsenal.getBalls()) {
//			if(ball.getName().equals(name)) {
//				arsenal.removeBall(ball);
//			}
//		}
//	}
	
	
}
