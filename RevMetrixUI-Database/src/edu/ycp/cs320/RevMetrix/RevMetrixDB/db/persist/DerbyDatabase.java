package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import edu.ycp.cs320.RevMetrix.RevmetrixDB.sqldemo.DBUtil;
//import db.persist.IDatabase;
//import db.persist.PersistenceException;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
//import db.persist.InitialData;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.Shot;



public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	} 
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;	
	
	@Override
	public List<Establishment> getEstablishmentsByAccount(int accID) {
		return executeTransaction(new Transaction<List<Establishment>>() {
			@Override
			public List<Establishment> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from establishments "+
							"where account_id = ?"
					);
					stmt.setInt(1, accID);

					
					
					List<Establishment> result = new ArrayList<Establishment>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Establishment esta = new Establishment();
						loadEstablishment(esta, resultSet, 1);
						
						result.add(esta);
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Establishment were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public ArrayList<Integer> getSessionsByEvent(int eventID) {
		return executeTransaction(new Transaction<ArrayList<Integer>>() {
			@Override
			public ArrayList<Integer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;	
				try {
					stmt = conn.prepareStatement(
							"select * from sessions "+
							"where event_id = ?"
					);
					stmt.setInt(1, eventID);
					
					
					ArrayList<Integer> result = new ArrayList<Integer>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Session sess = new Session();
						loadSession(sess, resultSet, 1);
						
						result.add(sess.getSessionID());
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Establishment were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Integer getLifetimePinsKnockedDown(int accountID) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSetEvents = null;
				ResultSet resultSetShots = null;
				
				//Find all events for the account
				try {
					stmt1 = conn.prepareStatement(
							"select * from events "+
							"where acc_id = ?"
					);
					stmt1.setInt(1, accountID);
					
					
					ArrayList<Integer> events = new ArrayList<Integer>();
					resultSetEvents = stmt1.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					int count = 0;

					while (resultSetEvents.next()) {
						found = true;
						Event event = new Event();
						loadEvent(event, resultSetEvents, 1);
//						count++;
//						System.out.print(" Event: "+count);
						
						events.add(event.getEventID());
					}
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					Integer result = 0;
					
					for(int i = 1; i <= events.size(); i++) {
						ArrayList<Integer> sessions = getSessionsByEvent(events.get(i-1));
						for(int j = 1; j <= sessions.size(); j++) {
							try {
								stmt2 = conn.prepareStatement(
										"select * from shots "+
										"where session_id = ?"
								);
								stmt2.setInt(1, sessions.get(j-1));
								resultSetShots = stmt2.executeQuery();
								
								// for testing that a result was returned
								found = false;
								
								count = 0;
								while (resultSetShots.next()) {
//									count++;
//									System.out.print(" Shot: "+count+" Session : "+ sessions.get(j-1));
									found = true;
									Shot shot = new Shot();
									loadShot(shot, resultSetShots, 1);
//									System.out.print("Event: "+count);
									
									result += shot.getCount();
								}
								// check if any authors were found
								if (!found) {
									System.out.println("No Shots were found in the database");
								}
							}finally {
								DBUtil.closeQuietly(stmt2);
								DBUtil.closeQuietly(resultSetShots);
							}
						}
					}
					return result;
				} finally {
					DBUtil.closeQuietly(resultSetEvents);
					DBUtil.closeQuietly(stmt1);
				}
			}
		});
	}
	
	@Override
	public Integer getLifetimePinsMissed(int accountID) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSetEvents = null;
				ResultSet resultSetShots = null;
				
				//Find all events for the account
				try {
					stmt1 = conn.prepareStatement(
							"select * from events "+
							"where acc_id = ?"
					);
					stmt1.setInt(1, accountID);
					
					
					ArrayList<Integer> events = new ArrayList<Integer>();
					resultSetEvents = stmt1.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					int count = 0;

					while (resultSetEvents.next()) {
						found = true;
						Event event = new Event();
						loadEvent(event, resultSetEvents, 1);
//						count++;
//						System.out.print(" Event: "+count);
						
						events.add(event.getEventID());
					}
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					Integer result = 0;
					
					for(int i = 1; i <= events.size(); i++) {
						ArrayList<Integer> sessions = getSessionsByEvent(events.get(i-1));
						for(int j = 1; j <= sessions.size(); j++) {
							try {
								stmt2 = conn.prepareStatement(
										"select * from shots "+
										"where session_id = ?"
								);
								stmt2.setInt(1, sessions.get(j-1));
								resultSetShots = stmt2.executeQuery();
								
								// for testing that a result was returned
								found = false;
								
								count = 0;
								while (resultSetShots.next()) {
//									count++;
//									System.out.print(" Shot: "+count+" Session : "+ sessions.get(j-1));
									found = true;
									Shot shot = new Shot();
									loadShot(shot, resultSetShots, 1);
//									System.out.print("Event: "+count);
									
									result += (10 - shot.getCount());
								}
								// check if any authors were found
								if (!found) {
									System.out.println("No Shots were found in the database");
								}
							}finally {
								DBUtil.closeQuietly(stmt2);
								DBUtil.closeQuietly(resultSetShots);
							}
						}
					}
					return result;
				} finally {
					DBUtil.closeQuietly(resultSetEvents);
					DBUtil.closeQuietly(stmt1);
				}
			}
		});
	}
	
	@Override
	public Establishment getEstablishmentByAccountAndEstablishmentID(int accID, int estaID) {
		return executeTransaction(new Transaction<Establishment>() {
			@Override
			public Establishment execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from establishments "+
							"where account_id = ? and esta_id = ?"
					);
					stmt.setInt(1, accID);
					stmt.setInt(2, estaID);
					
					
					Establishment result = new Establishment();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Establishment esta = new Establishment();
						loadEstablishment(esta, resultSet, 1);
						
						result = esta;
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Establishment were found in the database at AccID and ESTAID");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Event> getEventsByAccount(int accID) {
		return executeTransaction(new Transaction<List<Event>>() {
			@Override
			public List<Event> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from events "+
							"where acc_id = ?"
					);
					stmt.setInt(1, accID);
					
					List<Event> result = new ArrayList<Event>();
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						Event event = new Event();
						loadEvent(event, resultSet, 1);
						
						result.add(event);
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public String getEventNameByAccount(int accID, int eventID) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from events "+
							"where acc_id = ? and event_id = ?"
					);
					stmt.setInt(1, accID);
					stmt.setInt(2, eventID);
					
					String result = "";
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						Event event = new Event();
						loadEvent(event, resultSet, 1);
						
						result = event.getEventName();
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Integer getSessionScore(int sessionID) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from sessions "+
							"where session_id = ?"
					);
					stmt.setInt(1, sessionID);
					
					Integer result = null;
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						Session session = new Session();
						loadSession(session, resultSet, 1);
						
						result = session.getScore();
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public String getSessionDate(int sessionID) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from sessions "+
							"where session_id = ?"
					);
					stmt.setInt(1, sessionID);
					
					String result = null;
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						Session session = new Session();
						loadSession(session, resultSet, 1);
						
						result = session.getTime();
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Integer getCurrentGameLane(int gameID) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from games "+
							"where game_id = ?"
					);
					stmt.setInt(1, gameID);
					
					int result = -1;
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						Game game = new Game();
						loadGame(game, resultSet, 1);
						
						result = game.getLane();
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Integer[] getGamesBySessions(int sessionID) {
		return executeTransaction(new Transaction<Integer[]>() {
			@Override
			public Integer[] execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from games "+
							"where session_id = ?"
					);
					stmt.setInt(1, sessionID);
					
					Integer result[] = new Integer[3];
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					int i = 0;
					
					while (resultSet.next()) {
						
						found = true;
						Game game = new Game();
						loadGame(game, resultSet, 1);
						
						result[i] = game.getScore();
						i++;
					}
					
					// check if any authors were found
					if (!found) {
						System.out.println("No Events were found in the database");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public Integer insertNewEstablishment(int account_id, String name, String address) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer esta_id = -1;

				
				// try to find ball_id in db
				try
				{
					stmt1 = conn.prepareStatement(
							"select esta_id from establishments"
							+ " where name = ? and address = ? and account_id = ?"
					);
					
					stmt1.setString(1, name);
					stmt1.setString(2, address);
					stmt1.setInt(3, account_id);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						esta_id = resultSet1.getInt(1);

						System.out.println("Establishment <"+ name +"> found with address <"+ address +"> and with acc_id: "+account_id + "and esta id: "+esta_id);
					}
					else 
					{
						System.out.println("Establishment <"+ name +"> with address <"+ address +"> was not found");
					}
					if(esta_id <= 0)
					{
						
						stmt2 = conn.prepareStatement(
								"insert into establishments (account_id, name, address)"
								+ " values (?, ?, ?)"
						);
						//get current account_id
						
						
						stmt2.setInt(1, account_id);	//				//ball id, accountid, weight, name, righthand, brand, color
						stmt2.setString(2, name);
						stmt2.setString(3, address);
						stmt2.executeUpdate();
						
						// get the new ball_id
						stmt3 = conn.prepareStatement(
								"select esta_id from establishments"
									+ " where name = ? and address = ? and account_id = ?"
						);
							
						stmt3.setString(1, name);
						stmt3.setString(2, address);
						stmt3.setInt(3, account_id);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							esta_id = resultSet3.getInt(1);

							System.out.println("New Establishments inserted. Esta_id: "+ esta_id + 
									" account_id: "+account_id+" Name: "+name+" Address: "+address);
						}
					}
					return esta_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	
	public Integer removeEstablishment(int accID, String name) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"delete from establishments where name = ? and account_id = ?"
					);
					stmt.setString(1, name);
					stmt.setInt(2, accID);

					stmt.execute();
					
					return 1;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	
	@Override
	public List<Shot> findAllShotsWithSessionID(int sessionID) {
		return executeTransaction(new Transaction<List<Shot>>() {
			@Override
			public List<Shot> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select * from shots where shots.session_id = ?"
					);
					stmt1.setInt(1, sessionID);
					
					List<Shot> result = new ArrayList<Shot>();
					
					resultSet1 = stmt1.executeQuery();
					
					boolean found = false;
					while(resultSet1.next())
					{
						found = true;
						Shot shot = new Shot(0,0,0,0, 0, 0, "", "");
						loadShot(shot, resultSet1, 1);
						result.add(shot);
					}
					System.out.println("Found any shots?: "+ found);
					return result;
				} finally
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
				}
			}
		});

	}
	private void loadShot(Shot shot, ResultSet resultSet, int index) throws SQLException {
		shot.setShotID(resultSet.getInt(index++));
		shot.setFrameID(resultSet.getInt(index++));
		shot.setGameID(resultSet.getInt(index++));
		shot.setSessionID(resultSet.getInt(index++));
		shot.setShotNumber(resultSet.getInt(index++));
		shot.setCount(resultSet.getInt(index++));
		shot.setBallID(resultSet.getInt(index++));
		shot.setPinsLeft(resultSet.getString(index++));
		shot.setLeave(resultSet.getString(index++));
	}
	
