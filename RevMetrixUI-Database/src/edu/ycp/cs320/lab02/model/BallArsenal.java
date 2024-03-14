package edu.ycp.cs320.lab02.model;
import java.util.ArrayList;


public class BallArsenal {
	//Represents individual bowling balls
	public class Ball{
		private String name;
		private String color;
		
		public Ball(String name, String color) {
			this.name = name;
			this.color = color;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getColor() {
			return color;
		}
		
		public void setColor(String color) {
			this.color = color;
		}
	}
	
	//Manages a collection of bowling balls
	public class Arsenal{
		private ArrayList<Ball> balls;
		
		public Arsenal() {
			balls = new ArrayList<>();
		}
		
		public void addBall(Ball ball) {
			balls.add(ball);
		}
		
		public void removeBall(Ball ball) {
			balls.remove(ball);
		}
		
		public ArrayList<Ball> getBalls(){
			return balls;
		}
	}
}