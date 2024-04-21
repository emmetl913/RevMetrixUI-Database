package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;

import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Shot;

public interface IDatabase {
	/*
	 * public List<Pair<Author, Book>> findAuthorAndBookByTitle(String title);
	 * public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String
	 * lastName); public Integer insertBookIntoBooksTable(String title, String isbn,
	 * int published, String lastName, String firstName); public List<Pair<Author,
	 * Book>> findAllBooksWithAuthors(); public List<Author> findAllAuthors();
	 * public List<Author> removeBookByTitle(String title);
	 */		
	
	public Integer insertNewAccountinDB(String email, String password, String username);
	public Integer insertNewBallInDB(int account_id, float weight, String name, Boolean righthand, String brand, String color);
	
	public List<Account> getAccountByUsernameAndPassword(String username, String password);
	public List<Account> getAccountByUsername(String username);
	public List<Shot> findAllShotsWithSessionID(int sessionID);
	
	// Shot Level Methods
	List<Account> getAccountByEmail(String email);
	List<Ball> getBallsByAccountIdFromDB(int accountId);
	List<Ball> getBallByName(String name);

	// Shot Level Methods
	public Integer insertNewEvent(int eventID, int estbID, String name, int time, String type, int standing);
	public Integer insertNewSession(int sessionID, int eventID, String time, String oppType, String oppName, int score);
	public Integer insertNewGame(int gameID, int sessionID, int currentLane, int gameNum, int score);
	public Integer insertNewFrame(int gameID, int score, int frameNumber);
	public Integer insertNewShotWithFrameID(int sessionID, int gameID, int frameID, int shotNumber, String count, int ballID, String pinsLeft);
	
}
