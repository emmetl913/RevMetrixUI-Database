package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Shot;

public interface IDatabase {
	public Integer insertNewAccount(String email, String password, String username, String firstname, String lastname);
	public Integer insertNewBallInDB(int account_id, float weight, String name, Boolean righthand, String brand, String color1, String color2, String color3, String material);
	
	public List<Account> getAccountByUsernameAndPassword(String username, String password);
	public List<Account> getAccountByUsername(String username);
	public List<Shot> findAllShotsWithSessionID(int sessionID);
	
	// Shot Level Methods
	public Integer insertNewGame(int sessionID, int currentLane, int gameNum, int score);
	public Integer insertNewSession(int eventID, String time, String oppType, String oppName, int score);
	public List<Establishment> getEstablishmentsByAccount(int accID);
	List<Account> getAccountByEmail(String email);
	List<Ball> getBallsByAccountID(int accountId);
	List<Ball> getBallByBallID(int ballID);
	List<Ball> getBallByName(String name);

	
	public Integer removeBall(int accID, String name);

	List<Game> getGameBySessionID(int sessionID);
	List<Session> getSessionByEventID(int eventID);


	// Shot Level Methods
	public Integer insertNewFrame(int gameID, int score, int frameNumber);
	List<Frame> getFrameByGameID(int gameID);
	public boolean updateFrameByFrameID(int frameID, int newScore);

	List<Shot> getShotByFrameID(int frameID);
	public Integer insertNewShotWithFrameID(int sessionID, int gameID, int frameID, int shotNumber, int count, int ballID, String pinsLeft);
	
	
	public Integer insertNewEstablishment(int account_id, String name, String address);
	public Integer removeEstablishment(int accID, String name);
	public List<Event> getEventsByAccount(int accID);
	public Establishment getEstablishmentByAccountAndEstablishmentID(int accID, int estaID);
	public Integer insertNewEvent(int acc_id, int estb_id, String name, String time, String type, int standing);
	public String getEventNameByAccount(int accID, int eventID);
	public Integer getCurrentGameLane(int gameID);
	public Integer[] getGamesBySessions(int sessionID);
	public Game getGameByGameID(int gameID);
	public ArrayList<Integer> getSessionsByEvent(int eventID);
	public Integer getSessionScore(int sessionID);
	public String getSessionDate(int sessionID);
	public Integer getLifetimePinsKnockedDown(int accountID);
	public Integer getLifetimePinsMissed(int accountID);
	
	//Stats
	public List<String> getStrikesFromAccount(int gameID, int sessionID);
	public List<Shot> getSparesFromAccount(int gameID, int sessionID);
	public List<Shot> getSecondShotsFromAccount(int gameID, int sessionID);
	public List<Shot> getLeavesFromGameAndSession(int gameID, int sessionID);
}
