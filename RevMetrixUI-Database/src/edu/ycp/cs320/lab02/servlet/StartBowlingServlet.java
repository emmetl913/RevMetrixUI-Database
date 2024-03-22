package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import edu.ycp.cs320.lab02.controller.BowlingController;
import edu.ycp.cs320.lab02.model.BallArsenal;
import edu.ycp.cs320.lab02.model.Bowling;
import edu.ycp.cs320.lab02.model.Game;


public class StartBowlingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("StartBowling Servlet: doGet");	
		
	
		// Get session creation time.
				HttpSession session = req.getSession();
				long createTime = session.getCreationTime();
						   
				// Get last access time of this Webpage.
				long lastAccessTime = session.getLastAccessedTime();

				// Define session based objects like ArrayLists :P
				String gameArrayListKey = new String("gameArrayListKey");
				ArrayList<Game> games = new ArrayList<Game>();
				
				// Check if this is new comer on your Webpage.	
				// If first visit: new Game ArrayList
				if (session.isNew() ){
					Game game0 = new Game(0);
					Game game1 = new Game(1);
					Game game2 = new Game(2);
					games.add(game0);
					games.add(game1);
					games.add(game2);
					session.setAttribute(gameArrayListKey,  games);
				} 
				games = (ArrayList<Game>)session.getAttribute(gameArrayListKey);
				
				//Do Stuff with games ArrayList
				
				//Pass Revised Data Back into the Session
				
				session.setAttribute(gameArrayListKey,  games);
				//Pass data into the jsp as well
				req.setAttribute("games", games);

				// call JSP to generate session form
				req.getRequestDispatcher("/_view/startBowling.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("StartBowling Servlet: doPost");
		
		Bowling model = new Bowling();
		BowlingController controller = new BowlingController();
		controller.setModel(model);
		
		
		
		
		String errorMessage = null;
		
		req.getRequestDispatcher("/_view/startBowling.jsp").forward(req, resp);
	}
	
}
