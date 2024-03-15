package edu.ycp.cs320.lab02.controller;

import edu.ycp.cs320.lab02.model.Bowling;

public class BowlingController {
	private Bowling model;
	
	public void setModel(Bowling model)
	{
		this.model = model;
	}
	
	public void setInfo(String est, String session, String game, String frame)
	{
		model.setEstab(est);
		model.setSession(session);
		model.setGame(game);
		model.setFrame(frame);
		
	}
}
