package edu.ycp.cs320.RevMetrix.model;

import java.util.List;

public class Shot{
	private int pins, sessionID, gameID, frameID, shotNumber, ballID;
	private String count, pinsLeft;
	
	//Constructor
	public Shot(int sessionID, int gameID, int frameID, int shotNumber, String count, int ballID, String pinsLeft) {
		this.sessionID = sessionID;
		this.gameID = gameID;
		this.frameID = frameID;
		this.shotNumber = shotNumber;
		this.count = count;
		this.ballID = ballID;
		this.pinsLeft = pinsLeft;
	}
	
	public int getPins()
	{
		return this.pins;
	}
	public void setPins(int pins)
	{
		this.pins = pins;
	}
	public int getSessionID()
	{
		return this.sessionID; 
	}
	public void setSessionID(int id)
	{
		this.sessionID = id;
	}
	public int getGameID()
	{
		return this.gameID;
	}
	public void setGameID(int id)
	{
		this.gameID = id;
	}
	public int getFrameID()
	{
		return this.frameID;
	}
	public void setFrameID(int id)
	{
		this.frameID = id;
	}
	public int getShotNumber()
	{
		return this.shotNumber;
	}
	public void setShotNumber(int num)
	{
		this.shotNumber = num;
	}
	public String getCount()
	{
		return this.count;
	}
	public void setCount(String count)
	{
		this.count = count;
	}
	public int getBallID()
	{
		return this.ballID;
	}
	public void setBallID(int id)
	{
		this.ballID = id;
	}
	public String getPinsLeft()
	{
		return this.pinsLeft;
	}
	public void setPinsLeft(String left)
	{
		this.pinsLeft = left;
	}
	
	
}