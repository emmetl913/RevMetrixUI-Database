package edu.ycp.cs320.RevMetrix.model;

public class Bowling {
	private int dataState, frame;
	private String estab, session, game;
	
	public Bowling()
	{
	}
	// Getter and Setter for establishment name
	public void setEstab(String est)
	{
		this.estab = est;
	}
	public String getEstab()
	{
		return this.estab;
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
