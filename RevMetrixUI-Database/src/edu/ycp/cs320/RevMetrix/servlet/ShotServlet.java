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
import edu.ycp.cs320.RevMetrix.model.Session;


public class ShotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	private int currentScore;
	private int currentShotNumber;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }

		System.out.println("Shot Servlet: doGet");	
		
		//getSession = creates information based on the user
		
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute("currAccount");


		//check is new comer on webpage
		// frames = (ArrayList<Frame>) session.getAttribute("frames");
		
		//this game will contain 12 frames that would have been inserted at creation
		Game game = account.getCurrentGame();
		//for testing we will use gameID = 1;
		FrameController frameController = new FrameController();
		ShotController shotController = new ShotController();
				
		for(int i = 1; i <= 12; i++) {
			frameController.insertNewFrame(2, i);

		}
		List<Frame> frames = frameController.getFrameByGameID(2);//account.getCurrentGame().getGameID());
		
		
		//frameScore is -1 if it hasnt started yet
		//frameScore is -2 if the frame is active
		//frameScore is -3 if it needs shot data from future (aka "X" or "/")
		
		//in order to be able to resume a game set frameNumber = first frame with a -2 score
		int frameNumber = loadActiveFrame(frames);

		if(frameNumber == -1) {//the game is on its first frame
			frameNumber = 0; 
			//initialize first frame score to -2 to set it to active frame
			
			try {
				Shot secondShot = shotController.getShotByFrameID(frames.get(frameNumber).getFrameID()).get(1);
				//check to see which shot the frame was on
				if(secondShot == null) {
					currentShotNumber = 1;
				}
				else {
					currentShotNumber =2;
				}
			}
			finally {
				System.out.println("Current Shot Number = "+currentShotNumber);
			}
		}
		
		//SET CURRENT SCORE 
		currentScore = 0;
		for(Frame frame: frames) {
			if(frame.getScore() >= 0) {
				currentScore += frame.getScore();
			}
		}
		//only updates frame scores that are waiting for future frames (it uses the -3 frameScore)
		//otherwise we can set the framescore after submitting shot 1 and shot 2
		updateStrikeorSpareFrames(frames); 
		//^ uses currentScore
		
		//send the frameScores to the jsp if currentScore > 0
		//get the shots for these frames and send their value as well
		
		//stopped here but you probably coded loading from an existing game correctly
		//next step is to start inputting shots to the DB in the do post
		
			
		if(session.isNew()) {
		}
		
		//creates new game and frame within the game
		if(session.getAttribute("gameNumber") == null) {
			session.setAttribute("gameNumber", game.getGameNumber());
			session.setAttribute("frameNumber", frameNumber);
		}
		
		
		//write null test for ball arsenal...
		
		session.setAttribute("gameNumber", game.getGameNumber());
		
		
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
		
		//pass information to the jsp
		req.setAttribute("gameNumber", game.getGameNumber());
		req.setAttribute("frameNumber", frameNumber);	
		
		//if the frame is out of range, it sends an error message to the user
		boolean outOfRange = (frameNumber < 1 || frameNumber > 10);
		req.setAttribute("outOfRange", outOfRange);
				
//Here we pass the account's ball arsenal into the jsp
		
		BallArsenal model = (BallArsenal)session.getAttribute("ballArsenalKey");
		
		//Ensure model isn't empty so we can set the controller up
		if(session.isNew() || model == null) {
			model = new BallArsenal();
			session.setAttribute("ballArsenalKey",  model);
		}
		
		
		BallArsenalController arsenal = new BallArsenalController();
		arsenal.setModel(model);
		
		//Retrieve ball arsenal from DB
		ArrayList<Ball> balls = (ArrayList<Ball>) arsenal.getBallByAccountId(account.getAccountId());
		arsenal.setBalls(balls);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
		System.out.println("Shot Servlet: doPost");
		
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute("currAccount");
		
		String errorMessage = null;

		String shotKey = new String("shotKey");
		ArrayList<Frame> frames = (ArrayList<Frame>) session.getAttribute("frames");
		Integer frameNumber = (Integer) session.getAttribute("frameNumber");
		
		   // Check if this is new comer on your Webpage.
		if (session.isNew() ){
	      frames.add(new Frame(1, 1, frameNumber));
		  session.setAttribute(shotKey,  frames);
		} 
		
		frames = (ArrayList<Frame>)session.getAttribute(shotKey);
		
		
		
		Game game = new Game(1, 1, 1, 1, 0);
		//creates a new session
		Session ses = new Session(0, 0, "","","",0);
		
		session.setAttribute("gameNumber", game.getGameNumber());
		
		//retrieve or create a Frame object in the session
		if(frames == null) {
			frames = new ArrayList<Frame>();
			session.setAttribute("frames", frames);
		}
		
		FrameController frameController = new FrameController();
		ShotController controller = new ShotController();
		
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
		
