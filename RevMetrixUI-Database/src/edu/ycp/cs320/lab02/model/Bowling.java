package edu.ycp.cs320.lab02.model;

public class Bowling {
	private int frame;
	private String event, session, game;
	
	public Bowling()
	{
	}
	// Getter and Setter for establishment name
	public void setEvent(String ev)
	{
		this.event = ev;
	}
	public String getEvent()
	{
		return this.event;
	}
	// Getter and Setter for Session
	public void setSession(String session)
	{
		this.session = session;
	}
	public String getSession()
	{
		return this.session;
	}
	// Getter and Setter for Game
	public void setGame(String game)
	{
		this.game = game;
	}
	public String getGame()
	{
		return this.game;
	}
	// Getter and Setter for Frame
	public void setFrame(String frame)
	{
		this.frame = Integer.parseInt(frame);
	}
	public int getFrame()
	{
		return this.frame;
	}
}
