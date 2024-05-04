package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Shot;

public class ShotController {

	private List<Shot> shots;


	private IDatabase db = null;

	public ShotController() {
		shots = new ArrayList<Shot>();
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
	public List<Shot> getShotByFrameID(int frameID){
		List<Shot> frameList = db.getShotByFrameID(frameID);
		if(frameList.isEmpty()) {
			System.out.println("Aint no shots in your database bro for frame id: "+frameID);
			return null;
		}
		else {
			return frameList;
		}
	}
	public Integer insertNewShot(int sessionID, int gameID, int frameID, int shotNumber, int count, int ballID, String pinsLeft) {
		
		// insert new book (and possibly new author) into DB
		Integer ball_id = db.insertNewShotWithFrameID(sessionID, gameID, frameID, shotNumber, count,ballID, pinsLeft);
		//check if the insertion succeeded
		if (ball_id > 0){
			//System.out.println("New shot (shot ID: " + ball_id+") successfully added to shots table");
			return ball_id;
		}
		else{
			System.out.println("Failed to insert (shot ID: " + ball_id + ") to shots table");
			return null;
		}
	}

}