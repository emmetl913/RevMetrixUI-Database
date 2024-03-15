package edu.ycp.cs320.lab02.model;

public class Bowling {
	private int dataState, frame, game;
	private String estab, session;
	
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
		this.game = Integer.parseInt(game);
	}
	public int getGame()
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
