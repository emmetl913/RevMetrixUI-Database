package edu.ycp.cs320.lab02.model;

import java.util.List;

public class Shot{
	private int pins;
	private String type;
	private String ballName;
	
	//Constructor
	public Shot() {
		this.pins = 0;
		this.type = "";
		this.ballName = "";
	}
	
	public Shot(String type, int pins) {
		this.type = type;
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
	
	public String getBallName() {
		return ballName;
	}
	
	public void setBallName(String ballName) {
		this.ballName = ballName;
	}
}