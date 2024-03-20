package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.Account;
import edu.ycp.cs320.lab02.model.BallArsenal;
import edu.ycp.cs320.lab02.model.Ball;


public class BallArsenalController {
	private BallArsenal arsenal;
	
	public void setModel(BallArsenal model) {
		this.arsenal = model;
	}
	//adds a ball to the arsenal with the name and color
	public void addBall(String name) {
		Ball ball = new Ball(name);
		arsenal.addBall(ball);
	}
	public void changeBallNameAtIndex(int index, String newName){
		arsenal.getBallAtIndex(index).setName(newName);
	}
	//finds the ball that is to be removed, and removes it from the list
	public void removeBall(String name) {
		for(Ball ball : arsenal.getBalls()) {
			if(ball.getName().equals(name)) {
				arsenal.removeBall(ball);
			}
		}
	}
	
	
}
