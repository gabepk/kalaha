package br.com.kalaha.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.kalaha.util.Constants;

public class GameDTO {
	
	private Integer pits;
	private Integer stones;
	private Integer winner;
	private List<PlayerDTO> players;

	public GameDTO() {
		this.pits = Constants.NUMBER_OF_PITS;
		this.stones = Constants.NUMBER_OF_STONES;
		this.winner = null;
		
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
	public Integer getWinner() {
		return winner;
	}
	public void setWinner(Integer winner) {
		if (this.winner == null)
			this.winner = winner;
	}
	public List<PlayerDTO> getPlayers() {
		return players;
	}
	public void setPlayers(List<PlayerDTO> players) {
		this.players = players;
	}

}
