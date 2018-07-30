package br.com.kalaha.dto;

public class PlayerDTO {

	private Integer score;
	private Boolean myTurn;
	private Integer chosenPit;
	private Integer[] pits;
	
	public PlayerDTO(Integer numOfPits) {
		this.score = 0;
		this.myTurn = false;
		this.chosenPit = 0;
		this.pits = new Integer[numOfPits];
	}
	
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Boolean getMyTurn() {
		return myTurn;
	}
	public void setMyTurn(Boolean myTurn) {
		this.myTurn = myTurn;
	}
	public Integer getChosenPit() {
		return chosenPit;
	}
	public void setChosenPit(Integer chosenPit) {
		this.chosenPit = chosenPit;
	}

	public Integer[] getPits() {
		return pits;
	}	
	
}
