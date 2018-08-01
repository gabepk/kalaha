package br.com.kalaha.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.kalaha.util.Constants;

public class GameDTO {
	
	private Integer pits;
	private Integer stones;
	private Integer winner;
	private Integer nextPlayer;
	private boolean lastStoneOnPlayersBigPit;
	private boolean lastStoneOnPlayersSmallEmptyPit;
	private Integer lastPit;
	private List<PlayerDTO> players;

	public GameDTO() {
		this((int) Math.floor(Math.random() * 2));
	}
	
	public GameDTO(Integer nextPlayer) {
		this.pits = Constants.NUMBER_OF_PITS;
		this.stones = Constants.NUMBER_OF_STONES;
		this.winner = null;
		this.nextPlayer = nextPlayer;
		
		this.lastStoneOnPlayersBigPit = false;
		this.lastStoneOnPlayersSmallEmptyPit = false;
		this.lastPit = null;
		
		this.players = new ArrayList<>();
		this.players.add(new PlayerDTO());
		this.players.add(new PlayerDTO());
	}
	
	public Integer getPits() {
		return pits;
	}
	public void setPits(Integer pits) {
		this.pits = pits;
	}
	public Integer getStones() {
		return stones;
	}
	public void setStones(Integer stones) {
		this.stones = stones;
	}
	public boolean isLastStoneOnPlayersBigPit() {
		return lastStoneOnPlayersBigPit;
	}
	public void setLastStoneOnPlayersBigPit(boolean lastStoneOnPlayersBigPit) {
		this.lastStoneOnPlayersBigPit = lastStoneOnPlayersBigPit;
	}
	public boolean isLastStoneOnPlayersSmallEmptyPit() {
		return lastStoneOnPlayersSmallEmptyPit;
	}
	public void setLastStoneOnPlayersSmallEmptyPit(boolean lastStoneOnPlayersSmallEmptyPit) {
		this.lastStoneOnPlayersSmallEmptyPit = lastStoneOnPlayersSmallEmptyPit;
	}
	public Integer getLastPit() {
		return lastPit;
	}
	public void setLastPit(Integer lastPit) {
		this.lastPit = lastPit;
	}
	public Integer getWinner() {
		return winner;
	}
	public void setWinner(Integer winner) {
		if (this.winner == null)
			this.winner = winner;
	}
	public Integer getNextPlayer() {
		return nextPlayer;
	}
	public void setNextPlayer(Integer nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	public List<PlayerDTO> getPlayers() {
		return players;
	}
	public void setPlayers(List<PlayerDTO> players) {
		this.players = players;
	}

}
