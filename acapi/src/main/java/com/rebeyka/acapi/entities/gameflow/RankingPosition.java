package com.rebeyka.acapi.entities.gameflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RankingPosition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4751564318292585051L;

	private int position;
	
	private List<String> players;

	private List<String> score;
	
	public RankingPosition(int position, List<String> players, List<String> score) {
		this.position = position;
		this.players = players;
		this.score = score;
	}

	public RankingPosition(int position, String player, List<String> score) {
		this.position = position;
		this.players = new ArrayList<>();
		this.players.add(player);
		this.score = score;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public List<String> getScore() {
		return score;
	}

	public void setScore(List<String> score) {
		this.score = score;
	}
}
