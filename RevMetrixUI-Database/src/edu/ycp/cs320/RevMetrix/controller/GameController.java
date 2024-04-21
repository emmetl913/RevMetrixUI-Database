package edu.ycp.cs320.RevMetrix.controller;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Shot;

import java.util.ArrayList;

public class GameController{
	//gets the shots per frame
	public void updateFormattedShots(HttpSession session, ArrayList<Frame> frames) {
		//StringBuilder = modify contents of the string without creating a new String object
		StringBuilder formattedShots1 = new StringBuilder();
		StringBuilder formattedShots2 = new StringBuilder();
				
		int shotIndex = 0;
				
		for(Frame frame : frames) {
			for(Shot frameShot : frame.getShots()) {
				//append shot to the appropriate StringBuilder based on the shot index
				if(shotIndex == 0) {
					formattedShots1.append(frameShot.toString()).append(", ");
				}else {
					formattedShots2.append(frameShot.toString()).append(", ");
				}
						
				//increment shotIndex
				shotIndex = (shotIndex + 1) % 2;	
			}
		}
				
		session.setAttribute("formattedShots1", formattedShots1.toString());
		session.setAttribute("formattedShots2", formattedShots2.toString());
				
		System.out.print("Formatted shots updated successfully.");
	}
}
