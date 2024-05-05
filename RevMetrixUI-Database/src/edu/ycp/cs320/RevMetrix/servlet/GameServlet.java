package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.FrameController;
import edu.ycp.cs320.RevMetrix.controller.GameController;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Game;


public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if(!AccountServlet.validLogin()) {
			req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
		}
		else {
		System.out.println("Game Servlet: doGet");	
		HttpSession session = req.getSession();
	   
		Account acc = (Account) session.getAttribute("currAccount");
		
		Game model = null;
		GameController controller = new GameController();
		controller.setModel(model);
		
		Integer sessionID = (Integer) session.getAttribute("sessionID");
		System.out.println("Session ID for this games page is: "+sessionID);
		List<Game> games = controller.getGameBySessionID(sessionID);
		
		req.setAttribute("games", games);
		req.setAttribute("game", model);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(!AccountServlet.validLogin()) {
			req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
		}
		System.out.println("Game Servlet: doPost");
		
		String errorMessage = null;
		
		Game currentGame = new Game(0, 0, 0, 0, 0);
		Game invalidGame = currentGame; 
		GameController controller = new GameController();
		
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("currAccount");
		Integer sessionID = (Integer) session.getAttribute("sessionID");
		String selectedGame = req.getParameter("selectedGame");
		
		List<Game> games = controller.getGameBySessionID(sessionID);
		
        
        if(selectedGame != null)
        {
        	Integer gameID = getIntegerFromParameter(req.getParameter("selectedGame"));
        	System.out.println("Game id for selected game: "+gameID);
        	session.setAttribute("gameID", gameID);
        	currentGame = controller.getGameByGameID(gameID);
        } else 
        {
        	System.out.println("We've made it to the game creation");
	        Integer laneInput = getIntegerFromParameter(req.getParameter("inputLane"));
	        int gameNumber;
            if(games == null) {
            	gameNumber = 1;
            	System.out.println("This is the first game");
            }
            else {
                gameNumber = games.size();
                System.out.println("Game number set to: "+gameNumber);
            }
	        
	        if(laneInput == null ) {
	            errorMessage = "Enter a lane";
	            System.out.println("uh oh! bad lane");
	        }
	        	
	    	if(errorMessage == null) {
		        Integer gameID = controller.insertNewGame(sessionID, laneInput, gameNumber, 0);
		        System.out.println("Game ID for successful insert: "+gameID);
		        currentGame = new Game(gameID, sessionID, laneInput, gameNumber, 0);
	        	//Initialize Game with frames
	    		FrameController fc = new FrameController();
	    		for(int i = 1; i <= 12; i++) {
	    			fc.insertNewFrame(currentGame.getGameID(), i);
	    		}
	    		
	    		session.setAttribute("currentGame", currentGame);
		        session.setAttribute("gameID", gameID);
		        acc.setCurrentGame((Game)session.getAttribute("currentGame"));
		        
	    	}
        }
        
        req.setAttribute("errorMessage", errorMessage);
        session.setAttribute("currentGame", currentGame);
        
        if(req.getParameter("submit") != null || req.getParameter("SubmitCurrentGame") != null && errorMessage == null)
		{
			System.out.println("Game submit button is pressed");
    		resp.sendRedirect(req.getContextPath() + "/shot");
		}
		else
		{
			System.out.println("Game submit failure");
			req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
		}	
	}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
	
}
