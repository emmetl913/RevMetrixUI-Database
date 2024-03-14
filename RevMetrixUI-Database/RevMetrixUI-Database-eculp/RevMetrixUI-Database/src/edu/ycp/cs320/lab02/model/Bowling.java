package edu.ycp.cs320.lab02.model;

public class Bowling {
	private int dataState;
	private String estab;
	
	public Bowling()
	{
	}
	// Getter and Setter for page state
	public void setState(String state)
	{
		this.dataState = Integer.parseInt(state);
	}
	public int getState()
	{
		return this.dataState;
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
	
}
