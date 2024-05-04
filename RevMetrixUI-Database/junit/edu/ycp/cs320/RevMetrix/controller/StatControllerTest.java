package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.stat;
import edu.ycp.cs320.RevMetrix.controller.statController;

public class StatControllerTest {
	private stat model;
	private statController controller;
	
	@Before
	public void setUp() {
		model = new stat();
		controller = new statController(1);
	}
	
	@Test
	public void testGetSessionLeague() {
		String league = controller.getSessionLeague(2);

		System.out.println(league);
		assertTrue(league.equals("Bud Light Tournament"));
		
	}
	
	@Test
	public void testGetCurrentGameLane() {
		String lanes = controller.getCurrentGameLane(1);
		System.out.println(lanes);
		assertTrue(lanes.equals("29 - 30"));
		
	}
	
	
	
	@Test
	public void testGetGameStatsBySession() {
		Integer[] games = controller.getGameStatsBySession(1);
		
		for(int i = 0; i < games.length; i++) {
			System.out.println(games[i]);
		}
		
		assertTrue(games[0] == 163);
		assertTrue(games[1] == 185);
		assertTrue(games[2] == 185);

		
	}
	
	@Test
	public void testGetSessionsByEvent() {
		ArrayList<Integer> sessions = controller.getSessionsByEvent(2);
		
		for(Integer session:sessions) {
			System.out.println(session);

		}		
		
		assertTrue(sessions.get(0) == 1);
		assertTrue(sessions.get(1) == 2);
		assertTrue(sessions.get(2) == 3);

		
	}
}
