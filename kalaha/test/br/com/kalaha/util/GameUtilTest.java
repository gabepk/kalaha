package br.com.kalaha.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.kalaha.dto.GameDTO;
import br.com.kalaha.dto.PlayerDTO;

class GameUtilTest {

	private GameDTO game;
	
	@BeforeEach
	private void setUpBeforeClass() throws Exception {
		game = new GameDTO();
	}

	@Test
	void testGetStonesSucess() {
		// GIVEN
		int playerIndex = 0;
		int pitIndex = 0;
		
		// WHEN
		game.getPlayers().get(0).setPits(Arrays.asList(1,3,3,3,3,3));
		game.getPlayers().get(1).setPits(Arrays.asList(6,5,4,3,2,1));
		// Expeted to get 1 point from self + 1 point from opponent
		
		GameUtil.getStones(game, playerIndex, pitIndex);
		// THEN
		PlayerDTO player1 = game.getPlayers().get(0);
		List<Integer> expectedPitsPlayer1 = Arrays.asList(0,3,3,3,3,3);
		assertTrue(expectedPitsPlayer1.equals(player1.getPits()));
		assertTrue(player1.getScore() == 2);
		
		PlayerDTO player2 = game.getPlayers().get(1);
		List<Integer> expectedPitsPlayer2 = Arrays.asList(6,5,4,3,2,0);
		assertTrue(expectedPitsPlayer2.equals(player2.getPits()));
		assertTrue(player2.getScore() == 0);
	}
	
	@Test
	void testPlayOneStepEmptyPit() {
		// GIVEN
		int totalStones = 0;
		int playerIndex = 0;
		int pitIndex = 0;
		
		// WHEN
		// Empty first pit
		game.getPlayers().get(0).setPits(Arrays.asList(0,6,6,6,6,6));
		
		GameUtil.playOneStep(game, totalStones, playerIndex, pitIndex);
		
		// THEN
		PlayerDTO player1 = game.getPlayers().get(0);
		List<Integer> expectedPitsPlayer1 = Arrays.asList(0,6,6,6,6,6);
		assertTrue(expectedPitsPlayer1.equals(player1.getPits()));
		assertTrue(player1.getScore() == 0);
		
		PlayerDTO player2 = game.getPlayers().get(1);
		List<Integer> expectedPitsPlayer2 = Arrays.asList(6,6,6,6,6,6);
		assertTrue(expectedPitsPlayer2.equals(player2.getPits()));
		assertTrue(player2.getScore() == 0);
	}
	
	@Test
	void testPlayOneStepSucess() {
		// GIVEN
		int totalStones = 6;
		int playerIndex = 0;
		int pitIndex = 0;
		// WHEN
		GameUtil.playOneStep(game, totalStones, playerIndex, pitIndex);
		// THEN
		PlayerDTO player1 = game.getPlayers().get(0);
		List<Integer> expectedPitsPlayer1 = Arrays.asList(0,7,7,7,7,7);
		assertTrue(expectedPitsPlayer1.equals(player1.getPits()));
		assertTrue(player1.getScore() == 1);
		
		PlayerDTO player2 = game.getPlayers().get(1);
		List<Integer> expectedPitsPlayer2 = Arrays.asList(6,6,6,6,6,6);
		assertTrue(expectedPitsPlayer2.equals(player2.getPits()));
		assertTrue(player2.getScore() == 0);
	}
	
	@Test
	void testCheckEndOfGame_GameOver() {
		// WHEN
		game.getPlayers().get(0).setPits(Arrays.asList(0,0,0,0,0,0));
		GameUtil.checkEndOfGame(game);
		// THEN
		PlayerDTO player1 = game.getPlayers().get(0);
		List<Integer> expectedPitsPlayer1 = Arrays.asList(0,0,0,0,0,0);
		assertTrue(expectedPitsPlayer1.equals(player1.getPits()));
		assertTrue(player1.getScore() == 0);
		
		PlayerDTO player2 = game.getPlayers().get(1);
		List<Integer> expectedPitsPlayer2 = Arrays.asList(0,0,0,0,0,0);
		assertTrue(expectedPitsPlayer2.equals(player2.getPits()));
		assertTrue(player2.getScore() == 36);
		
		assertTrue(game.getWinner() == 1);
	}
	
	@Test
	void testCheckEndOfGame_GameNotOver() {
		// WHEN
		game.getPlayers().get(0).setPits(Arrays.asList(0,1,0,0,0,0));
		GameUtil.checkEndOfGame(game);
		// THEN
		PlayerDTO player1 = game.getPlayers().get(0);
		List<Integer> expectedPitsPlayer1 = Arrays.asList(0,1,0,0,0,0);
		assertTrue(expectedPitsPlayer1.equals(player1.getPits()));
		assertTrue(player1.getScore() == 0);
		
		PlayerDTO player2 = game.getPlayers().get(1);
		List<Integer> expectedPitsPlayer2 = Arrays.asList(6,6,6,6,6,6);
		assertTrue(expectedPitsPlayer2.equals(player2.getPits()));
		assertTrue(player2.getScore() == 0);
		
		assertTrue(game.getWinner() == null);
	}

}
