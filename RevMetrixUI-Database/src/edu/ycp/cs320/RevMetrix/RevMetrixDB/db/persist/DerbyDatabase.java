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
	public List<Shot> findAllShots() {
//		return executeTransaction(new Transaction<List<Shot>>() {
//			@Override
//			public List<Shot> execute(Connection conn) throws SQLException
//			{
//				PreparedStatement stmt1 = null;
//				ResultSet resultSet1 = null;
//				
//				try
//				{
//					stmt1 = conn.prepareStatement(
//							"select * "
//					);
//				}
//			}
//		});
		return null;
	}
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
						"  where accounts.username = ? "+
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
						Account acc = new Account("", "", "");
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
						Account acc = new Account("", "", "");
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
						Account acc = new Account("", "", "");
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
	private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
		account.setAccountId(resultSet.getInt(index++));
		account.setUsername(resultSet.getString(index++));
		account.setPassword(resultSet.getString(index++));
		account.setEmail(resultSet.getString(index++));
	}
	
	private void loadBall(Ball ball, ResultSet resultSet, int index) throws SQLException {
		ball.setBallId(resultSet.getInt(index++));
		ball.setAccountId(resultSet.getInt(index++));
		ball.setWeight(resultSet.getInt(index++));
		ball.setName(resultSet.getString(index++));
		ball.setRightHanded(resultSet.getBoolean(index++));
		ball.setBrand(resultSet.getString(index++));
		ball.setColor(resultSet.getString(index++));
	}
	
	@Override
	public Integer insertNewBallInDB(int account_id, float weight, String name, Boolean righthand, String brand, String color) {
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

						System.out.println("Ball <"+ name +"> found with brand <"+ brand +"> and with acc_id: "+account_id + "and ball id: "+ball_id);
					}
					else 
					{
						System.out.println("Ball <"+ name +"> with brand <"+ brand +"> was not found");
					}
					if(ball_id <= 0)
					{
						
						stmt2 = conn.prepareStatement(
								"insert into balls (account_id, weight, name, righthand, brand, color)"
								+ " values (?, ?, ?, ?, ?, ?)"
						);
						//get current account_id
						
						
						stmt2.setInt(1, account_id);	//				//ball id, accountid, weight, name, righthand, brand, color
						stmt2.setFloat(2, weight);
						stmt2.setString(3, name);
						stmt2.setBoolean(4, righthand);
						stmt2.setString(5, brand);
						stmt2.setString(6,color);
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
									" isRightHanded: " +righthand+ " brand: "+brand+" color: "+color);
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
	public Integer insertNewShotWithFrameID(int sessionID, int gameID, int frameID, int shotNumber, String count, int ballID,
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
					stmt1 = conn.prepareStatement(
							"select shot_id from shots"
							+ " where frame_id = ? "
					);
					
					stmt1.setInt(1, frameID);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						shot_id = resultSet1.getInt(1);
						System.out.println("Shot <"+ shot_id +"> found with frameID <"+ frameID +">");
					}
					else 
					{
						System.out.println("Shot <"+ shot_id +"> was not found");
					}
					if(shot_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into shots (frame_id, game_id, session_id, shot_number, count, ball_id, pins_left) values (?, ?, ?, ?, ?, ?, ?) "
						);
						stmt2.setInt(1, frameID);
						stmt2.setInt(2, gameID);
						stmt2.setInt(3, sessionID);
						stmt2.setInt(4, shotNumber);
						stmt2.setString(5, count);
						stmt2.setInt(6, ballID);
						stmt2.setString(7, pinsLeft);
						
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
							+ " where game_id = ? "
					);
					
					stmt1.setInt(1, gameID);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						frame_id = resultSet1.getInt(1);
						System.out.println("Frame <"+ frame_id +"> found with gameID <"+ gameID +">");
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
								+ " where game_id = ? and frame_id = ?"
						);
						stmt3.setInt(1, gameID);
						stmt3.setInt(2, frame_id);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							frame_id = resultSet3.getInt(1);
							System.out.println("New frame <"+frame_id+"> , <"+gameID+" ID: "+frame_id);
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
	public Integer insertNewGame(final int gameID, final int sessionID, final int currentLane, final int gameNum, final int score )
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
					stmt1 = conn.prepareStatement(
							"select game_id from games"
							+ " where session_id = ? "
					);
					
					stmt1.setInt(1, sessionID);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						game_id = resultSet1.getInt(1);
						System.out.println("Game <"+ game_id +"> found with sessionID <"+ sessionID +">");
					}
					else 
					{
						System.out.println("game <"+ game_id +"> was not found");
					}
					if(game_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into games (sessionID, currentLane, gameNumber, score) "
								+ " values(?, ?, ?, ?, ?)"
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
								+ " where game_id = ? and session_id = ?"
						);
						stmt3.setInt(1, gameID);
						stmt3.setInt(2, sessionID);
						
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
	public Integer insertNewEvent(int eventID, int estbID, String name, int time, String type, int standing) {
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
							+ " where estb_id = ? "
					);
					
					stmt1.setInt(1, estbID);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						event_id = resultSet1.getInt(1);
						System.out.println("Event <"+ eventID +"> found with estbID <"+ estbID +">");
					}
					else 
					{
						System.out.println("Event <"+ eventID +"> was not found");
					}
					if(event_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into events (estb_id, name, time, type, standing) "
								+ " values(?, ?, ?, ?, ?)"
						);
						stmt2.setInt(1, estbID);
						stmt2.setString(2, name);
						stmt2.setInt(3, time);
						stmt2.setString(4, type);
						stmt2.setInt(5, standing);
						
						stmt2.executeUpdate();
						
						System.out.println("New event <"+name+"> , <"+time+"> , <"+type+">, <"+standing+"> inserted into games");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select * from events "
								+ " where event_id = ? and estb_id = ?"
						);
						stmt3.setInt(1, eventID);
						stmt3.setInt(2, estbID);
						
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
	public Integer insertNewAccountinDB(final String email, final String password, final String username)
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
								"insert into accounts (username, email, password) "
								+ " values(?, ?, ?)"
						);
						stmt2.setString(1, username);
						stmt2.setString(2, email);
						stmt2.setString(3, password);
						
						stmt2.executeUpdate();
						
						System.out.println("New account <"+email+"> , <"+username+"> , <"+password+"> inserted into accounts");
						
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
	public Integer insertNewSession(final int sessionID,final int eventID,final String time,final String oppType,final String oppName,final int score) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;	
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;
				
				
				Integer session_id = -1;
				
				// try to find account_id in db
				try
				{
					stmt1 = conn.prepareStatement(
							"select session_id from sessions"
							+ " where event_id = ?"
					);
					
					stmt1.setInt(1, eventID);
					
					resultSet1 = stmt1.executeQuery();
					
					if(resultSet1.next())
					{
						session_id = resultSet1.getInt(1);
						System.out.println("Session <"+ sessionID +"> found with eventID <"+ eventID +">");
					}
					else 
					{
						System.out.println("Session <"+ sessionID +"> was not found");
					}
					if(session_id <= 0)
					{
						stmt2 = conn.prepareStatement(
								"insert into sessions (eventID, time, oppType, oppName, score) "
								+ " values(?, ?, ?, ?, ?)"
						);
						stmt2.setInt(1, eventID);
						stmt2.setString(2, time);
						stmt2.setString(3, oppType);
						stmt2.setString(4, oppName);
						stmt2.setInt(5, score);
						
						stmt2.executeUpdate();
						
						System.out.println("New session <"+eventID+"> , <"+time+"> , <"+oppType+"> , <"+oppName+">, <"+score+"> inserted into sessoins");
						
						// get the new account_id
						stmt3 = conn.prepareStatement(
								"select * from sessions "
								+ " where event_id = ? and session_id = ?"
						);
						stmt3.setInt(1, eventID);
						stmt3.setInt(2, sessionID);
						
						resultSet3 = stmt3.executeQuery();
						
						if (resultSet3.next())
						{
							session_id = resultSet3.getInt(1);
							System.out.println("New session  <"+eventID+"> , <"+time+"> , <"+oppType+"> , <"+oppName+">, <"+score+"> ID:"+session_id);
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
	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	// SQL transaction function which retries the transaction MAX_ATTEMPTS times before failing
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
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

	// TODO: Here is where you name and specify the location of your Derby SQL database
	// TODO: Change it here and in SQLDemo.java under CS320_LibraryExample_Lab06->edu.ycp.cs320.sqldemo
	// TODO: DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause conflicts later w/Git
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:C:/cs320-spring2024/suite.db;create=true");		
		
		// Set autocommit() to false to allow the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	
	//  creates the Authors and Books tables
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			
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
						+ " count varchar(1),"
						+ " ball_id integer,"
						+ " pins_left varchar(10)"
						+ ")"	
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
					
					stmt3 = conn.prepareStatement(
							"create table events ("+
							" event_id integer primary key "+
							" generated always as identity (start with 1, increment by 1), "+
							" estb_id integer,"+
							" name varchar(40),"+
							" time integer,"+
							" type varchar(40),"+
							" standing integer"+
							")"
					);
					stmt3.executeUpdate();
					tablesCreated += "Events, ";
					
					stmt1 = conn.prepareStatement(
							"create table games (" +
							"  game_id integer primary key " +
							"    generated always as identity (start with 1, increment by 1), " +
							"  session_id integer, " +
							"  currentLane integer, " +
							"  gameNumber integer," +
							"  score integer" +
							")"
					);
					stmt1.executeUpdate();
					tablesCreated += "Games, ";
					
					stmt4 = conn.prepareStatement(
							"create table accounts (" +
							"  account_id integer primary key " +
							"    generated always as identity (start with 1, increment by 1), " +
							"  username varchar(70), " +
							"  password varchar(25), " +
							"  email varchar(70)" +
							")"
					);
 					stmt4.executeUpdate();
										
 					tablesCreated += "Accounts, ";
				
					stmt2 = conn.prepareStatement(
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
					stmt2.executeUpdate();
					
					tablesCreated += "Sessions, ";
					
					stmt5 = conn.prepareStatement(
							"create table balls (" +
							"  ball_id integer primary key " +
							"  generated always as identity (start with 1, increment by 1), " +
							"  account_id integer," + 
							"  weight float, " +
							"  name varchar(70)," +
							"  righthand boolean, " +
							"  brand varchar(70)," +
							"  color varchar(70)" +
							")"//weight, name, righthand, brand, color
					);

					stmt5.executeUpdate();
										
					tablesCreated += "Balls, ";
					
					stmt6 = conn.prepareStatement(
							"create table establishments (" +
							"  esta_id integer primary key " +
							"  generated always as identity (start with 1, increment by 1), " +
							"  account_id integer," + 
							"  name varchar(70)," +
							"  address varchar(70)" +
							")"//EstaId, accountId, establishmentName, address
					);

					stmt6.executeUpdate();
										
					tablesCreated += "Establishments, ";
					System.out.println(tablesCreated);
					
					return true;
				} finally {
					
					// Ah, perfection
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
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
					insertShot = conn.prepareStatement("insert into shots (frame_id, game_id, session_id, shot_number, count, ball_id, pins_left) values (?, ?, ?, ?, ?, ?, ?)");
					for(Shot shot : shotList)
					{
						insertShot.setInt(1, shot.getFrameID());
						insertShot.setInt(2, shot.getGameID());
						insertShot.setInt(3, shot.getSessionID());
						insertShot.setInt(4, shot.getShotNumber());
						insertShot.setString(5, shot.getCount());
						insertShot.setInt(6, shot.getBallID());
						insertShot.setString(7, shot.getPinsLeft());
						insertShot.addBatch();
					}
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
					
					insertEvent = conn.prepareStatement("insert into events (estb_id, name, time, type, standing) values (?, ?, ?, ?, ?)");
					for(Event event : eventList)
					{
						insertEvent.setInt(1, event.getEstbID());
						insertEvent.setString(2, event.getEventName());
						insertEvent.setInt(3, event.getTime());
						insertEvent.setString(4, event.getType());
						insertEvent.setInt(5, event.getStanding());
						insertEvent.addBatch();
					}
					insertEvent.executeBatch();
					tablesPopulated += "Events, ";
					
					insertAccount = conn.prepareStatement("insert into accounts (username, password, email) values (?, ?, ?)");
					for (Account account : accountList)
					{
						insertAccount.setString(1, account.getUsername());
						insertAccount.setString(2, account.getPassword());
						insertAccount.setString(3, account.getEmail());
						insertAccount.addBatch();
					}
					insertNewAccountinDB("email@gmail.com", "password1", "username1");
					
					
					
					 List<Account> testList = new ArrayList<Account>(); testList =
					 getAccountByUsernameAndPassword("username1", "password1"); 
					 Account temp = testList.get(0);
					 System.out.println("Found account with id: "
					 +temp.getAccountId()
					 +" and username: "+temp.getUsername()
					 +" and password: "+temp.getPassword()
					 +" and email: "+temp.getEmail());
					 
					
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
						insertSession.setString(4, session.getOpponent());
						insertSession.setInt(5, session.getScore());
						insertSession.addBatch();
					}
					insertSession.executeBatch();
					
					tablesPopulated += "Sessions, ";

					
					insertBall= conn.prepareStatement("insert into balls (account_id, weight, name, righthand, brand, color) values (?, ?, ?, ?, ?, ?)");
					for (Ball ball : ballList)
					
					{
						insertBall.setInt(1, ball.getAccountId());					//ball id, accountid, weight, name, righthand, brand, color
						insertBall.setFloat(2, ball.getWeight());
						insertBall.setString(3, ball.getName());
						insertBall.setBoolean(4, ball.getRightHanded());
						insertBall.setString(5, ball.getBrand());
						insertBall.setString(6, ball.getColor());
						insertBall.addBatch();
					}
								
					
					insertBall.executeBatch();
					tablesPopulated += "Balls, ";
					
					insertEstablishment= conn.prepareStatement("insert into establishments (account_id, name, address) values (?, ?, ?)");
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
					DBUtil.closeQuietly(insertEstablishment);					
					DBUtil.closeQuietly(insertSession);
					DBUtil.closeQuietly(insertGame);
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
	
}
