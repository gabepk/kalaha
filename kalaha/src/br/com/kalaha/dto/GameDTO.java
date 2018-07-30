package br.com.kalaha.dto;

public class GameDTO {
	
	private Integer pits;
	private Integer stones;
	private PlayerDTO[] players;
	private Integer winner;
	
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

	public PlayerDTO[] getPlayers() {
		return players;
	}
	public void setPlayers(PlayerDTO[] players) {
		this.players = players;
	}
	public Integer getWinner() {
		return winner;
	}
	public void setWinner(Integer winner) {
		if (this.winner == null)
			this.winner = winner;
	}

}
