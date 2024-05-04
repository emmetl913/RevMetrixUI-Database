package edu.ycp.cs320.RevMetrix.model;

public class Game {
	private int lane, gameNumber, score, gameID, sessionID;

	
	public Game(int gameID, int sessionID, int currentLane, int gameNumber, int score) {
		this.gameID = gameID;
		this.sessionID = sessionID;
		this.lane = currentLane;
		this.gameNumber = gameNumber;
		this.score = score;
	}
	
	public Game() {
		// TODO Auto-generated constructor stub
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
	
	public int getGameID() {
		return this.gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	public int getSessionID()
	{
		return this.sessionID;
	}
	public void setSessionID(int sessionID)
	{
		this.sessionID = sessionID;
	}
}
