package br.com.kalaha.dto;

public class GameRuleDTO {

	private GameDTO game;
	private boolean lastStoneOnPlayersSmallEmptyPit;
	private Integer lastPit;
	private boolean lastStoneOnPlayersBigPit;
	
	public GameRuleDTO() {
		this.game = null;
		this.lastStoneOnPlayersSmallEmptyPit = false;
		this.lastPit = null;
		this.lastStoneOnPlayersBigPit = false;
	}
	
	public GameDTO getGame() {
		return game;
	}
	public void setGame(GameDTO game) {
		this.game = game;
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
	public boolean isLastStoneOnPlayersBigPit() {
		return lastStoneOnPlayersBigPit;
	}
	public void setLastStoneOnPlayersBigPit(boolean lastStoneOnPlayersBigPit) {
		this.lastStoneOnPlayersBigPit = lastStoneOnPlayersBigPit;
	}
	
}
