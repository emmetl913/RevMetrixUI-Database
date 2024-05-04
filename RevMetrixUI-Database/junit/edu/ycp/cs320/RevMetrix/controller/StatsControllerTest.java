package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DatabaseProvider;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.DerbyDatabase;
import edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist.IDatabase;
import edu.ycp.cs320.RevMetrix.controller.StatsController;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.Shot;

public class StatsControllerTest{
	private StatsController controller;
	private Game game;
	private Session session;
	private IDatabase db;
	
	@Before
	public void setUp() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		controller = new StatsController();
		game = new Game(1, 0, 0, 0, 0);
		session = new Session(1, 0, null, null, null, 0);
	}
	
	@Test
	public void testStrikesPercentage() {
		try {
			double percentage = controller.strikesPercentage();
			System.out.println("Strikes Percentage: " + percentage);
			assertEquals(0.0, percentage, 0.01);
			
			game.setGameID(1);
			session.setSessionID(1);
			percentage = controller.strikesPercentage();
			System.out.println("Strikes Percentage 2: " + percentage);
			assertEquals(20.0, percentage, 0.001);
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception occured: " + e.getMessage());
		}
	}
	
	@Test
	public void testSparesPercentage() {
		try {
			game.setGameID(1);
			session.setSessionID(1);
			double percentage = controller.sparePercentage();
			System.out.println("Spare Percentage: " + percentage);
			assertEquals(50.0, percentage, 0.01);
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception occured: " + e.getMessage());
		}
	}
}