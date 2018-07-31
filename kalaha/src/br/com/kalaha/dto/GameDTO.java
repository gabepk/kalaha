package br.com.kalaha.dto;

import java.util.Arrays;

import br.com.kalaha.util.Constants;

public class GameDTO {
	
	private Integer pits;
	private Integer stones;
	private Integer winner;
	private PlayerDTO[] players;

	public GameDTO() {
		this.pits = Constants.NUMBER_OF_PITS;
		this.stones = Constants.NUMBER_OF_STONES;
		this.winner = null;
		PlayerDTO[] players = new PlayerDTO[Constants.NUMBER_OF_PLAYERS];
		Arrays.fill(players, new PlayerDTO());
		this.players = players;
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
	public Integer getWinner() {
		return winner;
	}
	public void setWinner(Integer winner) {
		if (this.winner == null)
			this.winner = winner;
	}
	public PlayerDTO[] getPlayers() {
		return players;
	}
	public void setPlayers(PlayerDTO[] players) {
		this.players = players;
	}

}