//	+ " shot_id integer primary key"
//	+ " generated always as identity (start with 1, increment by 1),"
//	+ " frame_id integer,"
//	+ " game_id integer,"
//	+ " session_id integer,"
//	+ " shot_number integer,"
//	+ " count integer,"
//	+ " ball_id integer,"
//	+ " pins_left varchar(10),"
//	+ " leave varchar(2)"
	
	
	@Override
	public List<Account> getAccountByUsernameAndPassword(String username, String password) {
		return executeTransaction(new Transaction<List<Account>>() {
			@Override
			public List<Account> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
						"select * from accounts"+
						"  where accounts.username = ?"+
						"  and accounts.password = ?"
					);
					stmt1.setString(1, username);
					stmt1.setString(2, password);
					
					List<Account> result = new ArrayList<Account>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Account acc = new Account("", "", "","","");
						loadAccount(acc, resultSet1, 1);
						
						result.add(acc);
					}
					
					
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	
	@Override
	public List<Account> getAccountByUsername(String username) {
		return executeTransaction(new Transaction<List<Account>>() {
			@Override
			public List<Account> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
						"select * from accounts"+
						"  where accounts.username = ? "
					);
					stmt1.setString(1, username);
					
					List<Account> result = new ArrayList<Account>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Account acc = new Account("", "", "","","");
						loadAccount(acc, resultSet1, 1);
						
						result.add(acc);
					}
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	@Override
	public List<Account> getAccountByEmail(String email) {
		return executeTransaction(new Transaction<List<Account>>() {
			@Override
			public List<Account> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
						"select * from accounts"+
						"  where accounts.email = ? "
					);
					stmt1.setString(1, email);
					
					List<Account> result = new ArrayList<Account>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Account acc = new Account("", "", "","","");
						loadAccount(acc, resultSet1, 1);
						
						result.add(acc);
					}
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);					
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	
	public Integer removeBall(int accID, String name) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"delete from balls where name = ? and account_id = ?"
					);
					stmt.setString(1, name);
					stmt.setInt(2, accID);

					stmt.execute();
					
					return 1;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Ball> getBallByName(String name) {
		return executeTransaction(new Transaction<List<Ball>>() {
			@Override
			public List<Ball> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
						"select * from balls"+
						"  where balls.name = ? "
					);
					stmt1.setString(1, name);
					
					List<Ball> result = new ArrayList<Ball>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Ball ball = new Ball(0,0,"", true,"","", "","","");
						loadBall(ball, resultSet1, 1);
						
						result.add(ball);
					}
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	@Override public Game getGameByGameID(int gameID)
	{
		return executeTransaction(new Transaction<Game>() {
			@Override
			public Game execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select * from games"
							+" where game_id = ?"
					);
					stmt1.setInt(1, gameID);
					
					Game result = new Game();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Game game = new Game(0, 0, 0, 0, 0);
						loadGame(game, resultSet1, 1);
						System.out.println("Game found with ID: "+ game.getGameID());
						result = game;
					}
					
					return result;
				} finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	
	@Override
	public List<Game> getGameBySessionID(int sessionID) {
		return executeTransaction(new Transaction<List<Game>>() {
			@Override
			public List<Game> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select * from games"
							+" where games.session_id = ?"
					);
					stmt1.setInt(1, sessionID);
					
					List<Game> result = new ArrayList<Game>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Game game = new Game(0, 0, 0, 0, 0);
						loadGame(game, resultSet1, 1);
						System.out.println("Game found with ID: "+ game.getGameID());
						result.add(game);
					}
					
					return result;
				} finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	private void loadGame(Game game, ResultSet resultSet, int index) throws SQLException {
		game.setGameID(resultSet.getInt(index++));
		game.setSessionID(resultSet.getInt(index++));
		game.setLane(resultSet.getInt(index++));
		game.setGameNumber(resultSet.getInt(index++));
		game.setScore(resultSet.getInt(index++));
	}
	
	
	@Override
	public List<Session> getSessionByEventID(int eventID) {
		return executeTransaction(new Transaction<List<Session>>() {
			@Override
			public List<Session> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select * from sessions"
							+" where sessions.event_id = ?"
					);
					stmt1.setInt(1, eventID);
					
					List<Session> result = new ArrayList<Session>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Session session = new Session(0, 0, "", "", "", 0);
						loadSession(session, resultSet1, 1);
						//System.out.println("SKREET" + ball.getName());
						result.add(session);
					}
					
					return result;
				} finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	
	@Override
	public List<Ball> getBallsByAccountID(int accountId) {
		return executeTransaction(new Transaction<List<Ball>>() {
			@Override
			public List<Ball> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select * from balls"+
							" where balls.account_id = ?"
					);
					
					stmt1.setInt(1, accountId);
					
					List<Ball> result = new ArrayList<Ball>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
				
					while(resultSet1.next())
					{
						found = true;
						Ball ball = new Ball();
						loadBall(ball, resultSet1, 1);
						//System.out.println("SKREET" + ball.getName());
						result.add(ball);
					}
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	private void loadBall(Ball ball, ResultSet resultSet, int index) throws SQLException {
		//Proper order: ball id, accountid, weight, name, righthand, brand, color
		ball.setBallId(resultSet.getInt(index++));
		ball.setAccountId(resultSet.getInt(index++));
		ball.setWeight(resultSet.getFloat(index++));
		ball.setName(resultSet.getString(index++));
		ball.setRightHanded(resultSet.getBoolean(index++));
		ball.setBrand(resultSet.getString(index++));
		ball.setColor1(resultSet.getString(index++));
		ball.setColor2(resultSet.getString(index++));
		ball.setColor3(resultSet.getString(index++));
		ball.setMaterial(resultSet.getString(index++));
	}
	@Override
	public List<Shot> getShotByFrameID(int frameID){
		return executeTransaction(new Transaction<List<Shot>>() {
			@Override
			public List<Shot> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select * from shots"+
							" where shots.frame_id = ?"
					);
					
					stmt1.setInt(1, frameID);
					
					List<Shot> result = new ArrayList<Shot>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
				
					while(resultSet1.next())
					{
						found = true;
						Shot shot = new Shot();
						loadShot(shot, resultSet1, 1);
						//System.out.println("SKREET" + ball.getName());
						result.add(shot);
					}
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	
	@Override
	public Integer insertNewBallInDB(int account_id, float weight, String name, Boolean righthand, String brand, String color1, String color2, String color3, String material) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer ball_id = -1;

				
				// try to find ball_id in db
				try
				{
					stmt1 = conn.prepareStatement(
							"select ball_id from balls"
							+ " where name = ? and brand = ? and account_id = ?"
					);
					
					stmt1.setString(1, name);
					stmt1.setString(2, brand);
					stmt1.setInt(3, account_id);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						ball_id = resultSet1.getInt(1);

						System.out.println("Ball <"+ name +"> found with brand <"+ brand +"> and with acc_id: "+account_id + " and ball id: "+ball_id);
					}
					else 
					{
						System.out.println("Ball <"+ name +"> with brand <"+ brand +"> was not found");
					}
					if(ball_id <= 0)
					{
						
						stmt2 = conn.prepareStatement(
								"insert into balls (account_id, weight, name, righthand, brand, color1, color2, color3, material)"
								+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
						);
						//get current account_id
						
						
						stmt2.setInt(1, account_id);	//				//ball id, accountid, weight, name, righthand, brand, color
						stmt2.setFloat(2, weight);
						stmt2.setString(3, name);
						stmt2.setBoolean(4, righthand);
						stmt2.setString(5, brand);
						stmt2.setString(6,color1);
						stmt2.setString(7,color2);
						stmt2.setString(8,color3);
						stmt2.setString(9, material);

						stmt2.executeUpdate();
						
						// get the new ball_id
						stmt3 = conn.prepareStatement(
								"select ball_id from balls"
								+ " where name = ? and brand = ? and account_id = ?"
						);
							
						stmt3.setString(1, name);
						stmt3.setString(2, brand);
						stmt3.setInt(3, account_id);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							ball_id = resultSet3.getInt(1);

							System.out.println("New ball inserted. ball_id: "+ ball_id + 
									" account_id: "+account_id+" weight: "+weight+" name: "+name+
									" isRightHanded: " +righthand+ " brand: "+brand+" color: "+color1
									+"color2: "+ color2+" color3: "+ color3+" material: "+material);
						}
					}
					return ball_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	@Override
	public Integer insertNewShotWithFrameID(int sessionID, int gameID, int frameID, int shotNumber, int count, int ballID,
			String pinsLeft) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer shot_id = -1;
				
				// try to find account_id in db
				try
				{
//					stmt1 = conn.prepareStatement(
//							"select shot_id from shots"
//							+ " where frame_id = ? "
//					);
//					
//					stmt1.setInt(1, frameID);
//					
//					resultSet1 = stmt1.executeQuery();
//					
//					if(resultSet1.next())
//					{
//						shot_id = resultSet1.getInt(1);
//						System.out.println("Shot <"+ shot_id +"> found with frameID <"+ frameID +">");
//					}
//					else 
//					{
//						System.out.println("Shot <"+ shot_id +"> was not found");
//					}
					if(shot_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into shots (frame_id, game_id, session_id, shot_number, count, ball_id, pins_left, leave) values (?, ?, ?, ?, ?, ?, ?, ?) "
						);
						stmt2.setInt(1, frameID);
						stmt2.setInt(2, gameID);
						stmt2.setInt(3, sessionID);
						stmt2.setInt(4, shotNumber);
						stmt2.setInt(5, count);
						stmt2.setInt(6, ballID);
						stmt2.setString(7, pinsLeft);
						stmt2.setString(8, "no");
						
						stmt2.executeUpdate();
						
						System.out.println("New shot <"+frameID+"> , <"+gameID+"> , <"+sessionID+"> , <"+shotNumber+"> , <"+count+"> , <"+ballID+"> , <"+pinsLeft+"> inserted into shots");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select * from shots "
								+ " where shot_id = ? and frame_id = ?"
						);
						stmt3.setInt(1, shot_id);
						stmt3.setInt(2, frameID);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							shot_id = resultSet3.getInt(1);
							System.out.println("New frame <"+shot_id+"> , <"+frameID+"> ID: "+shot_id+" was found");
						}
					}
					return shot_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	@Override
	public boolean updateFrameByFrameID(int frameID, int newScore) {
	    return executeTransaction(new Transaction<Boolean>() {
	        @Override
	        public Boolean execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            try {
	                stmt = conn.prepareStatement(
	                        "update frames set score = ? where frame_id = ?"
	                );
	                stmt.setInt(1, newScore);
	                stmt.setInt(2, frameID);

	                int rowsAffected = stmt.executeUpdate();

	                // If one or more rows were affected, return true
	                return rowsAffected > 0;
	            } finally {
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	@Override
	public Integer insertNewFrame(int gameID, int score, int frameNumber) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer frame_id = -1;
				
				// try to find account_id in db
				try
				{
					stmt1 = conn.prepareStatement(
							"select frame_id from frames"
							+ " where game_id = ? and frame_number = ? "
					);
					
					stmt1.setInt(1, gameID);
					stmt1.setInt(2, frameNumber);

					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						frame_id = resultSet1.getInt(1);
						System.out.println("Frame <"+ frame_id +"> found with gameID <"+ gameID +"> and frame# <"+frameNumber+">" );
					}
					else 
					{
						System.out.println("Frame <"+ frame_id +"> was not found");
					}
					if(frame_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into frames (game_id, score, frame_number) "
								+ " values(?, ?, ?)"
						);
						stmt2.setInt(1, gameID);
						stmt2.setInt(2, score);
						stmt2.setInt(3, frameNumber);
						
						stmt2.executeUpdate();
						
						System.out.println("New frame <"+gameID+"> , <"+score+"> , <"+frameNumber+"> inserted into frames");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select * from frames "
								+ " where game_id = ? and frame_number = ?"
						);
						stmt3.setInt(1, gameID);
						stmt3.setInt(2, frameNumber);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							frame_id = resultSet3.getInt(1);
							System.out.println("New frameID = <"+frame_id+"> , <"+gameID+"> ID: "+frame_id +"Frame#: " + frameNumber);
						}
					}
					return frame_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	@Override
	public List<Frame> getFrameByGameID(int gameID) {
		return executeTransaction(new Transaction<List<Frame>>() {
			@Override
			public List<Frame> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select * from frames"+
							" where frames.game_id = ?"
					);
					
					stmt1.setInt(1, gameID);
					
					List<Frame> result = new ArrayList<Frame>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
				
					while(resultSet1.next())
					{
						found = true;
						Frame frame = new Frame(0,0,0);
						loadFrame(frame, resultSet1, 1);
						//System.out.println("SKREET" + ball.getName());
						result.add(frame);
					}
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
	private void loadFrame(Frame frame, ResultSet resultSet, int index) throws SQLException {
		//Proper order: ball id, accountid, weight, name, righthand, brand, color
		frame.setFrameID(resultSet.getInt(index++));
		frame.setGameID(resultSet.getInt(index++));
		frame.setScore(resultSet.getInt(index++));
		frame.setFrameNumber(resultSet.getInt(index++));
	}
	@Override 
	public Integer insertNewGame(final int sessionID, final int currentLane, final int gameNum, final int score )
	{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer game_id = -1;
				
				// try to find account_id in db
				try
				{
//					stmt1 = conn.prepareStatement(
//							"select game_id from games"
//							+ " where session_id = ?"
//					);
//					
//					stmt1.setInt(1, sessionID);
//					
//					resultSet1 = stmt1.executeQuery();
//					
//					if(resultSet1.next())
//					{
//						game_id = resultSet1.getInt(1);
//						System.out.println("Game <"+ game_id +"> found with sessionID <"+ sessionID +">");
//					}
//					else 
//					{
//						System.out.println("game <"+ game_id +"> was not found");
//					}
					if(game_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into games (session_id, currentLane, gameNumber, score) "
								+ " values(?, ?, ?, ?)"
						);
						stmt2.setInt(1, sessionID);
						stmt2.setInt(2, currentLane);
						stmt2.setInt(3, gameNum);
						stmt2.setInt(4, score);
						
						stmt2.executeUpdate();
						
						System.out.println("New game <"+sessionID+"> , <"+currentLane+"> , <"+gameNum+">, <"+score+"> inserted into games");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select * from games "
								+ " where session_id = ? and currentLane = ? and gameNumber = ?"
						);
						stmt3.setInt(1, sessionID);
						stmt3.setInt(2, currentLane);
						stmt3.setInt(3,  gameNum);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							game_id = resultSet3.getInt(1);
							System.out.println("New game <"+sessionID+"> , <"+currentLane+"> , <"+gameNum+">, <"+score+"> ID: "+game_id);
						}
					}
					return game_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	
	@Override
	public Integer insertNewEvent(int accID, int estbID, String name, String time, String type, int standing) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer event_id = -1;
				
				// try to find account_id in db
				try
				{
					stmt1 = conn.prepareStatement(
							"select event_id from events"
							+ " where acc_id = ? and name = ? and time = ?"
					);
					
					stmt1.setInt(1, accID);
					stmt1.setString(2, name);
					stmt1.setString(3, time);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						event_id = resultSet1.getInt(1);
						System.out.println("Event <"+ name +"> found with estbID <"+ estbID +">");
					}
					else 
					{
						System.out.println("Event <"+ name +"> was not found");
					}
					if(event_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into events (acc_id, estb_id, name, time, type, standing) "
								+ " values(?, ?, ?, ?, ?, ?)"
						);
						stmt2.setInt(1, accID);
						stmt2.setInt(2, estbID);
						stmt2.setString(3, name);
						stmt2.setString(4, time);
						stmt2.setString(5, type);
						stmt2.setInt(6, standing);
						
						stmt2.executeUpdate();
						
						System.out.println("New event <"+name+"> , <"+time+"> , <"+type+">, <"+standing+"> inserted into event");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select event_id from events"
							+ " where acc_id = ? and name = ? and time = ?"
						);
								
						stmt3.setInt(1, accID);
						stmt3.setString(2, name);
						stmt3.setString(3, time);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							event_id = resultSet3.getInt(1);
							System.out.println("New event <"+name+"> , <"+time+"> , <"+type+">, <"+standing+"> ID: "+event_id);
						}
					}
					return event_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	@Override
	public Integer insertNewAccount(final String email, final String password, final String username, String firstname, String lastname)
	{
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer account_id = -1;
				
				// try to find account_id in db
				try
				{
					stmt1 = conn.prepareStatement(
							"select account_id from accounts"
							+ " where email = ? and username = ?"
					);
					
					stmt1.setString(1, email);
					stmt1.setString(2, username);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						account_id = resultSet1.getInt(1);
						System.out.println("User <"+ username +"> found with email <"+ email +"> and with id: "+account_id);
					}
					else 
					{
						System.out.println("User <"+ username +"> with email <"+ email +"> was not found");
					}
					if(account_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into accounts (username, email, password, firstname, lastname) "
								+ " values(?, ?, ?, ?, ?)"
						);
						stmt2.setString(1, username);
						stmt2.setString(2, email);
						stmt2.setString(3, password);
						stmt2.setString(4,  firstname);
						stmt2.setString(5, lastname);
						
						stmt2.executeUpdate();
						
						System.out.println("New account <"+email+"> , <"+username+"> , <"+password+">, <"+firstname+"> , <"+lastname+"> inserted into accounts");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select account_id from accounts "
								+ " where email = ? and username = ?"
						);
						stmt3.setString(1, email);
						stmt3.setString(2, username);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							account_id = resultSet3.getInt(1);
							System.out.println("New account <"+email+"> , <"+username+"> ID:"+account_id);
						}
					}
					return account_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	
	@Override
	public Integer insertNewSession(int eventID, String time, String oppType, String oppName, int score) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer session_id = -1;
				
				try
				{
					stmt1 = conn.prepareStatement(
							"select session_id from sessions where "
							+ " event_id = ? and time = ? and oppType = ? and oppName = ? and score = ?"
					);
					stmt1.setInt(1, eventID);
					stmt1.setString(2, time);
					stmt1.setString(3, oppType);
					stmt1.setString(4, oppName);
					stmt1.setInt(5, score);
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						session_id = resultSet1.getInt(1);
						System.out.println("Session <"+ time +"> found with id: "+session_id);
					}
					else 
					{
						System.out.println("Session <"+ time +"> was not found");
					}
					if(session_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into sessions (event_id, time, oppType, oppName, score) "
								+ " values(?, ?, ?, ?, ?) "
						);
						stmt2.setInt(1, eventID);
						stmt2.setString(2, time);
						stmt2.setString(3, oppType);
						stmt2.setString(4, oppName);
						stmt2.setInt(5, score);
						
						stmt2.executeUpdate();
						
						System.out.println("New session <"+eventID+"><"+time+"> , <"+oppType+"> , <"+oppName+">, <"+score+"> inserted into sessions");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select * from sessions "
								+ " where event_id = ? and time = ? and oppType = ? and oppName = ? and score = ? "
						);
						stmt3.setInt(1, eventID);
						stmt3.setString(2, time);
						stmt3.setString(3, oppType);
						stmt3.setString(4, oppName);
				 		stmt3.setInt(5, score);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							session_id = resultSet3.getInt(1);
							System.out.println("New session  <"+session_id+">");
						}
					}
						return session_id;
				}
				finally 
				{
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					
				}
			}
		});
	}
	
	//get strikes from the account
	@Override
	public List<String> getStrikesFromAccount(int gameID, int sessionID){
		return executeTransaction(new Transaction<List<String>>() {
			@Override
			public List<String> execute(Connection conn) throws SQLException{
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from shots " +
							"where game_id = ? and session_id = ? and leave = 'X'"
					);
					
					stmt.setInt(1, gameID);
					stmt.setInt(2, sessionID);
					
					List<String> result = null;
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						
						Shot shot = new Shot();
						
						loadShot(shot, resultSet, 1);
						
						result.add(shot.getLeave());
					}
					
					return result;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<Shot> getSparesFromAccount(int gameID, int sessionID){
		return executeTransaction(new Transaction<List<Shot>>() {
			@Override
			public List<Shot> execute(Connection conn) throws SQLException{
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from shots " +
							"where game_id = ? and session_id = ? and leave = '/'"
					);
					
					stmt.setInt(1, gameID);
					stmt.setInt(2, sessionID);
					
					List<Shot> result = new ArrayList<Shot>();
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						
						Shot shot = new Shot();
						
						loadShot(shot, resultSet, 1);
						
						result.add(shot);
					}
					
					return result;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	//get number of second shots
//	@Override
	public List<Shot> getSecondShotsFromAccount(int gameID, int sessionID){
		return executeTransaction(new Transaction<List<Shot>>() {
			@Override
			public List<Shot> execute(Connection conn) throws SQLException{
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from shots " +
							"where game_id = ? and session_id = ? and shot_number = 2"
					);
					
					stmt.setInt(1, gameID);
					stmt.setInt(2, sessionID);
					
					List<Shot> result = new ArrayList<Shot>();
					resultSet = stmt.executeQuery();
					
					Boolean found = false;
					
					while(resultSet.next()) {
						found = true;
						
						Shot shot = new Shot();
						
						loadShot(shot, resultSet, 1);
						
						result.add(shot);
					}
					
					return result;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public List<Shot> getLeavesFromGameAndSession(int gameID, int sessionID){
		return executeTransaction(new Transaction<List<Shot>>() {
			@SuppressWarnings("resource")
			@Override
			public List<Shot> execute(Connection conn) throws SQLException{
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				int count = 0;
				
				try {
					stmt = conn.prepareStatement(
							"select * from shots " +
							"where game_id = ? and session_id = ? and (leave = 'X' or leave = '/' or leave = '-' or leave = 'F')"
					);
					
					stmt.setInt(1, gameID);
					stmt.setInt(2, sessionID);
					
					resultSet = stmt.executeQuery();
					
					List<Shot> leaves = new ArrayList<>();
					while(resultSet.next()) {
						Shot shot = new Shot();
						loadShot(shot, resultSet, 1);
						leaves.add(shot);
					}
					
					return leaves;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	// SQL transaction function which retries the transaction MAX_ATTEMPTS times
	// before failing
	public <ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();

		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	// TODO: Here is where you name and specify the location of your Derby SQL
	// database
	// TODO: Change it here and in SQLDemo.java under
	// CS320_LibraryExample_Lab06->edu.ycp.cs320.sqldemo
	// TODO: DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause
	// conflicts later w/Git
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:C:/cs320-spring2024/suite.db;create=true");

		// Set autocommit() to false to allow the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}
			
	private void loadSession(Session session, ResultSet resultSet, int index) throws SQLException {
		session.setSessionID(resultSet.getInt(index++));
		session.setEventID(resultSet.getInt(index++));
		session.setTime(resultSet.getString(index++));
		session.setOppType(resultSet.getString(index++));
		session.setName(resultSet.getString(index++));
		session.setScore(resultSet.getInt(index++));

	}
	
	private void loadEstablishment(Establishment establishment, ResultSet resultSet, int index) throws SQLException {
		establishment.setEstaId(resultSet.getInt(index++));
		establishment.setAccountId(resultSet.getInt(index++));
		establishment.setEstablishmentName(resultSet.getString(index++));
		establishment.setAddress(resultSet.getString(index++));
	}
	
	private void loadEvent(Event event, ResultSet resultSet, int index) throws SQLException {
		//event_id, acc_id, estb_id, name, time,type, standing
		event.setEventID(resultSet.getInt(index++));
		event.setAccount(resultSet.getInt(index++));
		event.setEstbID(resultSet.getInt(index++));
		event.setName(resultSet.getString(index++));
		event.setTime(resultSet.getString(index++));
		event.setType(resultSet.getString(index++));
		event.setStanding(resultSet.getInt(index++));

	}

	private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
		account.setAccountId(resultSet.getInt(index++));
		account.setUsername(resultSet.getString(index++));
		account.setPassword(resultSet.getString(index++));
		account.setEmail(resultSet.getString(index++));
		account.setFirstName(resultSet.getString(index++));
		account.setLastName(resultSet.getString(index++));
	}
	
	//  creates the Authors and Books tables
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			
				PreparedStatement stmt9 = null;
				PreparedStatement stmt8 = null;
				PreparedStatement stmt7 = null;
				PreparedStatement stmt6 = null;
				PreparedStatement stmt5 = null;		
				PreparedStatement stmt4 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt1 = null;
				
				

				String tablesCreated = "Tables Created: ";
			
				try {
					
					
					
					stmt8 = conn.prepareStatement(
						"create table shots ("
						+ " shot_id integer primary key"
						+ " generated always as identity (start with 1, increment by 1),"
						+ " frame_id integer,"
						+ " game_id integer,"
						+ " session_id integer,"
						+ " shot_number integer,"
						+ " count integer,"
						+ " ball_id integer,"
						+ " pins_left varchar(10),"
						+ " leave varchar(2)"
						+ ")"	
						
						//Count is an int that is the count of pins knocked down on that shot
						// pinsleft = "x", "124", "/", 
						//leave is = to "no", "s", or "w" this is so we can get whether a shot is going to be a split or washout or not
						//if spare attempt is true but leave = 's' or 'w' then we subtract those from spare attempts
						
					);
					stmt8.executeUpdate();
					tablesCreated += "Shots, ";
					
					stmt7 = conn.prepareStatement(
							"create table frames ("+
							" frame_id integer primary key "+
							" generated always as identity (start with 1, increment by 1), "+
							" game_id integer,"
							+ " score integer, "
							+ " frame_number integer"
							+ ")"
					);
					stmt7.executeUpdate();
					tablesCreated += "Frames, ";
					
					stmt6 = conn.prepareStatement(
							"create table events ("+
							" event_id integer primary key "+
							" generated always as identity (start with 1, increment by 1), "+
							" acc_id integer," +
							" estb_id integer,"+
							" name varchar(40),"+
							" time varchar(40),"+
							" type varchar(40),"+
							" standing integer"+
							")"
					);
					stmt6.executeUpdate();
					tablesCreated += "Events, ";
					
					stmt5 = conn.prepareStatement(
							"create table games (" +
							"  game_id integer primary key " +
							"    generated always as identity (start with 1, increment by 1), " +
							"  session_id integer, " +
							"  currentLane integer, " +
							"  gameNumber integer," +
							"  score integer" +
							")"
					);
					stmt5.executeUpdate();
					tablesCreated += "Games, ";
					
					stmt4 = conn.prepareStatement(
							"create table accounts (" +
							"  account_id integer primary key " +
							"    generated always as identity (start with 1, increment by 1), " +
							"  username varchar(70), " +
							"  password varchar(25), " +
							"  email varchar(70)," +
							"  firstname varchar(70)," +
							"  lastname varchar(70)" +
							")"
					);
 					stmt4.executeUpdate();
										
 					tablesCreated += "Accounts, ";
				
					stmt3 = conn.prepareStatement(
							"create table sessions (" +
							" session_id integer primary key "+
							" generated always as identity (start with 1, increment by 1), " +
							" event_id integer," +
							" time varchar(25)," +
							" oppType varchar(30)," +
							" oppName varchar(50)," +
							" score integer" +
							")"
					);
					stmt3.executeUpdate();
					
					tablesCreated += "Sessions, ";
					
					stmt2 = conn.prepareStatement(
							"create table balls (" +
							"  ball_id integer primary key " +
							"  generated always as identity (start with 1, increment by 1), " +
							"  account_id integer," + 
							"  weight float, " +
							"  name varchar(70)," +
							"  righthand boolean, " +
							"  brand varchar(70)," +
							"  color1 varchar(15)," +
							"  color2 varchar(15)," +
							"  color3 varchar(15)," +
							"  material varchar(70)" +
							")"//weight, name, righthand, brand, color
					);

					stmt2.executeUpdate();
										
					tablesCreated += "Balls, ";
					
					stmt1 = conn.prepareStatement(
							"create table establishments (" +
							"  esta_id integer primary key " +
							"  generated always as identity (start with 1, increment by 1), " +
							"  account_id integer," + 
							"  name varchar(70)," +
							"  address varchar(70)" +
							")"//EstaId, accountId, establishmentName, address
					);

					stmt1.executeUpdate();
										
					tablesCreated += "Establishments, ";
					
					
					System.out.println(tablesCreated);
					
					return true;
				} finally {
					
					// Ah, perfection
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);
					DBUtil.closeQuietly(stmt8);

				}
			}
		});
	}
	
	// loads data retrieved from CSV files into DB tables in batch mode
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				/*
				 * List<Author> authorList; List<Book> bookList; List<BookAuthor>
				 * bookAuthorList;
				 */
				List<Account> accountList;
				List<Ball> ballList;
				List<Establishment> estaList;
				List<Session> seshList;
				List<Game> gameList;
				List<Event> eventList;
				List<Frame> frameList;
				List<Shot> shotList;
				
				try {
					/*
					 * authorList = InitialData.getAuthors(); bookList = InitialData.getBooks();
					 * bookAuthorList = InitialData.getBookAuthors();
					 */
					accountList = InitialData.getAccounts();
					ballList = InitialData.getBallArsenal();
					estaList = InitialData.getEstablishments();
					seshList = InitialData.getSessions();
					gameList = InitialData.getGames();
					
					for(Establishment esta : estaList) {
						System.out.println(esta.getEstablishmentName());
					}
					
					eventList = InitialData.getEvents();
					frameList = InitialData.getFrames();
					shotList = InitialData.getShots();

				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertAccount = null;
				PreparedStatement insertBall = null;
				PreparedStatement insertEstablishment = null;
				PreparedStatement insertSession = null;
				PreparedStatement insertGame = null;
				PreparedStatement insertEvent = null;
				PreparedStatement insertFrame = null;
				PreparedStatement insertShot = null;

				String tablesPopulated = "Tables Populated: ";
				
				try {
					insertShot = conn.prepareStatement("insert into shots (frame_id, game_id, session_id, shot_number, count, ball_id, pins_left, leave) values (?, ?, ?, ?, ?, ?, ?, ?)");
					for(Shot shot : shotList)
					{
						insertShot.setInt(1, shot.getFrameID());
						insertShot.setInt(2, shot.getGameID());
						insertShot.setInt(3, shot.getSessionID());
						insertShot.setInt(4, shot.getShotNumber());
						insertShot.setInt(5, shot.getCount());
						insertShot.setInt(6, shot.getBallID());
						insertShot.setString(7, shot.getPinsLeft());
						insertShot.setString(8, shot.getLeave());
						insertShot.addBatch();
					}
					//insertNewShotWithFrameID(1, 1, 1, 1, "4", 6, "1234");
					
					insertShot.executeBatch();
					tablesPopulated += "Shots, ";
					
					insertFrame = conn.prepareStatement("insert into frames (game_id, score, frame_number) values (?, ?, ?)");
					for(Frame frame : frameList)
					{
						insertFrame.setInt(1, frame.getGameID());
						insertFrame.setInt(2, frame.getScore());
						insertFrame.setInt(3, frame.getFrameNumber());
						insertFrame.addBatch();
					}
					insertFrame.executeBatch();
					tablesPopulated += "Frames, ";
					
					insertEvent = conn.prepareStatement("insert into events (acc_id, estb_id, name, time, type, standing) values (?,?, ?, ?, ?, ?)");
					for(Event event : eventList)
					{
						insertEvent.setInt(1, event.getAccount());
						insertEvent.setInt(2, event.getEstbID());
						insertEvent.setString(3, event.getEventName());
						insertEvent.setString(4, event.getTime());
						insertEvent.setString(5, event.getType());
						insertEvent.setInt(6, event.getStanding());
						insertEvent.addBatch();
					}
					insertEvent.executeBatch();
					tablesPopulated += "Events, ";
					
					insertAccount = conn.prepareStatement("insert into accounts (username, password, email, firstname, lastname) values (?, ?, ?, ?, ?)");
					for (Account account : accountList)
					{
						insertAccount.setString(1, account.getUsername());
						insertAccount.setString(2, account.getPassword());
						insertAccount.setString(3, account.getEmail());
						insertAccount.setString(4, account.getFirstName());
						insertAccount.setString(5, account.getLastName());
						insertAccount.addBatch();
					}
//					insertNewAccountinDB("email@gmail.com", "password1", "username1");
//					
//					
//					
//					 List<Account> testList = new ArrayList<Account>(); testList =
//					 getAccountByUsernameAndPassword("username1", "password1"); 
//					 Account temp = testList.get(0);
//					 System.out.println("Found account with id: "
//					 +temp.getAccountId()
//					 +" and username: "+temp.getUsername()
//					 +" and password: "+temp.getPassword()
//					 +" and email: "+temp.getEmail());
//					 
//					
					insertAccount.executeBatch();
					tablesPopulated += "Accounts, ";

					insertGame = conn.prepareStatement("insert into games (session_id, currentLane, gameNumber, score) values (?, ?, ?, ?)");
					for (Game game : gameList)
					{
						insertGame.setInt(1, game.getSessionID());
						insertGame.setInt(2, game.getLane());
						insertGame.setInt(3, game.getGameNumber());
						insertGame.setInt(4, game.getScore());
						insertGame.addBatch();
					}
					insertGame.executeBatch();
					
					tablesPopulated += "Games, ";
					
					insertSession = conn.prepareStatement("insert into sessions (event_id, time, oppType, oppName, score) values (?, ?, ?, ?, ?)");
					for (Session session : seshList)
					{
						insertSession.setInt(1, session.getEventID());
						insertSession.setString(2, session.getTime());
						insertSession.setString(3, session.getOppType());
						insertSession.setString(4, session.getName());
						insertSession.setInt(5, session.getScore());
						insertSession.addBatch();
					}
					insertSession.executeBatch();
					
					tablesPopulated += "Sessions, ";

					
					insertBall= conn.prepareStatement("insert into balls (account_id, weight, name, righthand, brand, color1, color2, color3, material)" 
							+ 	" values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Ball ball : ballList)
					
					{
						insertBall.setInt(1, ball.getAccountId());					//ball id, accountid, weight, name, righthand, brand, color1, color2, color3, material
						insertBall.setFloat(2, ball.getWeight());
						insertBall.setString(3, ball.getName());
						insertBall.setBoolean(4, ball.getRightHanded());
						insertBall.setString(5, ball.getBrand());
						insertBall.setString(6, ball.getColor1());
						insertBall.setString(7, ball.getColor2());
						insertBall.setString(8, ball.getColor3());
						insertBall.setString(9, ball.getMaterial());
						insertBall.addBatch();
					}
					//System.out.println("Balls table populated");
					//testing the getAccountByEmail function
//					List<Account> testListForEmail= new ArrayList<Account>();
//					testListForEmail = getAccountByEmail("email@gmail.com");
//					temp = testList.get(0);
//					System.out.println("Test email should be email@gmail.com --> "+temp.getEmail());
					
//					insertNewBallInDB(1, (float)40.5, "Bowling Master", true, "Rolex", "Gold");
//					
//					List<Ball> ballListTest = getBallsByAccountIdFromDB(1);
//					for(Ball ball: ballListTest) {
//						System.out.println(ball.getName());
//
//					}
//								
//					
					insertBall.executeBatch();
					tablesPopulated += "Balls, ";
					
					insertEstablishment = conn.prepareStatement("insert into establishments (account_id, name, address) values (?, ?, ?)");
					for (Establishment establishment : estaList)
					
					{
						insertEstablishment.setInt(1, establishment.getAccountId());	//				//ball id, accountid, weight, name, righthand, brand, color
						insertEstablishment.setString(2, establishment.getEstablishmentName());
						insertEstablishment.setString(3, establishment.getAddress());
						insertEstablishment.addBatch();
					}
					insertEstablishment.executeBatch();
					tablesPopulated += "Establishments, ";
					
					System.out.println(tablesPopulated);
					
					return true;
				} finally {			
					DBUtil.closeQuietly(insertAccount);					
					DBUtil.closeQuietly(insertBall);
					DBUtil.closeQuietly(insertSession);
					DBUtil.closeQuietly(insertGame);
					
					DBUtil.closeQuietly(insertEstablishment);
					DBUtil.closeQuietly(insertEvent);
					DBUtil.closeQuietly(insertFrame);
					DBUtil.closeQuietly(insertShot);
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Suite DB successfully initialized!");
		
		
		
	}

	@Override
	public List<Ball> getBallByBallID(int ballID) {
		return executeTransaction(new Transaction<List<Ball>>() {
			@Override
			public List<Ball> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt1 = null;
				
				ResultSet resultSet1 = null;
				
				try
				{
					stmt1 = conn.prepareStatement(
						"select * from balls"+
						"  where balls.ball_id = ? "
					);
					stmt1.setInt(1, ballID);
					
					List<Ball> result = new ArrayList<Ball>();
					
					resultSet1 = stmt1.executeQuery();
					
					Boolean found = false;
					
					while(resultSet1.next())
					{
						found = true;
						Ball ball = new Ball(0,0,"", true,"","", "","","");
						loadBall(ball, resultSet1, 1);
						
						result.add(ball);
					}
					
					return result;
				} 
				finally
				{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(resultSet1);
				}
			}
		});
	}
}
