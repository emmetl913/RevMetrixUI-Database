package edu.ycp.cs320.lab02.model;
import java.util.ArrayList;


public class BallArsenal {
	public class Ball{
		private String color;
		private double size;
		
		public Ball(String color, double size) {
			this.color = color;
			this.size = size;
		}
		
		public String getColor() {
			return color;
		}
		
		public void setColor(String color) {
			this.color = color;
		}
		
		public double getSize() {
			return size;
		}
		
		public void setSize(double size) {
			this.size = size;
		}
	}
	
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