package edu.ycp.cs320.RevMetrix.controller;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;

public class statController {
	
	private IDatabase db = null;
	private int acc;
	
	public statController(int acc){
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();	
		
		this.acc = acc;
	}
	
	public String getSessionDate(int sessionID) {
		String date = "Nothing";
		//This is getting time not date at the moment!!!!
		date = db.getSessionDate(sessionID);
		return date;
	}
	
	public String getEventName(int eventID) {
		String League = "";
		League = db.getEventNameByAccount(acc, eventID);
		return League;
	}
	
	public String getCurrentGameLane(int gameID) {
		String lanes = "";
		int firstLane = db.getCurrentGameLane(gameID);
		
		lanes = "" + firstLane+ " - " + (firstLane+1);
		
		return lanes;
	}
	
	public Integer[] getGameStatsBySession(int sessionID) {
		Integer gameScore[] = new Integer[3];
		gameScore = db.getGamesBySessions(sessionID);
		
		return gameScore;
	}
	
	public ArrayList<Integer> getSessionsByEvent(int eventID) {
		ArrayList<Integer> sessionIDs = new ArrayList<Integer>();
		sessionIDs = db.getSessionsByEvent(eventID);
		return sessionIDs;
	}
	
	public Integer getSessionScore(int sessionID) {
		Integer sessionScore = db.getSessionScore(sessionID);
		return sessionScore;
	}
	
}