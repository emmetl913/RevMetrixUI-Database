package edu.ycp.cs320.lab02.model;
import java.util.ArrayList;



public class BallArsenal {

	//Manages a collection of bowling balls
		private ArrayList<Ball> balls;
		
		public BallArsenal() {
			balls = new ArrayList<>();
			
			Ball b1 = new Ball("ball1");
			Ball b2 = new Ball("ball2");
			Ball b3 = new Ball("ball3");
			balls.add(b1);
			balls.add(b2);
			balls.add(b3);

		}
		public Ball getBallFirst() {
			return balls.get(0);
		}
		
		public void addBall(Ball ball) {
			balls.add(ball);
		}
		
		public void removeBall(Ball ball) {
			balls.remove(ball);
		}
		public Ball getBallAtIndex(int index) {
			return balls.get(index);
		}
		public ArrayList<Ball> getBalls(){
			return balls;
		}
		public int getNumberOfBalls() {
			return balls.size();
		}
	
}