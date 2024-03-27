package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import edu.ycp.cs320.lab02.model.Frame;
import edu.ycp.cs320.lab02.model.Shot;

import edu.ycp.cs320.lab02.model.Frame;

public class FrameController{
	
	public void addShotToFrame(Frame frame, Shot shot) {
		frame.addShot(shot);
	}
	
	public void resetFrameShots(Frame frame) {
		frame.resetShots();
	}
}