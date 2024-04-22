package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.model.Game;

public class GameControllerReal {
	private List<Game> games;
	private Game model;
	private IDatabase db = null;
	
	public GameControllerReal()
	{
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	public void addGame(Game game)
	{
		this.games.add(game);
	}
	public void removeGame(Game game)
	{
		this.games.remove(game);
	}
	public Game getGameAtIndex(int index)
	{
		return this.games.get(index);
	}
	
//	public List<Game> getGames()
//	{
//		return this.games;
//	}
	
	public int getNumberOfGames()
	{
		return games.size();
	}
	public void setModel(Game model)
	{
		this.model = model;
	}
	
	public List<Game> getAllGamesForSessionID(int sessionID)
	{
		List<Game> gameList = db.getGameBySessionID(sessionID);
		if (gameList.isEmpty())
		{
			System.out.println("Games for <"+ sessionID +"> do not exist");
			return null;
		}
		else
		{
			return gameList;
		}
	}
	public Integer insertNewGame(int gameID, int sessionID, int lane, int gameNumber, int score)
	{
		Integer newGame = db.insertNewGame(gameID, sessionID, lane, gameNumber, score);
		
		if (newGame == null)
		{
			System.out.println("Game for <"+ gameID +"> and <"+ sessionID +"> does not exist");
			return null;
		} else
		{
			return newGame;
		}
	}
	public List<Game> getGames() {
		return this.games;
	}
}
