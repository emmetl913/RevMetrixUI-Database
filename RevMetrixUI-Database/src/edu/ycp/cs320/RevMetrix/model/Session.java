package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;

public class Session {
	private String time, eName;
	private String opp;
	private int score;
	private ArrayList<Game> games;
	
	// Getters
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
	public int getScore()
	{
		return this.score;
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
	public void setScore(int score)
	{
		this.score = score;
	}
	public void addGame(Game game)
	{
		this.games.add(game);
	}
}
