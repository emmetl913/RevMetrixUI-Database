package edu.ycp.cs320.RevMetrix.controller;

import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.model.Game;
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
	public Session getSessionByGameID(int gameID)
	{
		return db.getSessionByGameID(gameID);
	}
	public List<Game> getGamesForSession(int sessionID)
	{
		return db.getGamesForSession(sessionID);
	}
	public void updateSessionBySessionID(int sessionID, int newScore)
	{
		Boolean boggas = db.updateSessionBySessionID(sessionID, newScore);
		System.out.println("Boggas: "+ boggas);
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
	public Integer insertNewSession(int eventID, String time, String oppType, String oppName, int score)
	{
		Integer newSession = db.insertNewSession(eventID, time,  oppType, oppName, score);
		
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
