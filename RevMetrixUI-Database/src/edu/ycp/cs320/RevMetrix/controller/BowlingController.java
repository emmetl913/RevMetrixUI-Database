package edu.ycp.cs320.RevMetrix.controller;

import edu.ycp.cs320.RevMetrix.model.Bowling;

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
