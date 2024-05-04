package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.Shot;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Game;

public class InitialData {

	// reads initial Author data from CSV file and returns a List of Authors
	public static List<Account> getAccounts() throws IOException {
		List<Account> accountList = new ArrayList<Account>();
		ReadCSV readAccounts = new ReadCSV("account.csv");
		try {
			Integer accountId = 1;
			while (true) {
				List<String> tuple = readAccounts.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();

				// read author ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file				
				//Integer.parseInt(i.next());
			//	Integer.parseInt(i.next());
				// auto-generate author ID, instead
				//int temp1 = accountId++;
				String temp2 = i.next();
				String temp3 = i.next();
				String temp4 = i.next();
				String temp5 = i.next();
				String temp6 = i.next();
				Account account = new Account(temp2, temp3, temp4, temp5, temp6);

				accountList.add(account);
			}
			return accountList;
		} finally {
			readAccounts.close();
		}
	}
	public static List<Game> getGames() throws IOException
	{
		List<Game> gameList = new ArrayList<Game>();
		ReadCSV readGame = new ReadCSV("game.csv");
		try
		{
			Integer gameID = 1;
			while(true)
			{
				List<String> tuple = readGame.next();
				if(tuple == null)
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				int id = gameID++;
				int sessionID = Integer.parseInt(i.next());
				int currentLane = Integer.parseInt(i.next());
				int gameNumber = Integer.parseInt(i.next());
				int score = Integer.parseInt(i.next());
				
				Game game = new Game(id, sessionID, currentLane, gameNumber, score);
				
				gameList.add(game);
			}
			return gameList;
		}finally
		{
			readGame.close();
		}
	}
	public static List<Session> getSessions() throws IOException
	{
		List<Session> sessionList = new ArrayList<Session>();
		ReadCSV readSessions = new ReadCSV("session.csv");
		try
		{
			Integer sessionID = 1;
			while(true)
			{
				List<String> tuple = readSessions.next();
				if(tuple == null)
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				int eventID = Integer.parseInt(i.next());
				String time = i.next();
				String oppType = i.next();
				String oppName = i.next();
				int score = Integer.parseInt(i.next());
				
				Session session = new Session(sessionID++, eventID, time, oppType, oppName, score);
				
				sessionList.add(session);
			}
			return sessionList;
		}finally
		{
			readSessions.close();
		}
	}
	
	public static List<Event> getEvents() throws IOException
	{
		List<Event> eventList = new ArrayList<Event>();
		ReadCSV readEvents = new ReadCSV("event.csv");
		try
		{
			Integer eventID = 1;
			while(true)
			{
				List<String> tuple = readEvents.next();
				if (tuple == null)
				{
					break;
				}
				Iterator<String> i = tuple.iterator();
				int accID = Integer.parseInt(i.next());
				int estbID = Integer.parseInt(i.next());
				String name = i.next();
				String time = i.next();
				String type = i.next();
				int standing = Integer.parseInt(i.next());
				
				Event event = new Event(eventID++, accID, estbID, name, time, type, standing);
				
				eventList.add(event);
			}
			return eventList;
		} finally
		{
			readEvents.close();
		}
	}
	
	public static List<Frame> getFrames() throws IOException
	{
		List<Frame> frameList = new ArrayList<Frame>();
		ReadCSV readFrames = new ReadCSV("frame.csv");
		try
		{
			Integer frameID = 1;
			while(true)
			{
				List<String> tuple = readFrames.next();
				if (tuple == null)
				{
					break;
				}
				
				Iterator<String> i = tuple.iterator();
				
				
				int gameID = Integer.parseInt(i.next());
				int score = Integer.parseInt(i.next());
				int frameNumber = Integer.parseInt(i.next());
				
				Frame frame = new Frame(gameID, score, frameNumber);
				
				frameList.add(frame);
			}
			return frameList;
		} finally
		{
			readFrames.close();
		}
	}
	public static List<Shot> getShots() throws IOException
	{
		List<Shot> shotList = new ArrayList<Shot>();
		ReadCSV readShots = new ReadCSV("shot.csv");
		try
		{
			Integer shotID = 1;
			while(true)
			{
				List<String> tuple = readShots.next();
				if (tuple == null)
				{
				//	System.out.println("It broke!");
					break;
				}
				//System.out.println(tuple);
				Iterator<String> i = tuple.iterator();
				
				
				int sessionID = Integer.parseInt(i.next()); //This is in the incorrect order compared to my table on DerbyDatabase -Brandon
				int gameID = Integer.parseInt(i.next());
				int frameID = Integer.parseInt(i.next());
				int shotNumber = Integer.parseInt(i.next());
				int count = Integer.parseInt(i.next());
				int ballID = Integer.parseInt(i.next());
				String pinsLeft = i.next();
				String leave = i.next();
				
				Shot shot = new Shot(sessionID, gameID, frameID, shotNumber, count, ballID, pinsLeft, leave);
				
				shotList.add(shot);
			}
			return shotList;
		} finally
		{
			readShots.close();
		}
	}
	public static List<Ball> getBallArsenal() throws IOException
	{
		List<Ball> ballList = new ArrayList<Ball>();
		ReadCSV readBalls = new ReadCSV("ball_arsenal.csv");
		try
		{
			while(true)
			{
				List<String> tuple = readBalls.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();

				// read author ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file
				// Integer.parseInt(i.next());
				// auto-generate author ID, instead
				//ball id, accountid, weight, name, righthand, brand, color
			//	int temp1 = accountId++;
				
				//skip ball Id
				int accountId = Integer.parseInt(i.next());
				float weight = Float.parseFloat(i.next());
				String name = i.next();
				boolean rightHanded = Boolean.parseBoolean(i.next());
				String brand = i.next();
				String color1 = i.next();
				String color2 = i.next();
				String color3 = i.next();
				String material = i.next();

				Ball ball = new Ball(accountId, weight, name, rightHanded, brand, color1, color2, color3, material);
				
				ballList.add(ball);
			}
			return ballList;
		} finally {
			readBalls.close();
		}
	}

	public static List<Establishment> getEstablishments() throws IOException {
		List<Establishment> estaList = new ArrayList<Establishment>();
		ReadCSV readEstablishment = new ReadCSV("establishment.csv");
		try {
			Integer establishmentId = 1;
			while (true) {
				List<String> tuple = readEstablishment.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				System.out.println(tuple);
				// read author ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file
				// Integer.parseInt(i.next());
				// auto-generate author ID, instead
				// ball id, accountid, weight, name, righthand, brand, color
				// int temp1 = accountId++;

				// skip ball Id
				establishmentId++;
				int accountId = Integer.parseInt(i.next());
				String name = i.next();
				String address = i.next();
				Establishment establishment = new Establishment(establishmentId, accountId, name, address);

				estaList.add(establishment);
			}
			return estaList;
		} finally {
			readEstablishment.close();
		}
	}
}