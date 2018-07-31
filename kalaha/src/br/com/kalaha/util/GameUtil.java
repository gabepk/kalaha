package br.com.kalaha.util;

import br.com.kalaha.dto.GameDTO;

public class GameUtil {

	public static GameDTO checkEndOfGame(GameDTO game) {
		Integer playerIndex = null;
		
		// Check if there is any player with all pits empty
		if (game.getPlayers().get(0).getPits()
				.stream().mapToInt(Integer::intValue).sum() == 0) {
			playerIndex = 0;
		} else if (game.getPlayers().get(1).getPits()
				.stream().mapToInt(Integer::intValue).sum() == 0) {
			playerIndex = 1;
		}
		
		// If there is, sums all stones left to opponent score
		if (playerIndex != null) {
			// Get all stones left from opponent...
			int opponentIndex = playerIndex == 0 ? 1 : 0;
			int allStonesLeft = 0;
			for (int i=0; i<Constants.NUMBER_OF_PITS; i++) {
				allStonesLeft += game.getPlayers().get(opponentIndex).getPits().get(i);
				game.getPlayers().get(opponentIndex).getPits().set(i, 0);
			}
			
			// And add to opponent's score
			game.getPlayers().get(opponentIndex).setScore(
					game.getPlayers().get(opponentIndex).getScore() + allStonesLeft);
			
			// Calculate winner
			if (game.getPlayers().get(playerIndex).getScore() >
					game.getPlayers().get(opponentIndex).getScore()) {
				game.setWinner(playerIndex);
			} else if (game.getPlayers().get(playerIndex).getScore() <
					game.getPlayers().get(opponentIndex).getScore()) {
				game.setWinner(opponentIndex);
			} else {
				game.setWinner(2);
			}
			
		}
		
		return game;
	}
}
