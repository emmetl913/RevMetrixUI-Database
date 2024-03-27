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

	public Shot createShot(String type, int pins) {
		Shot shot = new Shot();
		shots.add(shot);
		return shot;
	}

	public List<Shot> getShots() {
		return shots;
	}

	public int getTotalPins() {
		int totalPins = 0;
		for (Shot shot : shots) {
			totalPins += shot.getKnockedOver();
		}
		return totalPins;
	}
	
	public int calculateScore(HttpSession session) {
	    List<Shot> shots = new ArrayList<>();
	    Integer frameNumber = (Integer) session.getAttribute("frameNumber");
	    int totalScore = 0;
	    
	    if(frameNumber != null) {
	    	// Retrieve all the shots for the user from the session
		    for (int i = 1; i < frameNumber; i++) {
		        Shot shot = (Shot) session.getAttribute("shot" + i);
		        if(shot != null) {
		        	shots.add(shot);
		        }
		    }

		    for (Shot shot : shots) {
		        // Implement your calculation logic here based on the shot type, knocked pins, etc.
		        // This is just an example
		        if (shot.getType().equals("strike")) {
		            totalScore += 10;
		            if(shots.size() > shots.indexOf(shot)+1) {
		            	Shot nextShot = shots.get(shots.indexOf(shot)+1);
		            	if(nextShot != null) {
		            		totalScore += nextShot.getKnockedOver();
		            		if(!nextShot.getType().equals("strike") && shots.size() > shots.indexOf(shot)+2) {
		            			Shot secondNextShot = shots.get(shots.indexOf(shot)+2);
		            			if(secondNextShot != null) {
		            				totalScore += secondNextShot.getKnockedOver();
		            			}
		            		}
		            	}
		            }
		        } else {
		            totalScore += shot.getKnockedOver();
		            if(shots.size() > shots.indexOf(shot)+1) {
		            	Shot nextShot = shots.get(shots.indexOf(shot)+1);
		            	if(nextShot != null) {
		            		totalScore += nextShot.getKnockedOver();
		            	}
		            }
		        }
		    }
	    }

	    return totalScore;
	}

	public void reset() {
		shots.clear();
	}

}