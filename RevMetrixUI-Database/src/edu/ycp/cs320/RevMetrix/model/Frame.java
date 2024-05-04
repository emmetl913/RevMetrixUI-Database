package edu.ycp.cs320.RevMetrix.model;

import java.util.ArrayList;
import java.util.List;

public class Frame{
	private int gameID, score, frameNumber;
	private ArrayList<Shot> shots;
	private int laneNumber;
	private int frameID;
	private Shot shot1, shot2;
	
	public Frame(int gameID, int score, int frameNumber) {
		this.frameNumber = frameNumber;
		this.gameID = gameID;
		this.score = score;
	}
	
	public int getGameID()
	{
		return this.gameID;
	}
	public void setGameID(int id)
	{
		this.gameID = id;
	}
	public int getFrameNumber()
	{
		return this.frameNumber;
	}
	public void setFrameNumber(int frameNumber)
	{
		this.frameNumber = frameNumber;
	}
	public int getScore()
	{
		return this.score;
	}
	public void setScore(int score)
	{
		this.score = score;
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

	public int getFrameID() {
		return frameID;
	}

	public void setFrameID(int frameID) {
		this.frameID = frameID;
	}

	public Shot getShot1() {
		return shot1;
	}

	public void setShot1(Shot shot1) {
		this.shot1 = shot1;
	}

	public Shot getShot2() {
		return shot2;
	}

	public void setShot2(Shot shot2) {
		this.shot2 = shot2;
	}
}