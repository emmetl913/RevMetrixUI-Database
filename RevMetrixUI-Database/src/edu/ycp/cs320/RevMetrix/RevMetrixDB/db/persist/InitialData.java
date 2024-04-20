package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Establishment;
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
				Account account = new Account(temp2, temp3, temp4);

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
				
				int seshID = sessionID++;
				int eventID = Integer.parseInt(i.next());
				String dateTime = i.next();
				String oppType = i.next();
				String oppName = i.next();
				int score = Integer.parseInt(i.next());
				
				Session session = new Session(seshID, eventID, dateTime, oppType, oppName, score);
				
				sessionList.add(session);
			}
			return sessionList;
		}finally
		{
			readSessions.close();
		}
	}
	
	public static List<Ball> getBallArsenal() throws IOException
	{
		List<Ball> ballList = new ArrayList<Ball>();
		ReadCSV readBalls = new ReadCSV("ball_arsenal.csv");
		try {
			Integer accountId = 1;
			while (true) {
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
				// ball id, accountid, weight, name, righthand, brand, color
				// int temp1 = accountId++;

				// skip ball Id
				accountId = Integer.parseInt(i.next());

				String temp1 = i.next();
				float weight = Float.parseFloat(temp1);
				String name = i.next();
				String temp3 = i.next();
				boolean rightHanded = Boolean.parseBoolean(temp3);
				String brand = i.next();
				String color = i.next();
				Ball ball;
				ball = new Ball(accountId, weight, name, rightHanded, brand, color);

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