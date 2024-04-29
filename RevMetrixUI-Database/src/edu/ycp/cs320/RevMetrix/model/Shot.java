package edu.ycp.cs320.RevMetrix.model;

import java.util.List;

public class Shot{
	private int pins, sessionID, gameID, frameID, shotNumber, ballID, count;
	private String leave, pinsLeft;
	private int shotID;
	
	//Constructor
	public Shot(int sessionID, int gameID, int frameID, int shotNumber, int count, int ballID, String pinsLeft, String leave) {
		this.sessionID = sessionID;
		this.gameID = gameID;
		this.frameID = frameID;
		this.shotNumber = shotNumber;
		this.count = count;
		this.ballID = ballID;
		this.pinsLeft = pinsLeft;
		this.leave = leave;
	}
	
	public Shot() {
		// TODO Auto-generated constructor stub
	}

	//pins
	public int getPins()
	{
		return this.pins;
	}
	public void setPins(int pins)
	{
		this.pins = pins;
	}
	//session
	public int getSessionID()
	{
		return this.sessionID; 
	}
	public void setSessionID(int id)
	{
		this.sessionID = id;
	}
	//game
	public int getGameID()
	{
		return this.gameID;
	}
	public void setGameID(int id)
	{
		this.gameID = id;
	}
	//frame
	public int getFrameID()
	{
		return this.frameID;
	}
	public void setFrameID(int id)
	{
		this.frameID = id;
	}
	//shot
	public int getShotNumber()
	{
		return this.shotNumber;
	}
	public void setShotNumber(int num)
	{
		this.shotNumber = num;
	}
	public int getCount()
	{
		return this.count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	//ball
	public int getBallID()
	{
		return this.ballID;
	}
	public void setBallID(int id)
	{
		this.ballID = id;
	}
	//pins left standing
	public String getPinsLeft()
	{
		return this.pinsLeft;
	}
	public void setPinsLeft(String left)
	{
		this.pinsLeft = left;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public int getShotID() {
		return shotID;
	}

	public void setShotID(int shotID) {
		this.shotID = shotID;
	}
}