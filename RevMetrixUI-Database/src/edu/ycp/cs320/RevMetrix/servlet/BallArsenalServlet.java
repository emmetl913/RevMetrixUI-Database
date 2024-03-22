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
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;

public class BallArsenalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

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
		BallArsenal model = new BallArsenal();

		// If first visit: new session id
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);
		  session.setAttribute(ballArsenalKey,  model);
		} 
		//Get model and userID from jsp
		userID = (String)session.getAttribute(userIDKey);

		//controller.setModel(model);
		
		ArrayList<Ball> balls = model.getBalls();
        if(model.getBalls() == null) {
			balls = new ArrayList<Ball>();
			balls.add(new Ball("FirstBall"));
		}
        else {
        	balls = model.getBalls();
        }
        
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
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("balls", balls);
		session.setAttribute(ballArsenalKey, model); //update session model

		req.getRequestDispatcher("/_view/ballArsenal.jsp").forward(req, resp);
		
	}
}
