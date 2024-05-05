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
import edu.ycp.cs320.RevMetrix.controller.ShotController;
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
		FrameController fc = new FrameController();
		Game currentGame = (Game)session.getAttribute("currentGame");
		int sessionID = (int)session.getAttribute("sessionID");

		List<Frame> frameList = fc.getFrameByGameID(currentGame.getGameID());
		//Shot testShot1 = new Shot(sessionID, currentGame.getGameID(), frameList.get(0).getFrameID(), 1,69, 1, "12345", "");
		//frameList.get(0).setShot1(testShot1);
		assignShotsToFrames(frameList);
		setCurrentFrameNumberAndShotNumber(frameList);
		
		
		req.setAttribute("selectedBallID", 0);
		req.setAttribute("currentShotNumber", currentShotNumber);
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
		
		//Load frames from gameID
		FrameController fc = new FrameController();
		Game currentGame = (Game)session.getAttribute("currentGame");
		int sessionID = (int)session.getAttribute("sessionID");
		
		
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
			}
			//insert shot
			ShotController sc = new ShotController();
			//leave is not designed into the function yet
			sc.insertNewShot(sessionID, currentGame.getGameID(), frameList.get(currentFrameNumber-1).getFrameID(),
							currentShotNumber, pinsDownCount, ballID, pinsLeft);
		}
		//Now lets assign existing shots to their frames while we use this frameList
		assignShotsToFrames(frameList);
		for(Frame frame: frameList) {
			System.out.print("frame# - " +frame.getFrameNumber());
			if(frame.getShot1()!= null) {
				System.out.print(" shot1 pinsLeft: " + frame.getShot1().getPinsLeft() + " count:"+ frame.getShot1().getCount());
			}
			if(frame.getShot2()!=null) {
				System.out.print(" shot2 pinsLeft: " + frame.getShot2().getPinsLeft() + " count:"+ frame.getShot2().getCount());
			}
			System.out.println(""); //linebreak
		}

		//Here we ensure that we have the correct frame and shot numbers so we know where to input a shot
		setCurrentFrameNumberAndShotNumber(frameList);
		System.out.println("Frame#: " + currentFrameNumber + " Shot#: " +currentShotNumber);
		session.setAttribute("frameList", frameList);
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("selectedBallID", ballID);
		req.setAttribute("currentShotNumber", currentShotNumber);
		
		//used to get previous pins layout for shot2
		String pinsLeftShot1="";
		if(frameList.get(currentFrameNumber-1).getShot1() != null) {
			pinsLeftShot1=frameList.get(currentFrameNumber-1).getShot1().getPinsLeft();
		}
		req.setAttribute("shot1PinsLeft", pinsLeftShot1);
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
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
		}
		else if(count == 0) {
			pinsLeft = "-";
		}
//		else if(req.getParameter("shotBox").equals("F")) {
//			pinsLeft = "F";
//		}
		System.out.println("Pinsleft: " + pinsLeft);
		pinsDownCount = count;
		return pinsLeft;
	}
	
}
