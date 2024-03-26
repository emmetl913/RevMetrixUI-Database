package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;

public class SessionArray {
	private ArrayList<Session> session;
	
	public SessionArray()
	{
		session = new ArrayList<>();
	}
	
	public void addSession(Session session)
	{
		this.session.add(session);
	}
	public void removeSession(Session session)
	{
		this.session.remove(session);
	}
	public Session getSessionAtIndex(int index)
	{
		return this.session.get(index);
	}
	public int getNumberOfSessions()
	{
		return this.session.size();
	}
}
