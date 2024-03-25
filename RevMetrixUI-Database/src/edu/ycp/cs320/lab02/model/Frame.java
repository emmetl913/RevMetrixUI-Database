package edu.ycp.cs320.lab02.model;

import java.util.ArrayList;

public class Frame{
	private int frameNumber;
	private int gameNumber;
	private ArrayList<Frame> frame;
	
	public Frame(int frameNumber) {
		this.frameNumber = frameNumber;
		frame = new ArrayList<>();
	}
	
	public Frame(int gameNumber, int frameNumber){
		this.gameNumber = gameNumber;
		this.frameNumber = frameNumber;
	}
	
	public Frame(int gameNumber, ArrayList<Frame> frames){
		this.gameNumber = gameNumber;
		this.frame = frames;
	}
	
	public int getFrame() {
		return frameNumber;
	}
	
	public void setFrame(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	
	public void addFrame(Frame frame) {
		this.frame.add(frame);
	}
	
	public void removeFrame(Frame frame) {
		this.frame.remove(frame);
	}
	
	public Frame getFrameAtIndex(int index) {
		if(index < 0 || index >= frame.size()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		
		return frame.get(index);
	}
	
	public ArrayList<Frame> getFrames(){
		return frame;
	}
	
	public void setFrames(ArrayList<Frame> frames) {
		this.frame = frames;
	}
	
	public int getNumberOfFrames() {
		return frame.size();
	}
}