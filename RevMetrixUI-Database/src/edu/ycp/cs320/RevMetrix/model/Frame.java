package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;

public class Frame{
	private int frameNumber;
	private int gameNumber;
	private ArrayList<Shot> shots;
	private int laneNumber;
	
	public Frame() {
		this.frameNumber = 1;
		this.gameNumber = 1;
		this.laneNumber = 1;
		this.shots = new ArrayList<>();
	}
	
	public Frame(int frameNumber) {
		this.frameNumber = frameNumber;
		this.shots = new ArrayList<>();
	}
	
	public Frame(int gameNumber, int frameNumber){
		this.gameNumber = gameNumber;
		this.frameNumber = frameNumber;
		this.shots = new ArrayList<>();
	}
	
	public Frame(int gameNumber, ArrayList<Shot> shots){
		this.gameNumber = gameNumber;
		this.shots = shots;
	}
	
	public int getFrame() {
		return frameNumber;
	}
	
	public void setFrame(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	
	public void addShot(Shot shot) {
		shots.add(shot);
	}
	
	public void addFrame(Shot frame) {
		this.shots.add(frame);
	}
	
	public void removeFrame(Shot frame) {
		this.shots.remove(frame);
	}
	
	public Shot getShot(int index) {
		if(index < 0 || index >= shots.size()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		
		return shots.get(index);
	}
	
	public ArrayList<Shot> getShots(){
		return shots;
	}
	
	public void setFrames(ArrayList<Shot> shots) {
		this.shots = shots;
	}
	
	public int getNumberOfFrames() {
		return shots.size();
	}
	
	public void setLaneNumber(int laneNumber) {
		this.laneNumber = laneNumber;
	}
	
	public int getLaneNumber() {
		return laneNumber;
	}
	
	public void resetShots() {
		shots.clear();
	}
}