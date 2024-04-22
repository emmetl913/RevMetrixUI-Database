package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;

import java.util.ArrayList;

import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;

import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Shot;

public interface IDatabase {
	public Integer insertNewBallInDB(float weight, String name, Boolean righthand, String brand, String color);
	public Integer insertNewAccountinDB(String email, String password, String username);
	public Integer insertNewBallInDB(int account_id, float weight, String name, Boolean righthand, String brand, String color);
	
	public List<Account> getAccountByUsernameAndPassword(String username, String password);
	public List<Account> getAccountByUsername(String username);
	public List<Shot> findAllShotsWithSessionID(int sessionID);
	
	// Shot Level Methods
	public Integer insertNewGame(int gameID, int sessionID, int currentLane, int gameNum, int score);
	public Integer insertNewSession(int sessionID, int eventID, String time, String oppType, String oppName, int score);
	public List<Establishment> getEstablishmentsByAccount(int accID);
	List<Account> getAccountByEmail(String email);
	List<Ball> getBallsByAccountIdFromDB(int accountId);
	List<Ball> getBallByName(String name);

	// Shot Level Methods
	public Integer insertNewFrame(int gameID, int score, int frameNumber);
	public Integer insertNewShotWithFrameID(int sessionID, int gameID, int frameID, int shotNumber, String count, int ballID, String pinsLeft);
	public Integer insertNewEstablishment(int account_id, String name, String address);
	public Integer removeEstablishment(int accID, String name);
	public List<Event> getEventsByAccount(int accID);
	public Integer insertNewEvent(int accID, int estbID, String name, int time, String type, int standing);
	
}
