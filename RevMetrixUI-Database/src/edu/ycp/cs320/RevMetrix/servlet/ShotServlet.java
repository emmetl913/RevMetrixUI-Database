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
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute("currAccount");
		
		//Load frames from gameID
		FrameController fc = new FrameController();
		Game currentGame = (Game)session.getAttribute("currentGame");
		List<Frame> frameList = fc.getFrameByGameID(currentGame.getGameID());
		
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
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
		List<Frame> frameList = fc.getFrameByGameID(currentGame.getGameID());
//		for(Frame frame: frameList) {
//				System.out.println(frame.getFrameID() + "frame# - " +frame.getFrameNumber());
//		}
		
		//The value from the shot box right below the 1 pin (will be an int, "X", "/", "F", "-")
		String shotBox = req.getParameter("shotBox");
		System.out.println("Shot count: "+ shotBox);

		//ONLY EVER ACCESS INDICIES 1-10
		int pins[] = returnPinValues(req); 
		
		if(req.getParameter("submitShot") != null) {
			System.out.println("You have clicked submit shot!");
			errorMessage = "hi stinky :P";
		}
		
		
		req.setAttribute("errorMessage", errorMessage);
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
	private int[] returnPinValues(HttpServletRequest req){ //1 is down 0 is up
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
}
