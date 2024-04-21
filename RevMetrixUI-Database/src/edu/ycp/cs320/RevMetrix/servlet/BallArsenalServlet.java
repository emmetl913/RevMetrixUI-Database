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
		
		BallArsenalController controller = new BallArsenalController();
		// Get session creation time.
		HttpSession session = req.getSession();
	    //long createTime = session.getCreationTime();
		   
		// Get last access time of this Webpage.
		//long lastAccessTime = session.getLastAccessedTime();
		//String userIDKey = new String("userID");
		//String userID = new String("ABCD");

		   // Check if this is new comer on your Webpage.
		String ballArsenalKey = new String("ballArsenalKey");
		BallArsenal model = (BallArsenal)session.getAttribute(ballArsenalKey);

		// If first visit: new session id
		if (session.isNew() ){
			if(model == null) {
				model = new BallArsenal();
			}
		  session.setAttribute(ballArsenalKey,  model);
		} 
		
		if(model == null) {
			model = new BallArsenal();
			session.setAttribute(ballArsenalKey, model);
		}
		controller.setModel(model);	
		
		ArrayList<Ball> balls;
       // balls = model.getBalls();
		Account currentAccount = (Account) session.getAttribute("currAccount");
        balls = (ArrayList<Ball>) controller.getBallByAccountId(currentAccount.getAccountId());
        controller.setBalls(balls);
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

		
		BallArsenalController controller = new BallArsenalController();
				
		// Get session creation time.
				HttpSession session = req.getSession();
				String ballArsenalKey = new String("ballArsenalKey");
				BallArsenal model = (BallArsenal)session.getAttribute(ballArsenalKey);

				// If first visit: new session id
				if (session.isNew() ){
					if(model == null) {
						model = new BallArsenal();
					}
				  session.setAttribute(ballArsenalKey,  model);
				} 
				
				if(model == null) {
					model = new BallArsenal();
					session.setAttribute(ballArsenalKey, model);
				}
				controller.setModel(model);	
				
				ArrayList<Ball> balls;
		       // balls = model.getBalls();
				Account currentAccount = (Account) session.getAttribute("currAccount");
				if(currentAccount == null) {
					//If they have not signed in don't run the code after this by sending them to the login page
					req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
				}
		        balls = (ArrayList<Ball>) controller.getBallByAccountId(currentAccount.getAccountId());
//		        if(balls == null) {
//					balls = new ArrayList<Ball>();
//					balls.add(new Ball("FirstBall"));
//				}
		       
				//end session shenanigans
		
		//on button press
		String newBallName = req.getParameter("ballName");
		String newBallBrand = req.getParameter("ballBrand");
		String newBallWeight = req.getParameter("ballWeight");
		String newBallColor = req.getParameter("ballColor");
		String newBallLeftHand = req.getParameter("leftHand");
		String newBallRightHand = req.getParameter("rightHand");
		String removeBallName = req.getParameter("removeBallName");
		boolean rightHanded = true;
		
		if (req.getParameter("addBall") != null ) {
		    float ballWeight = Float.parseFloat(newBallWeight);
		    rightHanded = Boolean.parseBoolean(newBallRightHand);
		    Integer did = 0;
		    try {
				if (req.getParameter("newType").equals("left")) {
					rightHanded = false;
					did++;
				} else if (req.getParameter("newType").equals("right")) {
					rightHanded = true;
					did++;
				} 
		    }	
		    catch(NullPointerException e) {
		    	errorMessage = "Please select the ball's hand";
		    }
		    if(did != 0) {
				controller.insertBallinDB(currentAccount.getAccountId(), 
				ballWeight, newBallName, rightHanded, newBallBrand, newBallColor);
			}
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
//		if (session.getAttribute("currentGame") != null ) {
//			Game g = (Game)session.getAttribute("currentGame");
//			System.out.println(g.getLane());
//		}
		String selectedBall = req.getParameter("selectedBall");
		
		try {
			//Setting the selected ball to the currentBall on the currentACcount
			if (selectedBall !=  null)
			{
				String tempBallName = selectedBall;
				Ball tempBall = new Ball(tempBallName);
				System.out.println(tempBallName);
				currentAccount.setCurrentBall(tempBall);
			}
			System.out.println("Account Current Ball: " + currentAccount.getCurrentBall().getName());
		} catch(NullPointerException e)
		{
			System.out.println("you are dumb you should've figured this out by now dumbass");
		}
		
		
		req.setAttribute("errorMessage", errorMessage);
		 balls = (ArrayList<Ball>) controller.getBallByAccountId(currentAccount.getAccountId());
	     controller.setBalls(balls);
		req.setAttribute("balls", balls);
		//Update the current ball to the session account
		session.setAttribute("currAccount", currentAccount);
		
		session.setAttribute(ballArsenalKey, model); //update session model

		req.getRequestDispatcher("/_view/ballArsenal.jsp").forward(req, resp);
		
	}
}