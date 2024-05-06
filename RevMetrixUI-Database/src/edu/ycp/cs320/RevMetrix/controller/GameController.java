package edu.ycp.cs320.RevMetrix.controller;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Shot;

import java.util.ArrayList;
import java.util.List;

public class GameController{
	
	private Game model;
	private IDatabase db = null;
	
	public GameController ()
	{
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
	public void setModel(Game model)
	{
		this.model = model;
	}
	public Integer insertNewGame(int sessionID, int currentLane, int gameNum, int score)
	{
		Integer newGame = db.insertNewGame(sessionID, currentLane, gameNum, score);
		
		if(newGame == null)
		{
			System.out.println("Game for sessionID: <"+sessionID+"> failed to insert");
			return null;
		} else
		{
			return newGame;
		}
	}
	
	public Game getGameByGameID(int gameID)
	{
		Game result = db.getGameByGameID(gameID);
		if(result == null)
		{
			System.out.println("Games for gameID: <"+gameID+"> not found");
			return null;
		} else 
		{
			System.out.println("Games for gameID: <"+gameID+"> found successfully");
			return result;
		}
	}
	public void updateGameByGameID(int gameID, int newScore)
	{
		Integer boggas = db.updateGameByGameID(gameID, newScore);
		System.out.println("Boggas: "+ boggas);
	}
	public List<Game> getGameBySessionID(int sessionID)
	{
		List<Game> resultList = db.getGameBySessionID(sessionID);
		
		if(resultList == null)
		{
			System.out.println("Games for sessionID: <"+sessionID+"> not found");
			return null;
		} else 
		{
			System.out.println("Games for sessionID: <"+sessionID+"> found successfully");
			return resultList;
		}
	}
	//gets the shots per frame
	public void updateFormattedShots(HttpSession session, ArrayList<Frame> frames) {
		//StringBuilder = modify contents of the string without creating a new String object
		StringBuilder formattedShots1 = new StringBuilder();
		StringBuilder formattedShots2 = new StringBuilder();
				
		int shotIndex = 0;
				
		for(Frame frame : frames) {
			for(Shot frameShot : frame.getShots()) {
				//append shot to the appropriate StringBuilder based on the shot index
				if(shotIndex == 0) {
					formattedShots1.append(frameShot.toString()).append(", ");
				}else {
					formattedShots2.append(frameShot.toString()).append(", ");
				}
						
				//increment shotIndex
				shotIndex = (shotIndex + 1) % 2;	
			}
		}
				
		session.setAttribute("formattedShots1", formattedShots1.toString());
		session.setAttribute("formattedShots2", formattedShots2.toString());
				
		System.out.print("Formatted shots updated successfully.");
	}
}
