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
	private IDatabase db = null;
	
	public StatsController() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
	public List<String> getStrikesFromAccount(int gameID, int sessionID){
		return db.getStrikesFromAccount(gameID, sessionID);
	}
	
	public double strikesPercentage(int gameID, int sessionID) {
		int totalNumberOfFrames = 10;
		int numberOfStrikes = 0;
		
		//retrieves the strikes from the database
		ArrayList<String> shots = (ArrayList<String>) getStrikesFromAccount(gameID, sessionID);
		
		if(shots == null) {
			System.err.println("List of shots is null");
			return 0.0;
		}
		
		for(String shot : shots) {
			System.out.println(shot);
			if(shot != null) {
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
	
	public List<Shot> getSparesFromAccount(int gameID, int sessionID){
		return db.getSparesFromAccount(gameID, sessionID);
	}
	
	public double sparePercentage(int gameID, int sessionID) {
		List<Shot> spares = getSparesFromAccount(gameID, sessionID);
		List<Shot> secondShots = db.getSecondShotsFromAccount(gameID, sessionID);

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
	
	public double leavePercentage(int gameID, int sessionID) {
		List<Shot> spares = db.getSparesFromAccount(gameID, sessionID);
		int totalSpares = spares.size();
		
		int numOfLeaves = getTotalLeavesFromGameAndSession(gameID, sessionID);
		
		if(numOfLeaves == 0) {
			return 0.0;
		}
		
		return ((double) totalSpares / numOfLeaves) * 100;
	}
}