package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.GameControllerReal;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Game;


public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Important information: 
	//		currentGame is not being used by other functions
	//		This will be main data to implement into the database
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if(!AccountServlet.validLogin()) {
			req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
		}
		System.out.println("Game Servlet: doGet");	
		HttpSession session = req.getSession();
	  
		Account acc = (Account) session.getAttribute("currAccount");
		System.out.print(acc.getAccountId());
		GameControllerReal controller = new GameControllerReal();
		
		List<Game> games = new ArrayList<Game>();
		games = controller.getGames();
		
		for(Game game : games)
		{
			System.out.println(game.getGameID());
		}
		
		req.setAttribute("model", games);
		
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Game Servlet: doPost");
		
		String errorMessage = null;
		HttpSession session = req.getSession();
	    
		Account acc = (Account) session.getAttribute("currAccount");
		System.out.print(acc.getAccountId());
		Game model = new Game(0,0,0,0,0);
		GameControllerReal controller = new GameControllerReal();
		controller.setModel(model);
		
		List<Game> games = controller.getGames();
		if(games == null)
		{
			games = new ArrayList<Game>();
			games.add(new Game(0, 0, 0, 0, 0));
		}
		
		

		   // Check if this is new comer on your Webpage.
		
		String gamesListKey = new String("gamesListKey");
	//	ArrayList<Game> games = (ArrayList<Game>)session.getAttribute(gamesListKey);
		
		// If first visit: new session id
		if (session.isNew() ){
		  //initialize the games list with 3 games
		  games.add(new Game(1, 1, 14, 2, 3));
		  games.add(new Game(1, 2, 22, 3, 4));
		  games.add(new Game(1, 3, 4, 4, 5));
		  session.setAttribute(gamesListKey, games);
		  
		} 
		if(games == null) {
			games = new ArrayList<Game>();
			
			games.add(new Game(1, 1, 14, 2, 3));
			games.add(new Game(1, 2, 22, 3, 4));
			games.add(new Game(1, 3, 4, 4, 5));
			session.setAttribute(gamesListKey, games);
		}
		//Get model and userID from jsp
		
		games = (ArrayList<Game>)session.getAttribute(gamesListKey);
		//controller.setModel(model);
//		if(games != null) {
//			for(Game g: games) {
//				System.out.println(g.getLane());
//			}
//		}
		
        
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
        String dropDownValue = req.getParameter("gameDropDown");
      //  Integer selectedIndex = Integer.parseInt(dropdownValue);
        //Make a new game and add it to game list
        if(req.getParameter("select") != null) {
        	//currentGame = games.get(selectedIndex);

        	currentGame = games.get(Integer.parseInt(dropDownValue));
           // System.out.println("Game at index: x" +" selected: " + dropDownValue);
        	System.out.println(currentGame.getLane());
        	
        	req.setAttribute("gameObjArr", games);
    		session.setAttribute(gamesListKey, games);
    		session.setAttribute("currentGame", currentGame);
    		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);

        }
        if(req.getParameter("new") != null) {
        	//currentGame = selected game from dropdown
        	
        	currentGame = games.get(games.size()-1);
        	
        	req.setAttribute("gameObjArr", games);
    		session.setAttribute(gamesListKey, games);
    		session.setAttribute("currentGame", currentGame);
    		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);

        }
        
        
        req.setAttribute("gameObjArr", games);
		session.setAttribute(gamesListKey, games);
		session.setAttribute("currentGame", currentGame);
		//Account acc = (Account)session.getAttribute("currAccount");
		acc.setCurrentGame((Game)session.getAttribute("currentGame"));
		session.setAttribute("currAccount", acc);

		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
	
}
