package edu.ycp.cs320.RevMetrix.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import edu.ycp.cs320.RevMetrix.model.Game;

public class GameControllerTest {
	GameController controller;
	
	@Before
	public void setUp()
	{
		Game model = new Game();
		controller = new GameController();
		controller.setModel(model);
		controller.insertNewGame(1, 1, 1, 1);
	}
	
	@Test
	public void testGetGameBySessionID()
	{
		List<Game> testGame = controller.getGameBySessionID(1);
		
		System.out.print("Test get Games: " );
		for(Game game: testGame)
		{
			System.out.print(game.getGameID()+" ");
		}
		assertTrue(testGame.get(0).getGameID() == 1);
	}
	
	@Test
	public void testInsertNewGame()
	{
		Integer testInt = controller.insertNewGame(2, 2, 2, 2);
		
		System.out.println("Result integer for game: "+testInt);
		assertTrue(testInt == 4);
	}
}
