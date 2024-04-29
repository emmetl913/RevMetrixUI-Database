package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.GameController;
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
		
		Game model = null;
		GameController controller = new GameController();
		controller.setModel(model);
		
		List<Game> resultList = controller.getGameBySessionID(1);
		
		String gameListKey = "gameListKey";
		
		req.setAttribute("gameObjArr", resultList);
		session.setAttribute(gameListKey, resultList);
		req.setAttribute("game", model);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Game Servlet: doPost");
		
		Game currentGame = new Game(0, 0, 0, 0, 0);
		Game model = new Game(0, 0, 0, 0, 0);
		GameController controller = new GameController();
		
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("currAccount");
		int sessionID = 0;
		
		controller.setModel(model);
        String gamesListKey = new String("gamesListKey");
		List<Game> games = controller.getGameBySessionID(0);
        // Retrieve the value of the button clicked
        String buttonValue = req.getParameter("gameStatus");
        
       
        
        Integer laneInput = getIntegerFromParameter(req.getParameter("laneInput"));
        
        if(laneInput == null) {
        	laneInput = 0;
        }
        model.setLane(laneInput);
        
        String dropDownValue = req.getParameter("gameDropDown");
      //  Integer selectedIndex = Integer.parseInt(dropdownValue);
        //Make a new game and add it to game list
        if(req.getParameter("select") != null) {
        	//currentGame = games.get(selectedIndex);
        	if(dropDownValue != null)
        	{
        		currentGame = games.get(Integer.parseInt(dropDownValue));
        	}
        	// System.out.println("Game at index: x" +" selected: " + dropDownValue);
        	System.out.println(currentGame.getLane());
        	
        	req.setAttribute("gameObjArr", games);
    		session.setAttribute(gamesListKey, games);
    		session.setAttribute("currentGame", currentGame);
    		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);

        }
        if(req.getParameter("new") != null) {
        	//currentGame = selected game from dropdown
        
        	
        	controller.insertNewGame(currentGame.getGameID(), currentGame.getSessionID(), currentGame.getLane(), currentGame.getGameNumber(), currentGame.getScore());
        	req.setAttribute("gameObjArr", games);
    		session.setAttribute(gamesListKey, games);
    		session.setAttribute("currentGame", currentGame);
    		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);

        }
        
        
        req.setAttribute("gameObjArr", games);
		session.setAttribute("currentGame", currentGame);
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
