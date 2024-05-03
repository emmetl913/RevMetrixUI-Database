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
		List<Game> resultList = controller.getGameBySessionID(sessionID);
		
		String gameListKey = "gameListKey";
		
//		req.setAttribute("gameObjArr", resultList);
		session.setAttribute(gameListKey, resultList);
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
		
        String gamesListKey = new String("gamesListKey");
		List<Game> games = controller.getGameBySessionID(sessionID);
		
        
        
        String dropDownValue = req.getParameter("gameDropDown");
    
        
        if(req.getParameter("select") != null) {
        	//currentGame = games.get(selectedIndex);
        	System.out.println("You clicked the button");
        	if(dropDownValue != null || dropDownValue.equals(""))
        	{
        		currentGame = games.get(Integer.parseInt(dropDownValue));
        	}
        	if(currentGame != invalidGame) {
        	System.out.println("Selected Game ID: "+currentGame.getGameID()+" lane: " + currentGame.getLane());
        	System.out.println(currentGame.getLane());
        	
        	session.setAttribute(gamesListKey, games);
    		session.setAttribute("currentGame", currentGame);
    		acc.setCurrentGame((Game)session.getAttribute("currentGame"));
    		session.setAttribute("currAccount", acc);
    		
    	
        	req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
    		}
    		else {
    			errorMessage = "Must select a valid game";
    		}

        }
       
        if(req.getParameter("new") != null) {
        	
        	Integer laneInput = getIntegerFromParameter(req.getParameter("laneInput"));
            if(laneInput == null ) {
            	
            	errorMessage = "Enter a lane";
            }
    		if(errorMessage == null) {

    			//set current game number to proper value
    			//get num of games attached to session
                int gameNumber;
                if(games == null) {
                	gameNumber = 1;
                }
                else {
                	gameNumber = games.size();
                }
	        	int gameID = controller.insertNewGame(sessionID, laneInput, gameNumber, 0);
	        	currentGame = new Game(gameID, sessionID, laneInput, gameNumber, 0);
	        	//Initialize Game with frames
	    		FrameController fc = new FrameController();
	    		for(int i = 1; i <= 12; i++) {
	    			fc.insertNewFrame(currentGame.getGameID(), i);
	    		}
	        	
	    		session.setAttribute(gamesListKey, games);
	    		session.setAttribute("currentGame", currentGame);
	    		acc.setCurrentGame((Game)session.getAttribute("currentGame"));
	    		session.setAttribute("currAccount", acc);

        		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
    		}
        }
        
        else {
        req.setAttribute("gameObjArr", games);
		session.setAttribute("currentGame", currentGame);
		acc.setCurrentGame((Game)session.getAttribute("currentGame"));
		session.setAttribute("currAccount", acc);

		//pass error message
		req.setAttribute("errorMessage", errorMessage);	
		
		// call JSP to generate empty form
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
