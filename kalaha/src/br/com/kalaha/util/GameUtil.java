package br.com.kalaha.util;

import br.com.kalaha.dto.GameDTO;

public class GameUtil {

	public static void getStones(GameDTO game, int playerIndex, int pitIndex) {
		int opponentIndex = playerIndex == 0 ? 1 : 0;
		int stonesFromOpponentsPit = game.getPlayers().get(opponentIndex).getPits().get(Constants.NUMBER_OF_PITS - 1 - pitIndex);
		int stonesFromPlayersPit = game.getPlayers().get(playerIndex).getPits().get(pitIndex);
		
		// Empty pits
		game.getPlayers().get(opponentIndex).getPits().set(Constants.NUMBER_OF_PITS - 1 - pitIndex, 0);
		game.getPlayers().get(playerIndex).getPits().set(pitIndex, 0);
		
		// Sum stones to player's score
		game.getPlayers().get(playerIndex).setScore(
				game.getPlayers().get(playerIndex).getScore() +
				stonesFromPlayersPit + stonesFromOpponentsPit);
	}
	
	public static void playOneStep(GameDTO game, int totalStones, int playerIndex, int pitIndex) {
		if (totalStones <= 0) return;
		
		// Empty chosen pit
		game.getPlayers().get(playerIndex).getPits().set(pitIndex, 0);
		
		// Clear rules
		game.setLastStoneOnPlayersBigPit(false);
		game.setLastStoneOnPlayersSmallEmptyPit(false);
		
		// Sow following pits
		int i = 1;
		int pitValue = 0;
		int lastPit = pitIndex;
		Integer nextPitIndex = pitIndex;
		Integer nextPlayerIndex = playerIndex;
		do {
			nextPitIndex = (pitIndex + i) % Constants.NUMBER_OF_PITS; // Does not depend on player
			
			nextPlayerIndex = (nextPitIndex == 0) ? // Check if is sowing on the 'other' side
					(nextPlayerIndex == 0 ? 1 : 0) : // If it is, check which side is the 'other' side
					nextPlayerIndex; // If it is not, nextPlayer is the same one as last iteration
			
			// Scores if current player finishes all his/hers small pits
			if (nextPitIndex == 0 && nextPlayerIndex != playerIndex) {
				pitValue = game.getPlayers().get(playerIndex).getScore();
				game.getPlayers().get(playerIndex).setScore(pitValue + 1);
				
				// Return if there are no more stones
				if (--totalStones <= 0) {
					game.setLastStoneOnPlayersBigPit(true);
					break;
				}
			}
			
			// Sow stone in the next pit
			pitValue = game.getPlayers().get(nextPlayerIndex).getPits().get(nextPitIndex);
			game.getPlayers().get(nextPlayerIndex).getPits().set(nextPitIndex, pitValue + 1);
			
			i++;
			lastPit = nextPitIndex;
		} while (--totalStones > 0);
		
		// Set player's last pit
		game.setLastPit(lastPit);
		
		// Set if that last pit was empty
		if (nextPlayerIndex == playerIndex) {
			pitValue = game.getPlayers().get(playerIndex).getPits().get(lastPit);
			if (pitValue == 1)
				game.setLastStoneOnPlayersSmallEmptyPit(true);
		}
	}
	
	public static void checkEndOfGame(GameDTO game) {
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
	}
}
