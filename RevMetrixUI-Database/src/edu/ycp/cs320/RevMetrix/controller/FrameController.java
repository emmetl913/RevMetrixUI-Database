package edu.ycp.cs320.RevMetrix.controller;

import java.util.ArrayList;

import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Shot;

public class FrameController{
	
	public void addShotToFrame(Frame frame, Shot shot) {
		frame.addShot(shot);
	}
	
	public void resetFrameShots(Frame frame) {
		frame.resetShots();
	}
}