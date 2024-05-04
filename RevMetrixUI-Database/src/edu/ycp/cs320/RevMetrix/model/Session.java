package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;

public class Session {
	private int sessionID, eventID;
	private String time, eName, date;
	private String opp, oppType;
	private int score;
	private ArrayList<Game> games;
	
	
	public Session(int sessionId, int eventID, String time, String date, String oppType, String opp, int score)
	{
		this.sessionID = sessionId;
		this.eventID = eventID;
		this.time = time;
		this.date = date;
		this.oppType = oppType;
		this.opp = opp;
		this.score = score;
	}
	public Session() {
		// TODO Auto-generated constructor stub
	}
	// Getters
	public String getDate()
	{
		return this.date;
	}
	public String getTime()
	{
		return this.time;
	}
	public String getName()
	{
		return this.eName;
	}
	public String getOpponent()
	{
		return this.opp;
	}
	public String getOppType()
	{
		return this.oppType;
	}
	public int getScore()
	{
		return this.score;
	}
	public int getSessionID()
	{
		return this.sessionID;
	}
	public int getEventID()
	{
		return this.eventID;
	}
	
	public Game getSpecificGame(int gameNum)
	{
		return this.games.get(gameNum);
	}
	public ArrayList<Game> getGameList()
	{
		return this.games;
	}
	
	
	// Setters
	public void setDate(String date)
	{
		this.date = date;
	}
	public void setTime(String time1)
	{
		this.time = time1;
	}
	public void setName(String name)
	{
		this.eName = name;
	}
	public void setOpp(String opp)
	{
		this.opp = opp;
	}
	public void setOppType(String oppType)
	{
		this.oppType = oppType;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
	public void setSessionID(int sessionID)
	{
		this.sessionID = sessionID;
	}
	public void setEventID(int eventID)
	{
		this.eventID = eventID;
	}
	
	
	public void addGame(Game game)
	{
		this.games.add(game);
	}
}
