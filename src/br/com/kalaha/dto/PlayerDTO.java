package br.com.kalaha.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.kalaha.util.Constants;

public class PlayerDTO {

	private Integer score;
	private List<Integer> pits;
	
	public PlayerDTO() {
		this.score = 0;
		
		this.pits = new ArrayList<>();
		for(int i=0; i<Constants.NUMBER_OF_PITS; i++)
			this.pits.add(Constants.NUMBER_OF_STONES);
	}
	
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public void setPits(List<Integer> pits) {
		this.pits= pits;
	}
	public List<Integer> getPits() {
		return pits;
	}
}
