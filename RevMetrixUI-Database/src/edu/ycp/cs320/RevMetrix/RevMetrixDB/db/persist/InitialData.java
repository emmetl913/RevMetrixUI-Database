package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.Game;


public class InitialData {

	// reads initial Author data from CSV file and returns a List of Authors
	public static List<Account> getAccounts() throws IOException
	{
		List<Account> accountList = new ArrayList<Account>();
		ReadCSV readAccounts = new ReadCSV("account.csv");
		try
		{
			Integer accountId = 1;
			while(true)
			{
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
		}finally {
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
				int estbID = Integer.parseInt(i.next());
				String name = i.next();
				int time = Integer.parseInt(i.next());
				String type = i.next();
				int standing = Integer.parseInt(i.next());
				
				Event event = new Event(eventID++, estbID, name, time, type, standing);
				
				eventList.add(event);
			}
			return eventList;
		} finally
		{
			readEvents.close();
		}
	}
	
	public static List<Ball> getBallArsenal() throws IOException
	{
		List<Ball> ballList = new ArrayList<Ball>();
		ReadCSV readBalls = new ReadCSV("ball_arsenal.csv");
		try
		{
			Integer ballID = 1;
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
			//	Integer.parseInt(i.next());
				// auto-generate author ID, instead
				//ball id, accountid, weight, name, righthand, brand, color
			//	int temp1 = accountId++;
				
				//skip ball Id
				int accountId = Integer.parseInt(i.next());
				float weight = Float.parseFloat(i.next());
				String name = i.next();
				boolean rightHanded = Boolean.parseBoolean(i.next());
				String brand = i.next();
				String color = i.next();
				Ball ball = new Ball(ballID++, accountId, weight, name, rightHanded, brand, color);
				
				ballList.add(ball);
			}
			return ballList;
		}finally {
			readBalls.close();
		}
	}
	
	public static List<Establishment> getEstablishments() throws IOException
	{
		List<Establishment> estaList = new ArrayList<Establishment>();
		ReadCSV readEstablishment = new ReadCSV("establishment.csv");
		try
		{
			Integer accountId = 1;
			while(true)
			{
				List<String> tuple = readEstablishment.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();

				// read author ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file				
			//	Integer.parseInt(i.next());
				// auto-generate author ID, instead
				//ball id, accountid, weight, name, righthand, brand, color
			//	int temp1 = accountId++;
				
				//skip ball Id
				int establishmentId = Integer.parseInt(i.next());
				accountId = Integer.parseInt(i.next());
				
				String name = i.next();
				String address = i.next();
				Establishment establishment;
				establishment = new Establishment(establishmentId,accountId, name, address);
				
				estaList.add(establishment);
			}
			return estaList;
		}finally {
			readEstablishment.close();
		}
	}
	
	/*
	 * public static List<Author> getAuthors() throws IOException { List<Author>
	 * authorList = new ArrayList<Author>(); ReadCSV readAuthors = new
	 * ReadCSV("authors.csv"); try { // auto-generated primary key for authors table
	 * Integer authorId = 1; while (true) { List<String> tuple = readAuthors.next();
	 * if (tuple == null) { break; } Iterator<String> i = tuple.iterator(); Author
	 * author = new Author();
	 * 
	 * // read author ID from CSV file, but don't use it // it's there for reference
	 * purposes, just make sure that it is correct // when setting up the
	 * BookAuthors CSV file Integer.parseInt(i.next()); // auto-generate author ID,
	 * instead author.setAuthorId(authorId++); author.setLastname(i.next());
	 * author.setFirstname(i.next()); authorList.add(author); }
	 * System.out.println("authorList loaded from CSV file"); return authorList; }
	 * finally { readAuthors.close(); } }
	 */
	// reads initial Book data from CSV file and returns a List of Books
	/*
	 * public static List<Book> getBooks() throws IOException { List<Book> bookList
	 * = new ArrayList<Book>(); ReadCSV readBooks = new ReadCSV("books.csv"); try {
	 * // auto-generated primary key for table books Integer bookId = 1; while
	 * (true) { List<String> tuple = readBooks.next(); if (tuple == null) { break; }
	 * Iterator<String> i = tuple.iterator(); Book book = new Book();
	 * 
	 * // read book ID from CSV file, but don't use it // it's there for reference
	 * purposes, just make sure that it is correct // when setting up the
	 * BookAuthors CSV file Integer.parseInt(i.next()); // auto-generate book ID,
	 * instead book.setBookId(bookId++); //
	 * book.setAuthorId(Integer.parseInt(i.next())); // no longer in books table
	 * book.setTitle(i.next()); book.setIsbn(i.next());
	 * book.setPublished(Integer.parseInt(i.next()));
	 * 
	 * bookList.add(book); } System.out.println("bookList loaded from CSV file");
	 * return bookList; } finally { readBooks.close(); } }
	 */
	// reads initial BookAuthor data from CSV file and returns a List of BookAuthors
	/*
	 * public static List<BookAuthor> getBookAuthors() throws IOException {
	 * List<BookAuthor> bookAuthorList = new ArrayList<BookAuthor>(); ReadCSV
	 * readBookAuthors = new ReadCSV("book_authors.csv"); try { while (true) {
	 * List<String> tuple = readBookAuthors.next(); if (tuple == null) { break; }
	 * Iterator<String> i = tuple.iterator(); BookAuthor bookAuthor = new
	 * BookAuthor(); bookAuthor.setBookId(Integer.parseInt(i.next()));
	 * bookAuthor.setAuthorId(Integer.parseInt(i.next()));
	 * bookAuthorList.add(bookAuthor); }
	 * System.out.println("bookAuthorList loaded from CSV file"); return
	 * bookAuthorList; } finally { readBookAuthors.close(); } }
	 */
}