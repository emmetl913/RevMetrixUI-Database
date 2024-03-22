package edu.ycp.cs320.RevMetrix.model.startBowling;

public class Session {
	private String date, time;
	private boolean team; //True if playing against a team, false if individual, null if playing alone
	private int totalScore;
	
	// Setters
	public void setDate(String date)
	{
		this.date = date;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public void setTeam(boolean team)
	{
		this.team = team;
	}
	public void setScore(int score)
	{
		this.totalScore = score;
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
	public boolean getTeam()
	{
		return this.team;
	}
	public int getScore()
	{
		return this.totalScore;
	}
}
