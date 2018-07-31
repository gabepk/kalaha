package br.com.kalaha.dto;

import java.util.Arrays;

import br.com.kalaha.util.Constants;

public class PlayerDTO {

	private Integer score;
	private Boolean myTurn;
	private Integer[] pits;
	
	public PlayerDTO() {
		this.score = 0;
		this.myTurn = false;
		this.pits = new Integer[Constants.NUMBER_OF_PITS];
		Arrays.fill(this.pits, Constants.NUMBER_OF_STONES);
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

	public Integer[] getPits() {
		return pits;
	}
	
	
}
