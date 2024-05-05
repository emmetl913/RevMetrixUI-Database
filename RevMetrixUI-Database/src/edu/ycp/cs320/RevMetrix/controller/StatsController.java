package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.Shot;

public class StatsController{
//	private Shot shot;
	private Game game;
	private Session session;
	private IDatabase db = null;
	
	public StatsController() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		game = new Game(1, 0, 0, 0, 0);
		session = new Session(1, 0, null, null, null, 0);
	}
	
	public List<Shot> getStrikesFromAccount(){
		return db.getStrikesFromAccount(game.getGameID(), session.getSessionID());
	}
	
	public double strikesPercentage() {
		int totalNumberOfFrames = 10;
		int numberOfStrikes = 0;
		
		//retrieves the strikes from the database
		List<Shot> shots = getStrikesFromAccount();
		
		if(shots == null) {
			System.err.println("List of shots is null");
			return 0.0;
		}
		
		for(Shot shot : shots) {
			System.out.print(shot);
			if(shot != null && shot.getPinsLeft().equals('X')) {
				numberOfStrikes++;
			}
		}
		
		System.out.println("Number of strikes: " +numberOfStrikes);
		
		if(totalNumberOfFrames == 0) {
			System.err.println("No frames");
			return 0.0;
		}
		
		return ((double) numberOfStrikes / totalNumberOfFrames)*100;
	}
	
	public List<Shot> getSparesFromAccount(){
		return db.getSparesFromAccount(game.getGameID(), session.getSessionID());
	}
	
	public double sparePercentage() {
		List<Shot> spares = getSparesFromAccount();
		List<Shot> secondShots = db.getSecondShotsFromAccount(game.getGameID(), session.getSessionID());

		int numberOfSecondShots = secondShots.size();
		int numberOfSpares = spares.size();
		
		if(numberOfSecondShots == 0) {
			return 0.0;
		}
		
		return ((double) numberOfSpares / numberOfSecondShots);
	}
	
	public int getTotalLeavesFromGameAndSession(int gameID, int sessionID) {
		List<Shot> leaves = db.getLeavesFromGameAndSession(gameID, sessionID);
		return leaves.size();
	}
	
	public double leavePercentage() {
		List<Shot> spares = db.getSparesFromAccount(game.getGameID(), session.getSessionID());
		int totalSpares = spares.size();
		
		int numOfLeaves = getTotalLeavesFromGameAndSession(game.getGameID(), session.getSessionID());
		
		if(numOfLeaves == 0) {
			return 0.0;
		}
		
		return ((double) totalSpares / numOfLeaves) * 100;
	}
}