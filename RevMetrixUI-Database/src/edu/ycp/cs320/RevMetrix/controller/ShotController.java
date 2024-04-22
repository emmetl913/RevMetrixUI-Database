package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.model.Shot;

public class ShotController {

	private List<Shot> shots;

	public ShotController() {
		shots = new ArrayList<Shot>();
	}

	public Shot createShot(int sessionID, int gameID, int frameID, int shotNumber, String count, int ballID, String pinsLeft) {
		Shot shot = new Shot(1, 1, 1, 1, "", 1, "");
		shots.add(shot);
		return shot;
	}

	public List<Shot> getShots() {
		return shots;
	}

	public int getTotalPins() {
		int totalPins = 0;
		for (Shot shot : shots) {
			totalPins += shot.getPins();
		}
		return totalPins;
	}
	
	public int calculateScore(HttpSession session) {
	    List<Shot> shots = new ArrayList<>();
	    Integer frameNumber = (Integer) session.getAttribute("frameNumber");
	    int totalScore = 0;
	    
	    if(frameNumber != null) {
	    	//retrieve all shots from the user's session
	    	for(int i=1; i<frameNumber; i++) {
	    		Shot shot = (Shot) session.getAttribute("shot" + i);
	    		if(shot != null) {
	    			shots.add(shot);
	    		}
	    	}
	    	
	    	for(Shot shot : shots) {
	    		//process shot type
//	    		totalScore += processShotType(shot.getType());
	    		
	    		//add knocked over pins
	    		totalScore += shot.getPins();
	    	}
	    }

	    return totalScore;
	}

	public void reset() {
		shots.clear();
	}
	
	public boolean isShotType(String value) {
		return value.equals("X") || value.equals("/") || value.equals("-") || value.equals("F");
	}
	
	public int processShotType(String type) {
		if(isShotType(type)) {
			//strike
			if(type.equals("X")) {
				return 10;
			//spare
			}else if(type.equals("/")) {
				return 10;
			//gutter or foul
			}else {
				return 0;
			}
		}else {
			//not a shot type
			return 0;
		}
	}

}