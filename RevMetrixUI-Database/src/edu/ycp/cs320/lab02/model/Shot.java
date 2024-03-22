package edu.ycp.cs320.lab02.model;

public class Shot{
	private int pins;
	private String type;
	
	//Constructor
	public Shot(int pins) {
		this.pins = pins;
	}
	
	//gets the numbers of pins that were knocked over from the user input
	public int getKnockedOver() {
		return pins;
	}
	
	//sets the pins knocked over from the user
	public void setKnockedOver(int pins) {
		this.pins = pins;
	}
	
	//get the type of score (F, -, X, /)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}	
}