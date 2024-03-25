package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;

import edu.ycp.cs320.lab02.model.Frame;

public class FrameController{
	private Frame frame;
	private ArrayList<Frame> frames;
	
	public ArrayList<Frame> getFrames(){
		if(frames == null) {
			frames = new ArrayList<>();
		}
		return frames;
	}
	
	public void addFrame(int frameNumber) {
		if(frameNumber < 1) {
			throw new IllegalArgumentException("Frame number must be at least 1");
		}
		
		Frame newFrame = new Frame(frameNumber);
		frames.add(newFrame);
	}
}