package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.session.Session;

import edu.ycp.cs320.RevMetrix.controller.BallArsenalController;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;
import edu.ycp.cs320.RevMetrix.model.Game;

public class BallArsenalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }
		
		System.out.println("BallArsenal Servlet: doGet");	
		
		//BallArsenalController controller = new BallArsenalController();
		// Get session creation time.
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		   
		// Get last access time of this Webpage.
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");

		   // Check if this is new comer on your Webpage.
		String ballArsenalKey = new String("ballArsenalKey");
		BallArsenal model = (BallArsenal)session.getAttribute(ballArsenalKey);

		// If first visit: new session id
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);
		  session.setAttribute(ballArsenalKey,  model);
		} 
		//Get model and userID from jsp
		userID = (String)session.getAttribute(userIDKey);

		//controller.setModel(model);
		
		if(model == null) {
			model = new BallArsenal();
			session.setAttribute(ballArsenalKey, model);
		}
		ArrayList<Ball> balls = model.getBalls();
        balls = model.getBalls();
        
		// Set the ArrayList as a request attribute
		req.setAttribute("balls", balls);
		session.setAttribute(ballArsenalKey, model); //update session model

		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/ballArsenal.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("BallArsenal Servlet: doPost");
		
		String errorMessage = null;

		BallArsenal model = new BallArsenal();
		BallArsenalController controller = new BallArsenalController();
				
		// Get session creation time.
				HttpSession session = req.getSession();
			    long createTime = session.getCreationTime();
				   
				// Get last access time of this Webpage.
				long lastAccessTime = session.getLastAccessedTime();
				String userIDKey = new String("userID");
				String userID = new String("ABCD");

				String ballArsenalKey = new String("ballArsenalKey");

				   // Check if this is new comer on your Webpage.
				if (session.isNew() ){
			      session.setAttribute(userIDKey, userID);
				  session.setAttribute(ballArsenalKey,  model);
				} 
				model = (BallArsenal)session.getAttribute(ballArsenalKey);
				userID = (String)session.getAttribute(userIDKey);
				
				//end session shenanigans
		Account acc =  (Account)session.getAttribute("currAccount");
		session.setAttribute("currAccount", acc);
		controller.setModel(model);
        ArrayList<Ball> balls = model.getBalls(); //get ball ArrayList from session updated model
		if(balls == null) {
			balls = new ArrayList<Ball>();
			balls.add(new Ball("FirstBall"));
		}
		//on button press
		String newBallName = req.getParameter("ballName");
		String removeBallName = req.getParameter("removeBallName");
		
		if (req.getParameter("addBall") != null ) {
			//System.out.println(model.getBallAtIndex(0).getName());
			controller.addBall(newBallName);
		}
		if(req.getParameter("removeBall") != null) {
			//controller.removeBall(removeBallName);
			 Iterator<Ball> iterator = balls.iterator();
			    while (iterator.hasNext()) {
			        Ball ball = iterator.next();
			        if (ball.getName().equals(removeBallName)) {
			            iterator.remove(); // Remove the ball from the ArrayList
			            break; // Exit the loop after removing the ball
			        }
			    }
		}
		if (session.getAttribute("currentGame") != null ) {
			Game g = (Game)session.getAttribute("currentGame");
			System.out.println(g.getLane());
		}
		String selectedBall = req.getParameter("selectedBall");
		
		try {
			if (selectedBall !=  null)
			{
				String tempBallName = selectedBall;
				Ball tempBall = new Ball(tempBallName);
				System.out.println(tempBallName);
				acc.setCurrentBall(tempBall);
			}
			System.out.println("Account Current Ball: " + acc.getCurrentBall().getName());
		} catch(NullPointerException e)
		{
			System.out.println("you are dumb you should've figured this out by now dumbass");
		}
		
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("balls", balls);
		session.setAttribute(ballArsenalKey, model); //update session model

		req.getRequestDispatcher("/_view/ballArsenal.jsp").forward(req, resp);
		
	}
}