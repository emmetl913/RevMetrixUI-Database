package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Shot;

public class FrameController{
	
	public void addShotToFrame(Frame frame, Shot shot) {
		frame.addShot(shot);
	}
	
	public void resetFrameShots(Frame frame) {
		frame.resetShots();
	}
	
	public static Frame findOrCreateFrame(List<Frame> frames, int frameNumber) {
		for(Frame frame : frames) {
			if(frame.getFrame() == frameNumber) {
				//found existing frame
				return frame;
			}
		}
		
		//if the frame doesn't exist, create a new one and add it to the list
		Frame newFrame = new Frame(frameNumber);
		frames.add(newFrame);
		return newFrame;
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