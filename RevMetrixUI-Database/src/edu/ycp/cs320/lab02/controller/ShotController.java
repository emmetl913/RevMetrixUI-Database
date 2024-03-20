package edu.ycp.cs320.lab02.controller;
import edu.ycp.cs320.lab02.model.Shot;

public class ShotController {
	private Shot shot;
	
	public void setModel(Shot shot) {
		this.shot = shot;
	}
	
	public int KnockedOver(Shot shot) {
		return 10-shot.getKnockedOver();
	}
	
	public int Types() {
		if(shot.getType().equals("X")) {
			return 10;
		}else if(shot.getType().equals("-")) {
			return 0;
		}else if(shot.getType().equals("F")) {
			return 0;
		}
		return 10-shot.getKnockedOver();	//spare
	}
}