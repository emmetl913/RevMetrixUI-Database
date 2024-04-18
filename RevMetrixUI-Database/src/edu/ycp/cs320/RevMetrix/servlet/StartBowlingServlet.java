package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.EventArray;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Session;

public class StartBowlingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }

		System.out.println("StartBowling Servlet: doGet");	
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		   
		// Get last access time of this Webpage.
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");

		   // Check if this is new comer on your Webpage.
		String startBowlingKey = new String("startBowlingKey");
		EventArray model = new EventArray();
		
		String gamesListKey = new String("gamesListKey");
		ArrayList<Game> games = new ArrayList<Game>();
		
		// If first visit: new session id
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);
		  session.setAttribute(startBowlingKey,  model);
		  
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
		
		
		ArrayList<Establishment> establishment = new ArrayList<>();
		Establishment e1 = new Establishment("Colony Park Lanes & Games", "1900 Pennsylvania Ave, York, PA 17404");
		
		ArrayList<Event> events = model.getEvents();
        if(model.getEvents() == null) {
        	events = new ArrayList<Event>();
        	events.add(new Event("FirstName", "FirstType", 0, e1));
		}
        else {
        	events = model.getEvents();
        }
        
        
        //Initialize a Game that will be sent out to other portions of the website (currentGame)
        Game currentGame = null;
        
        // Retrieve the value of the button clicked
        String buttonValue = req.getParameter("gameStatus");
        
        // Check which button was clicked
        if ("startNewGame".equals(buttonValue)) {
        	Game g = new Game(games.size()+1,1);
        	games.add(g); //game gets added to the end of the list //dont worry that the gameNumber will repeat.
        	//Eventually it won't because it will take database values
        	currentGame = g;
        	System.out.println(g.getGameNumber());
        }
        //Make a new game and add it to game list
        if(req.getAttribute("Start New Game") != null) {
        	
        }
        if(req.getAttribute("Selet Current Game") != null) {
        	//currentGame = selected game from dropdown
        }
		// Set the ArrayList as a request attribute
		req.setAttribute("event", events);
		session.setAttribute(startBowlingKey, model); //update session model
		
		req.setAttribute("gameObjArr", games);
		session.setAttribute(gamesListKey, games);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/startBowling.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("StartBowling Servlet: doPost");
		
		
		// Event Methods
		EventArray model = new EventArray();
		EventController controller = new EventController();
		controller.setModel(model);
		
		String type = null;
		
		if (req.getParameter("practice") != null) {
			type = "practice";
		} else if (req.getParameter("tournament") != null) {
			type = "tournament";
		} else if (req.getParameter("leauge") != null) {
			type = "leauge";
		} else {
			throw new ServletException("Unknown command");
		}
		
		
		
		ArrayList<Event> events = model.getEvents(); //get ball ArrayList from session updated model
		if(events == null) {
			
		}
		
		
		String errorMessage = null;
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("game", model);
		
		req.getRequestDispatcher("/_view/startBowling.jsp").forward(req, resp);
	}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
	
}
