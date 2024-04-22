package edu.ycp.cs320.RevMetrix.controller;

import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.model.Session;

public class SessionController {
	private Session model;
	private IDatabase db = null;
	
	public SessionController()
	{
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	public void setModel(Session model)
	{
		this.model = model;
	}
	public List<Session> getSessionByEventID(int sessionID, int eventID, String time, String oppType, String oppName, int score)
	{
		List<Session> sessionList = db.getSessionByEventID(eventID);
		if(sessionList.isEmpty())
		{
			System.out.println("No session stupid");
			return null;
		}
		else 
		{
			return sessionList;
		}
	}
	
	public boolean insertSessionInDB(int sessionID, int eventID, String time, String oppType, String oppName, int score)
	{
		Integer session_id = db.insertNewSession(sessionID, eventID, time, oppType, oppName, score);
		
		if (session_id > 0)
		{
			System.out.println("New session (Session ID: " + session_id + ", Event ID: "+ eventID +" successfully added to db");
			return true;
		} 
		else
		{
			System.out.println("New session (Session ID: " + session_id + ", Event ID: "+ eventID +" failed to add to db");
			return false;
		}
	}
}