//		String[] selectedPins1 = req.getParameterValues("selectedPins1");
//		String[] selectedPins2 = req.getParameterValues("selectedPins2");
//		
//		session.setAttribute("selectedPins1", selectedPins1);
//		session.setAttribute("selectedPins2", selectedPins2);
		
		BallArsenalController ballArsenalController = new BallArsenalController();
		String selectedBallId = req.getParameter("ballArsenalDropdown");
		
		int ballId = 0;
		
		if(selectedBallId != null || !selectedBallId.equals("")) {
			ballId = Integer.parseInt(selectedBallId);
			System.out.println("Ball ID: " +ballId);
		}else {
			System.out.println("Selected ball is null. Ball Id = " +ballId);
		}
		List<Ball> ballList = ballArsenalController.getBallByBallId(ballId);
		Ball ball = ballList.get(0);
		System.out.println("Ball name: " +ball);
		account.setCurrentBall(ball);
		
		BallArsenal model = (BallArsenal)session.getAttribute("ballArsenalKey");
		if(session.isNew()) {
			model = new BallArsenal();
			session.setAttribute("ballArsenalKey",  model);
		}
		
		BallArsenalController arsenal = new BallArsenalController();
		arsenal.setModel(model);
		ArrayList<Ball> balls = (ArrayList<Ball>) arsenal.getBallByAccountId(account.getAccountId());
		arsenal.setBalls(balls);
		
		String shotNum = req.getParameter("shotNumber");
		int shotNumber = 1;
		
		if(shotNum != null && !shotNum.isEmpty()) {
			try {
				shotNumber = Integer.parseInt(shotNum);
				if(shotNumber == 1) {
					
				}else if(shotNumber == 2) {
					
				}
			}catch (NumberFormatException e) {
				System.err.println("Invalid shot number: " +shotNum);
			}
		}
		
		
//		Integer score1 = Integer.parseInt(req.getParameter("score1"));
//		Integer score2 = Integer.parseInt(req.getParameter("score-box2"));
//		Integer score1 = (Integer) session.getAttribute("score1");
//		Integer score2 = (Integer) session.getAttribute("score2");


	    req.setAttribute("errorMessage", errorMessage);
	    
	    session.setAttribute("currAccount", account);
	    
	    String pin1 = req.getParameter("pin1");
	    String pin2 = req.getParameter("pin2");
	    String pin3 = req.getParameter("pin3");
	    String pin4 = req.getParameter("pin4");
	    String pin5 = req.getParameter("pin5");
	    String pin6 = req.getParameter("pin6");
	    String pin7 = req.getParameter("pin7");
	    String pin8 = req.getParameter("pin8");
	    String pin9 = req.getParameter("pin9");
	    String pin0 = req.getParameter("pin0");
	    
	    System.out.println("Pin1: "+pin1+" Pin2: "+pin2+" Pin3: "+pin3+" Pin4: "+pin4+
	    		" Pin5: "+pin5+" Pin6: "+pin6+" Pin7: "+pin7+" Pin8: "+pin8+
	    		" Pin9: "+pin9+ " Pin10: "+pin0);
	  
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
	private int loadActiveFrame(List<Frame> frames) {
		int frameNumber = -1;
		for(int i = 0; i < 12; i++) {
			if(frames.get(i).getScore() == -2) {
				frameNumber = frames.get(i).getFrameNumber();
			}
			if(frameNumber != -1) {
				break;
			}
		} //now frame number = active frame 		
		return frameNumber;
	}
	private void updateStrikeorSpareFrames(List<Frame> frames) {
		//for all frameScores == -3 check to see if their score can be finalized
				for(int i =0; i<10; i++) { //this way frame 10 can access frame 12 with out an out of bounds
					
					//we set frameScore to -3 if someone gets a "X" or "/"
					if(frames.get(i).getScore() == -3) {
						//check to see if pinsleft is "X" or "/"
						Shot shot = new Shot(); // = getShotByFrameID(currentFrameID, Shot1)
						
						if(shot.getPinsLeft() == "X") {
							Shot nextShot = new Shot(); //getShotByFrameID(currentFrameID+1, Shot1);
							//if nextShot == null dont change score because new score doesnt exist yet
							if(nextShot != null) {
							//if 2nd shot is strike check next frame for the final shot for a strike case
								
								if (nextShot.getPinsLeft() == "X"){
									//get nextShot from next frame
									Shot nextnextShot = new Shot(); //= getShotByFrameID(currentFrameID+2, Shot1);
									if(nextnextShot != null) {
										//get the score of the next shot we dont care if it is a strike or whatever we just need the numPinsDown aka count
										int secondShotScore = nextnextShot.getCount();
										
										//figure out how to track the currentScore
										frames.get(i).setScore(currentScore + 10 + 10 + secondShotScore);
										
									}
								}
								if(nextShot.getPinsLeft() != "X") {
									//current frameScore = nextFrame total
									//note shot2 not new frame
									Shot nextnextShot = new Shot(); //= getShotByFrameID(currentFrameID+1, Shot2);
									if(nextnextShot != null) {
										frames.get(i).setScore(currentScore + nextShot.getCount() + nextnextShot.getCount());
									}
								}
							}
						}
						shot = new Shot(); // = getShotByFrameID(currentFrameID, Shot2)
						if(shot.getPinsLeft()=="/" && shot != null) {
							Shot nextShot = new Shot(); //getShotByFrameID(currentFrameID+1, Shot1)
							if(nextShot != null) {
								frames.get(i).setScore(currentScore + 10 + nextShot.getCount());
							}
						}
						
					}
				}
	}
}