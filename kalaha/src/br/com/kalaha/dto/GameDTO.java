package br.com.kalaha.dto;

public class GameDTO {
	
	private static GameDTO instance;
	
	private Integer pits;
	private Integer stones;
	private PlayerDTO[] players;
	private Integer winner;
	
	public GameDTO getInstance() {
		if(instance == null) {
			instance = new GameDTO();
			instance.pits = 6;
			instance.stones = 6;
			instance.players = new PlayerDTO[2];
			instance.winner = null;
		}
		return instance;
	}
	
	public Integer getPits() {
		return pits;
	}
	public Integer getStones() {
		return stones;
	}
	public PlayerDTO[] getPlayers() {
		return players;
	}
	public Integer getWinner() {
		return winner;
	}
	public void setWinner(Integer winner) {
		if (this.winner == null)
			this.winner = winner;
	}

}
