package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.BallArsenal;

public class BallArsenalController {
	private BallArsenal arsenal;
	
	public BallArsenalController() {
		this.arsenal = new BallArsenal();
	}
	
	//adds a ball to the arsenal with the name and color
	public void addBall(String name, String color) {
		BallArsenal.Ball ball = arsenal.new Ball(name, color);
		arsenal.new Arsenal().addBall(ball);
	}
	
	//finds the ball that is to be removed, and removes it from the list
	public void removeBall(String name) {
		BallArsenal.Arsenal arsenalInstance = arsenal.new Arsenal();
		for(BallArsenal.Ball ball : arsenalInstance.getBalls()) {
			if(ball.getName().equals(name)) {
				arsenalInstance.removeBall(ball);
			}
		}
	}
	
	//returns the number of balls within the arsenal
	public int getNumberOfBalls() {
		return arsenal.new Arsenal().getBalls().size();
	}
}
