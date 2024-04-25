package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.controller.FrameController;
import edu.ycp.cs320.RevMetrix.controller.ShotController;
import edu.ycp.cs320.RevMetrix.controller.BallArsenalController;
import edu.ycp.cs320.RevMetrix.model.Shot;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;
import edu.ycp.cs320.RevMetrix.model.Game;


public class ShotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<String> ballArsenal = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }

		System.out.println("Shot Servlet: doGet");	
		
		//getSession = creates information based on the user
		
		HttpSession session = req.getSession();

		//int gameNumber = (int) session.getAttribute("gameNumber");
		Integer frameNumber = (Integer) session.getAttribute("frameNumber");
		
		//check is new comer on webpage
		String shotKey = new String("shotKey");
		ArrayList<Frame> frames = (ArrayList<Frame>) session.getAttribute("frames");
		Game game = new Game(1, 1, 1, 1, 0);
//		
		if(session.isNew()) {
			//session.setAttribute(shotKey, model);
//			if(frames == null) {
//				frames = new ArrayList<Frame>();
//				
//				frames.add(new Frame(1,1));
//				session.setAttribute(shotKey, frames);
//			}
		}
		
		//creates new game and frame within the game
		if(session.getAttribute("gameNumber") == null) {
			session.setAttribute("gameNumber", game.getGameNumber());
			session.setAttribute("frameNumber", frameNumber);
		}
		
		//get ball arsenal from the session
		List<Ball> ballArsenal = (List<Ball>) session.getAttribute("ballArsenal");
		
		//write null test for ball arsenal...
		
		session.setAttribute("gameNumber", game.getGameNumber());
		
		if(frameNumber == null) {
			frameNumber = 1;
			session.setAttribute("frameNumber", frameNumber);
		}
		
		//initialize the frames ArrayList
		if(frames == null) {
			frames = new ArrayList<Frame>();
			session.setAttribute("frames", frames);
		}
		
		String action = req.getParameter("action");
		if("nextFameBtn".equals(action)) {
			if(frameNumber < 10) {
				frameNumber++;
			}
		}else if("previousFrameBtn".equals(action)) {
			if(frameNumber > 1) {
				frameNumber--;
			}
		}
		
		//add new frame object to the arraylist
		frames.add(new Frame(0, 0, frameNumber));
		
		//update frame number in session
		session.setAttribute("frameNumber", frameNumber);
		
		//passes information to the jsp
		req.setAttribute("ballArsenal", ballArsenal);
		req.setAttribute("gameNumber", game.getGameNumber());
		req.setAttribute("frameNumber", frameNumber);	
		
		//if the frame is out of range, it sends an error message to the user
		boolean outOfRange = (frameNumber < 1 || frameNumber > 10);
		req.setAttribute("outOfRange", outOfRange);
		
		Account account = (Account) session.getAttribute("currAccount");
		
		BallArsenal model = (BallArsenal)session.getAttribute("ballArsenalKey");
		if(session.isNew()) {
			model = new BallArsenal();
			session.setAttribute("ballArsenalKey",  model);
		}
		
		BallArsenalController arsenal = new BallArsenalController();
		arsenal.setModel(model);
		ArrayList<Ball> balls = (ArrayList<Ball>) arsenal.getBallByAccountId(account.getAccountId());
		arsenal.setBalls(balls);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
		System.out.println("Game Servlet: doPost");
		
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute("currAccount");
		
		String errorMessage = null;
		Object sessionShot = session.getAttribute("shotKey");

		String shotKey = new String("shotKey");
		ArrayList<Frame> frames = (ArrayList<Frame>) session.getAttribute("frames");
		Integer frameNumber = (Integer) session.getAttribute("frameNumber");
		
		Account currentAccount = (Account) session.getAttribute("currAccount");
		
		   // Check if this is new comer on your Webpage.
		if (session.isNew() ){
	      frames.add(new Frame(1, 1, frameNumber));
		  session.setAttribute(shotKey,  frames);
		} 
		
		frames = (ArrayList<Frame>)session.getAttribute(shotKey);
		
		Game game = new Game(1, 1, 1, 1, 0);
		
		session.setAttribute("gameNumber", game.getGameNumber());
		
		//retrieve or create a Frame object in the session
		if(frames == null) {
			frames = new ArrayList<Frame>();
			session.setAttribute("frames", frames);
		}
		
		FrameController frameController = new FrameController();
		ShotController controller = new ShotController();
		
		//get first and second shot from the user
		Integer score1 = (Integer) session.getAttribute("score1");
		Integer score2 = (Integer) session.getAttribute("score2");
		
		if(score1 == null) {
			score1 = 0;
		}
		if(score2 == null) {
			score2 = 0;
		}
		
		//handle form submission for next frame action
		String action = req.getParameter("action");
		if("nextFrameBtn".equals(action)) {
			//increment frame number
			if(frameNumber == null) {
				frameNumber = 1; //initialize frame number
			}else if(frameNumber <= 10) {
				frameNumber++; //increment frame number is not exceeding 10
			}
			session.setAttribute("frameNumber", frameNumber);
		}
		
		if("previousFrameBtn".equals(action)) {
			if(frameNumber != null && frameNumber > 1) {
				frameNumber--;
				
				//DB implementation...
			}
		}
		
		String[] selectedPins1 = req.getParameterValues("selectedPins1");
		String[] selectedPins2 = req.getParameterValues("selectedPins2");
		
		session.setAttribute("selectedPins1", selectedPins1);
		session.setAttribute("selectedPins2", selectedPins2);
		
		//get ball id from the jsp
		String selectedBallId = req.getParameter("ballArsenalDropdown");
		//get ball from ball ID - setCurrentBall(DB ball)
//		currentAccount.setCurrentBall(selectedBallId);
		session.setAttribute("currAccount", currentAccount);
		
		BallArsenal model = (BallArsenal)session.getAttribute("ballArsenalKey");
		if(session.isNew()) {
			model = new BallArsenal();
			session.setAttribute("ballArsenalKey",  model);
		}
		
		BallArsenalController arsenal = new BallArsenalController();
		arsenal.setModel(model);
		ArrayList<Ball> balls = (ArrayList<Ball>) arsenal.getBallByAccountId(account.getAccountId());
		arsenal.setBalls(balls);
		
		
		//create a new Shot object with submitted data
		//creating a new shot:
		//sessionID, gameID, frameID, shotNumber, count, ballID, pinsLeft
		Shot shot = new Shot(0, 0, 0, 1, "", 0, "");
		
		Frame frame = frameController.findOrCreateFrame(frames, frameNumber);
		frame.addShot(shot);
		
		//add shot object to session
		session.setAttribute("shot", shot);
		session.setAttribute("score1", score1);
		session.setAttribute("score2", score2);
				
		
		//calculate the total score using the ShotController
		int totalScore = controller.calculateScore(session);
		session.setAttribute("totalScore", totalScore);
	    
		//sets ball ID and pins left from the user input
	    if(shot.getBallID() != 0 && shot.getPinsLeft() != "") {
	    	shot.setBallID(shot.getBallID());;
	    	shot.setPinsLeft(shot.getPinsLeft());
	    }
	    
	    //creates a new shot object
	    //Shot shots = new Shot(ballName, shotType, pins);
	    
	    //total score in frames or games????
	    totalScore = controller.calculateScore(session);
	    session.setAttribute("totalScore", totalScore);
	    
	    req.setAttribute("errorMessage", errorMessage);
	    session.setAttribute(shotKey, shot);
	    
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
}