package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.controller.FrameController;
import edu.ycp.cs320.RevMetrix.controller.GameController;
import edu.ycp.cs320.RevMetrix.controller.SessionController;
import edu.ycp.cs320.RevMetrix.controller.ShotController;
import edu.ycp.cs320.RevMetrix.controller.statController;
import edu.ycp.cs320.RevMetrix.controller.BallArsenalController;
import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.model.Shot;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Session;


public class ShotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int currentScore;
	private int currentShotNumber;
	private int currentFrameNumber;
	private int pinsDownCount;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }
		else {
		System.out.println("Shot Servlet: doGet");	
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute("currAccount");
		
		
		//load data to display establishment name on shots page
		EventController ec = new EventController(account.getAccountId());
		//THIS LINE WILL BREAK WHEN U GET ZACHS CODE: change to ec.getAllEventsForAccount();
//		List<Event> events = ec.getAllEventsForAccount(account.getAccountId());
//		
//		Integer eventID = (Integer)session.getAttribute("eventID");	
//		for(Event event: events) {
//			
//			if(event.getEventID() == eventID) {
//				Establishment est = event.getEstablishment();
//				String shotEstablishmentName = est.getEstablishmentName();
//				session.setAttribute("shotEstablishmentName", shotEstablishmentName);
//				System.out.println("est name: " + shotEstablishmentName);
//			}
//		}
		
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
		
		//Load frames from gameID
		Integer currentGame1Score = 0;
		Integer currentGame2Score = 0;
		Integer currentGame3Score = 0;
		FrameController fc = new FrameController();
		Integer eventID = (Integer)session.getAttribute("eventID");
		Integer gameID = (Integer)session.getAttribute("gameID");
		Game currentGame = (Game)session.getAttribute("currentGame");
		System.out.println("Current game number: "+currentGame.getGameNumber());
		System.out.println("Current gameID info: "+gameID);
		Integer sessionID = (int)session.getAttribute("sessionID");
		String currentEventName = ec.getEventByEventID(eventID).getEventName();
		System.out.println("Current event for shot: "+currentEventName);
		List<Frame> frameList = fc.getFrameByGameID(gameID);
		//Shot testShot1 = new Shot(sessionID, currentGame.getGameID(), frameList.get(0).getFrameID(), 1,69, 1, "12345", "");
		//frameList.get(0).setShot1(testShot1);
		assignShotsToFrames(frameList);
		setCurrentFrameNumberAndShotNumber(frameList);
		updateFrameScores(frameList);
		
		req.setAttribute("selectedBallID", 0);
		req.setAttribute("currentShotNumber", currentShotNumber);
		req.setAttribute("currentFrameNumber", currentFrameNumber);
		req.setAttribute("currentGame1Score", currentGame1Score);
		req.setAttribute("currentGame2Score", currentGame2Score);
		req.setAttribute("currentGame3Score", currentGame3Score);
		req.setAttribute("currentGameNumber", (int)currentGame.getGameNumber());
		req.setAttribute("currentLaneNumber", (int)currentGame.getLane());
		req.setAttribute("shotEstablishmentName", currentEventName);
		session.setAttribute("frameList", frameList);
		
		
		//used to get previous pins layout for shot2
		String pinsLeftShot1="";
		for(Frame frame: frameList) {
			System.out.println("f#: " + frame.getFrameNumber());
		}
		if(frameList.get(currentFrameNumber-1).getShot1() != null) {
			pinsLeftShot1=frameList.get(currentFrameNumber-1).getShot1().getPinsLeft();
		}
		req.setAttribute("shot1PinsLeft", pinsLeftShot1);
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
		}
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    //REACTIVATE WHEN DONE CODING
//		if(!AccountServlet.validLogin()) {
//            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
//        }
		
		System.out.println("Shot Servlet: doPost");
		String errorMessage = null;

		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute("currAccount");
		Integer eventID = (Integer)session.getAttribute("eventID");
		//Load frames from gameID
		Integer currentGame1Score = 0;
		Integer currentGame2Score = 0;
		Integer currentGame3Score = 0;
		statController statc = new statController(account.getAccountId());
		FrameController fc = new FrameController();
		GameController gc = new GameController();
		SessionController sec = new SessionController();
		EventController ec = new EventController(account.getAccountId());
		String currentEventName = ec.getEventByEventID(eventID).getEventName();
		Game currentGame = (Game)session.getAttribute("currentGame");
		int sessionID = (int)session  .getAttribute("sessionID");
		
		
		int ballID = -1;
		//BallID fetch from jsp
		//maybe condense to function
		if(req.getParameter("ballArsenalDropdown") != null && !req.getParameter("ballArsenalDropdown").isEmpty()) {
			ballID = Integer.parseInt(req.getParameter("ballArsenalDropdown"));
		}
		if(ballID == -1) {
			errorMessage = "Please select your ball";
		}
		else {
			BallArsenalController bc = new BallArsenalController();
			account.setCurrentBall(bc.getBallByBallId(ballID).get(0));
			session.setAttribute("currAccount", account);
			System.out.println("Current BallName on account = " + account.getCurrentBall().getName());
		}
		
		
		List<Frame> frameList = fc.getFrameByGameID(currentGame.getGameID());
		
		//The value from the shot box right below the 1 pin (will be an int, "X", "/", "F", "-")
		String shotBox = req.getParameter("shotBox");
		System.out.println("Shot box: "+ shotBox);
		
		
		
		if(req.getParameter("submitShot") != null && errorMessage == null) {
			System.out.println("You have clicked submit shot!");
			//ONLY EVER ACCESS INDICIES 1-10
			int pins[] = returnPinValues(req); 
			
			String pinsLeft = calculatePinsLeft(pins, req);
			
			assignShotsToFrames(frameList);
			setCurrentFrameNumberAndShotNumber(frameList);
			//if shot# = 2, count = 10 - pinsleft.length() - prevShot count
			if(currentShotNumber == 2) {
				pinsDownCount = 10 - pinsLeft.length() - frameList.get(currentFrameNumber-1).getShot1().getCount();
				if(req.getParameter("spareFixplus1").equals("1")) {
					pinsDownCount+= 1;
				}
			}
			//insert shot
			ShotController sc = new ShotController();
			//leave is not designed into the function yet
			sc.insertNewShot(sessionID, currentGame.getGameID(), frameList.get(currentFrameNumber-1).getFrameID(),
							currentShotNumber, pinsDownCount, ballID, pinsLeft);
			int frameScoreSum = 0;
			for(Frame frameItem : frameList)
			{
				if(frameItem.getScore() != -1)
				{
					frameScoreSum = frameItem.getScore();
				}
			}
			currentGame.setScore(frameScoreSum);
			
			int temp = currentGame.getGameNumber();
			List<Game> gameList = new ArrayList<Game>();
			List<Integer> gameIDs = new ArrayList<Integer>();
			
			if(temp == 1)
			{
				currentGame1Score = currentGame.getScore();
				System.out.println(sessionID);
				
				gameIDs = statc.getGameIDSBySessions(sessionID);
				
				for(int i = 0; i <gameIDs.size(); i ++)
				{
					System.out.println(gameIDs.get(i));
				    Game game = statc.getGameByGameID(gameIDs.get(i));
				    if (game != null) {
				        gameList.add(game);
				    } else {
				        System.out.println("Game with ID " + gameIDs.get(i) + " not found.");
				    }
				}
				if(gameList.size() < 3 && gameList.size() != 1)
				{
					currentGame2Score = gameList.get(1).getScore();
				}
				if(gameList.size() < 4 && gameList.size() != 1)
				{
					currentGame2Score = gameList.get(1).getScore();
					currentGame3Score = gameList.get(2).getScore();
				}
				
				
			}
			else if (temp == 2)
			{
				currentGame2Score = currentGame.getScore();
				gameIDs = statc.getGameIDSBySessions(sessionID);
				
				for(int i = 0; i <gameIDs.size(); i ++)
				{
					System.out.println(gameIDs.get(i));
				    Game game = statc.getGameByGameID(gameIDs.get(i));
				    if (game != null) {
				        gameList.add(game);
				    } else {
				        System.out.println("Game with ID " + gameIDs.get(i) + " not found.");
				    }
				}
				
				if(gameList.size() == 2)
				{
					
					currentGame1Score = gameList.get(0).getScore();
					//currentGame3Score = gameList.get(2).getScore();
				} 
				else
				{
					currentGame1Score = gameList.get(0).getScore();
					currentGame3Score = gameList.get(2).getScore();
				}
				
			}
			else 
			{
				currentGame3Score = currentGame.getScore();
				gameIDs = statc.getGameIDSBySessions(sessionID);
				
				for(int i = 0; i <gameIDs.size(); i ++)
				{
					System.out.println(gameIDs.get(i));
				    Game game = statc.getGameByGameID(gameIDs.get(i));
				    if (game != null) {
				        gameList.add(game);
				    } else {
				        System.out.println("Game with ID " + gameIDs.get(i) + " not found.");
				    }
				}
				if(gameList.size() < 3 && gameList.size() != 1)
				{
					currentGame2Score = gameList.get(0).getScore();
				}
				if(gameList.size() < 4 && gameList.size() != 1)
				{
					currentGame2Score = gameList.get(0).getScore();
					currentGame3Score = gameList.get(1).getScore();
				}
				
				
				
			}
			
			
			gc.updateGameByGameID(currentGame.getGameID(), frameScoreSum);
			sec.updateSessionBySessionID(sessionID, currentGame1Score+currentGame2Score+currentGame3Score);
			
		}
		//Now lets assign existing shots to their frames while we use this frameList
		assignShotsToFrames(frameList);
	

		//Here we ensure that we have the correct frame and shot numbers so we know where to input a shot
		setCurrentFrameNumberAndShotNumber(frameList);
		frameList = fc.getFrameByGameID(currentGame.getGameID());
		assignShotsToFrames(frameList);


		updateFrameScores(frameList);
		frameList = fc.getFrameByGameID(currentGame.getGameID());
		assignShotsToFrames(frameList);


		System.out.println("Frame#: " + currentFrameNumber + " Shot#: " +currentShotNumber);
		session.setAttribute("frameList", frameList);
		req.setAttribute("currentGame1Score", currentGame1Score);
		req.setAttribute("currentGame2Score", currentGame2Score);
		req.setAttribute("currentGame3Score", currentGame3Score);
		req.setAttribute("currentGameNumber", (int)currentGame.getGameNumber());
		req.setAttribute("currentLaneNumber", (int)currentGame.getLane());
		req.setAttribute("shotEstablishmentName", currentEventName);
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("selectedBallID", ballID);
		req.setAttribute("currentShotNumber", currentShotNumber);
		req.setAttribute("currentFrameNumber", currentFrameNumber);

		//used to get previous pins layout for shot2
		String pinsLeftShot1="";
		if(frameList.get(currentFrameNumber-1).getShot1() != null) {
			pinsLeftShot1=frameList.get(currentFrameNumber-1).getShot1().getPinsLeft();
		}
		req.setAttribute("shot1PinsLeft", pinsLeftShot1);
		
		frameList = fc.getFrameByGameID(currentGame.getGameID());
		assignShotsToFrames(frameList);


		for(Frame frame: frameList) {
			System.out.print("frame# - " +frame.getFrameNumber() + ", score: "+frame.getScore());
			if(frame.getShot1()!= null) {
				System.out.print(" shot1 pinsLeft: " + frame.getShot1().getPinsLeft() + " count:"+ frame.getShot1().getCount());
			}
			if(frame.getShot2()!=null) {
				System.out.print(" shot2 pinsLeft: " + frame.getShot2().getPinsLeft() + " count:"+ frame.getShot2().getCount());
			}
			System.out.println(""); //linebreak
		}
		
		
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}

	private void updateFrameScores(List<Frame> frameList) {
	    FrameController fc = new FrameController(); // Instantiate the controller outside the loop

	    for (Frame frame : frameList) {
	        int frameID = frame.getFrameID();
	        int frameNumber = frame.getFrameNumber();

	        // Calculate the frame score
	        System.out.println("FrameNUMBER LOOK--------------------: "+frameNumber);
	        int frameScore;
	        if (frameNumber == 1) {
	            frameScore = setFrameScore(frameList, frameNumber, 0);
	        } else {
	            int prevFrameScore = frameList.get(frameNumber - 2).getScore();
	            frameScore = setFrameScore(frameList, frameNumber, prevFrameScore);
	        }

	        // Update the frame's score in the database
	        boolean success = fc.updateFrameByFrameID(frameID, frameScore);
	        
	        // Log the update operation
	        if (success) {
	            System.out.println("Frame score updated successfully for frame ID: " + frameID);
	        } else {
	            System.out.println("Failed to update frame score for frame ID: " + frameID);
	        }
	        
	        // Update the frame object with the new score
	        frame.setScore(frameScore);
	    }
	}
	private int setFrameScore(List<Frame> frameList, int currFrameNum, int prevFrameScore) {
		int frameScore = -1; //frameList index by frame# = frame#-1
		Shot shot1=  frameList.get(currFrameNum-1).getShot1();
		Shot shot2=  frameList.get(currFrameNum-1).getShot2();
		if(shot1 != null & shot2 != null) {
			System.out.println("shot1ID: "+ shot1.getShotID());
			if(shot1.getPinsLeft() != "X" && shot2.getPinsLeft() != "/") {
				frameScore = prevFrameScore + shot1.getCount() + shot2.getCount(); 
			}
		}
		if(shot1 != null) {
			if(shot1.getPinsLeft().equals("X")) {
				Shot nextShot = frameList.get(currFrameNum).getShot1();
				if(nextShot != null) {
					if(nextShot.getPinsLeft().equals("X")) {
						Shot nextNextShot = frameList.get(currFrameNum+1).getShot1();
						if(nextNextShot != null) {
							frameScore = prevFrameScore + 10 + 10 + nextNextShot.getCount();
						}
					}
					else { //nextShot != "X"
						Shot nextNextShot = frameList.get(currFrameNum).getShot2();
						if(nextNextShot != null) {
							frameScore = prevFrameScore + 10 +nextShot.getCount() + nextNextShot.getCount();
						}
					}
				}
			}
			if(shot2 != null) {
				if(shot2.getPinsLeft().equals("/")) {
					Shot nextShot = frameList.get(currFrameNum).getShot1();
					if(nextShot != null) {
						frameScore = prevFrameScore + 10 + nextShot.getCount();
					}
				}
			}
		}
//		if(shot1 == null || shot2 == null) {
//			frameScore = -1;
//		}
		return frameScore;
	}

		
	private int[] returnPinValues(HttpServletRequest req){ //1 is down 0 is up. Pins[1]-Pins[10] are proper index
		int[] pins = new int[12]; //silly goose error means have array even bigger than needed
        for(int i = 1; i <= 10; i++) {
        	//System.out.println(("pin"+i));
        	if(req.getParameter("pin"+(i)).equals("down")) {
        		pins[i] = 1;
        	}
        	else {
        		pins[i] = 0;
        	}
        }
		return pins;
	} 
	private void assignShotsToFrames(List<Frame> frames) {
		ShotController sc = new ShotController();
		for(Frame frame: frames) {
			List<Shot> shots = sc.getShotByFrameID(frame.getFrameID());
			if(shots != null) {
				if(shots.get(0) != null) {
					frame.setShot1(shots.get(0));
				}
				if(shots.size() >1 && shots.get(1) != null) {
					frame.setShot2(shots.get(1));
				}
			}
		}
	}
	private void setCurrentFrameNumberAndShotNumber(List<Frame> frames) {
		boolean goNextFrame = true;
			for(Frame frame: frames) {
				if(goNextFrame) {
					Shot shot1 = frame.getShot1();
					Shot shot2 = frame.getShot2();
					if(shot1 == null) {
						//This must be the current shot and frame
						currentFrameNumber = frame.getFrameNumber();
						currentShotNumber = 1;
						goNextFrame = false;
					}
					if(shot1 != null && !shot1.getPinsLeft().equals("X")) { 
						if(shot2 != null){
							//goNextFrame stays true
						}
						else { //shot2 is null and no strike so we play here 
							currentFrameNumber = frame.getFrameNumber();
							currentShotNumber = 2;
							goNextFrame = false;
						}
					}
				}
			}
			System.out.println("Frame#: " + currentFrameNumber + " Shot#: " + currentShotNumber);
	}
	private String calculatePinsLeft(int[] pins, HttpServletRequest req) {
		String pinsLeft="";
		int count = 0;
		for(int i =1 ; i <= 10; i++) {
			if(pins[i] == 0) { //then the pin is up
				if(i == 10) {
					pinsLeft += ""+0; //pin 10 is represented by 0 in this property
				}
				else {
					pinsLeft += "" + i;
				}
			}
			else {
				count++;
			}
			
		}
		if(count == 10 && currentShotNumber == 1) {
			System.out.println("X");
			pinsLeft = "X";
		}
		else if(count == 10 && currentShotNumber == 2) {
			System.out.println("/");
			pinsLeft = "/";
			String tocount = req.getParameter("shotBox");
			count = Integer.parseInt(tocount);
			pinsDownCount = count;
		}
		else if(count == 0 && currentShotNumber == 1) {
			pinsLeft = "1234567890";
		}
		if(req.getParameter("shotBox").equals("F")) {
			pinsLeft = "F";
		}
		System.out.println("Pinsleft: " + pinsLeft);
		pinsDownCount = count;
		return pinsLeft;
	}
	
}
