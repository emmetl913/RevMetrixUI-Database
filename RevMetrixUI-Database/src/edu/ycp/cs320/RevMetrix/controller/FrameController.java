package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Shot;

public class FrameController{
	
	private IDatabase db = null;
	
	public FrameController(){
		//create db instance
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
public boolean insertNewFrame(int game_id, int frameNumber) {
		
		// insert new frame with score -1 to show frame has not been played
		Integer frame_id = db.insertNewFrame(game_id, -1 , frameNumber);
		//check if the insertion succeeded
		if (game_id > 0){
			System.out.println("New frame (frame ID: " + frame_id +") successfully added to balls table");
			return true;
		}
		else{
			System.out.println("Failed to insert (Ball ID: " + frame_id + ") to balls table");
			return false;
		}
	}

//THis function will only be here until it is used in GameCOntroller instead

	
public List<Frame> getFrameByGameID(int gameID){
	List<Frame> frameList = db.getFrameByGameID(gameID);
	if(frameList.isEmpty()) {
		System.out.println("Aint no frames in your database bro for game id: "+gameID);
		return null;
	}
	else {
		return frameList;
	}
}


//	public void displayShots(List<Frame> frames, int selectedFrameNumber) {
//		for(Frame frame : frames) {
//			if(frame.getFrame() == selectedFrameNumber) {
//				//display shots for the selected frame
//				List<Shot> shots = frame.getShots();
//			}
//		}
//	}
}