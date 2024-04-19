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
import edu.ycp.cs320.RevMetrix.model.Session;



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
	public Integer insertNewSession(int sessionID, int eventID, String time, String oppType, String oppName, int score) {
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
							+ " where session_id = ? and event_id = ?"
					);
					
					stmt1.setInt(1, sessionID);
					stmt1.setInt(2, eventID);
					
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

				
				// try to find account_id in db
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
						
						System.out.println("New ball inserted. ball_id: "+ ball_id + 
								" account_id: "+account_id+" weight: "+weight+" name: "+name+
								"isRightHanded: " +righthand+ " brand: "+brand+" color: "+color);
						
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

							System.out.println("Ball <"+ name +"> found with brand <"+ brand +"> and with acc_id: "+account_id + "and ball id: "+ball_id);
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
						
				PreparedStatement stmt4 = null;
				PreparedStatement stmt2 = null;
				
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;


			
				try { 
					
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
										
					System.out.println("Accounts table created");
				
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
					
					System.out.println("Sessions table created");
					
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
										
					System.out.println("Balls table created");
					
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
										
					System.out.println("Establishment table created");
					return true;
				} finally {
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);


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
				
				try {
					/*
					 * authorList = InitialData.getAuthors(); bookList = InitialData.getBooks();
					 * bookAuthorList = InitialData.getBookAuthors();
					 */
					accountList = InitialData.getAccounts();
					ballList = InitialData.getBallArsenal();
					estaList = InitialData.getEstablishments();
					seshList = InitialData.getSessions();

				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertAuthor     = null;
				PreparedStatement insertBook       = null;
				PreparedStatement insertBookAuthor = null;
				PreparedStatement insertAccount = null;
				PreparedStatement insertBall = null;
				PreparedStatement insertEstablishment = null;
				PreparedStatement insertSession = null;



				try {
					
					
					insertAccount = conn.prepareStatement("insert into accounts (username, password, email) values (?, ?, ?)");
					for (Account account : accountList)
					{
						insertAccount.setString(1, account.getUsername());
						insertAccount.setString(2, account.getPassword());
						insertAccount.setString(3, account.getEmail());
						insertAccount.addBatch();
					}
					insertAccount.executeBatch();
					
					System.out.println("Account table populated");

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
					
					System.out.println("Sessions table populated");

					
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
					System.out.println("Balls table populated");
					
					insertEstablishment= conn.prepareStatement("insert into establishments (account_id, name, address) values (?, ?, ?)");
					for (Establishment establishment : estaList)
					

					{
						insertEstablishment.setInt(1, establishment.getAccountId());	//				//ball id, accountid, weight, name, righthand, brand, color
						insertEstablishment.setString(2, establishment.getEstablishmentName());
						insertEstablishment.setString(3, establishment.getAddress());
						insertEstablishment.addBatch();


					}
					
					insertEstablishment.executeBatch();
					System.out.println("Establishment table populated");
					
					
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertBook);
					DBUtil.closeQuietly(insertAuthor);
					DBUtil.closeQuietly(insertBookAuthor);					
					DBUtil.closeQuietly(insertAccount);					
					DBUtil.closeQuietly(insertBall);
					DBUtil.closeQuietly(insertEstablishment);					
					DBUtil.closeQuietly(insertSession);
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
