package edu.ycp.cs320.lab02.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.lab02.model.Shot;

public class ShotController {

	private List<Shot> shots;

	public ShotController() {
		shots = new ArrayList<Shot>();
	}

	public Shot createShot(String type, int pins) {
		Shot shot = new Shot(type, pins);
		shots.add(shot);
		return shot;
	}

	public List<Shot> getShots() {
		return shots;
	}

	public int getTotalPins() {
		int totalPins = 0;
		for (Shot shot : shots) {
			totalPins += shot.getKnockedOver();
		}
		return totalPins;
	}

	public void reset() {
		shots.clear();
	}

}