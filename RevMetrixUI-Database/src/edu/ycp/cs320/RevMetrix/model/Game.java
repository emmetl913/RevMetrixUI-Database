package edu.ycp.cs320.RevMetrix.model;

public class Game {
	private int lane;
	private int gameNumber;
	private int score;
	private int frameCount;
	
	public Game(int newGameNumber, int newLane) {
		this.lane = newLane;
		this.gameNumber = newGameNumber;
		//set game number by referencing session
		//score can be initialized if shot[0] != null
		score = 10;
		//When game is initialized should have only the first frame
		frameCount = 1; //frame count should increase on submitting the shot page
	}
	
	public int getLane() {
		return lane;
	}
	public void setLane(int newLane) {
		this.lane = newLane;
	}
	public void setGameNumber(int newGameNumber) {
		this.gameNumber = newGameNumber;
	}
	public int getGameNumber() {
		return gameNumber;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int newScore) {
		this.score = newScore;
	}
	public int getFrameCount() {
		return frameCount;
	}
	public void setFrameCount(int newFrameCount) {
		this.frameCount = newFrameCount;
	}
	
}
