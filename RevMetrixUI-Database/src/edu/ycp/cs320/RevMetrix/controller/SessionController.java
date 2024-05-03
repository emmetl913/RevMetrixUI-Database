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
	
	public List<Session> getSessionByEventID(int eventID)
	{
		List<Session> resultList = db.getSessionByEventID(eventID);
		
		if(resultList.isEmpty())
		{
			System.out.println("Sessions for <"+eventID+"> not found");
			return null;
		} else 
		{
			return resultList;
		}
	}
	public Integer insertNewSession(int eventID, String time, String date, String oppType, String oppName, int score)
	{
		Integer newSession = db.insertNewSession(eventID, time, date, oppType, oppName, score);
		
		if (newSession == null)
		{
			System.out.println("Session for <"+eventID+"> insert failed");
			return null;
		} else
		{
			System.out.println("Session for event ID: "+eventID+"  and for session ID: "+newSession+"  has succeded");
			return newSession;
		}
	}
}
