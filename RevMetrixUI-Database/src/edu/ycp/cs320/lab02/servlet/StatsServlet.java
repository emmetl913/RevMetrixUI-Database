package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.lab02.model.Game;

public class StatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//Important information: 
	//		currentGame is not being used by other functions
	//		This will be main data to implement into the database
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Stat Servlet: doGet");	
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		   
		// Get last access time of this Webpage.
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");

		   // Check if this is new comer on your Webpage.
		
		//String gamesListKey = new String("gamesListKey");
		//ArrayList<Game> games = (ArrayList<Game>)session.getAttribute(gamesListKey);
		
		// If first visit: new session id
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);		  
		  //initialize the games list with 3 games

	      //session.setAttribute(gamesListKey, games);
		  
		} 
//		if(games == null) {
//			games = new ArrayList<Game>();
//			
//			games.add(new Game(1,14));
//			games.add(new Game(2,22));
//			games.add(new Game(3,4));
//			session.setAttribute(gamesListKey, games);
//		}
		//Get model and userID from jsp
		userID = (String)session.getAttribute(userIDKey);
		//games = (ArrayList<Game>)session.getAttribute(gamesListKey);
        
        //Initialize a Game that will be sent out to other portions of the website (currentGame)
       
        
        // Retrieve the value of the button clicked
        
        
        
//		req.setAttribute("gameObjArr", games);
//		session.setAttribute(gamesListKey, games);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/stats.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Stat Servlet: doPost");
		
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		   
		// Get last access time of this Webpage.
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");

		   // Check if this is new comer on your Webpage.
		
		String gamesListKey = new String("gamesListKey");
		ArrayList<Game> games = new ArrayList<Game>();
		
		// If first visit: new session id
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);		  
		  //initialize the games list with 3 games
		  games.add(new Game(1,14));
		  games.add(new Game(2,22));
		  games.add(new Game(3,4));
		  session.setAttribute(gamesListKey, games);
		  
		} 
		//Get model and userID from jsp
		userID = (String)session.getAttribute(userIDKey);
		games = (ArrayList<Game>)session.getAttribute(gamesListKey);
		//controller.setModel(model);
		if(games != null) {
			for(Game g: games) {
				System.out.println(g.getLane());
			}
		}
		
        
        //Initialize a Game that will be sent out to other portions of the website (currentGame)
        Game currentGame = null;
        
        // Retrieve the value of the button clicked
        String buttonValue = req.getParameter("gameStatus");
        
        // Check which button was clicked
//        if ("startNewGame".equals(buttonValue)) {
//        	Game g = new Game(games.size()+1,1);
//        	games.add(g); //game gets added to the end of the list //dont worry that the gameNumber will repeat.
//        	//Eventually it won't because it will take database values
//        	currentGame = g;
//        	System.out.println(g.getGameNumber());
//        }
        
        Integer laneInput = getIntegerFromParameter(req.getParameter("laneInput"));
        if(laneInput == null) {
        	laneInput = 0;
        }
        //Make a new game and add it to game list
        if(req.getParameter("select") != null) {
        	currentGame = games.get(0);
        }
        if(req.getParameter("new") != null) {
        	//currentGame = selected game from dropdown
        	games.add(new Game(games.size(),laneInput));
        	currentGame = games.get(games.size()-1);
        }
		req.setAttribute("gameObjArr", games);
		session.setAttribute(gamesListKey, games);
		session.setAttribute("currentGame", currentGame);
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/stats.jsp").forward(req, resp);
	}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
	
}
