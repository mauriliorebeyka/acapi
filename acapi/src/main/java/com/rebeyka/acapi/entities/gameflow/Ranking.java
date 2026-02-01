package com.rebeyka.acapi.entities.gameflow;

import java.util.ArrayList;
import java.util.List;

import com.rebeyka.acapi.entities.Player;

public abstract class Ranking {

	protected List<RankingPosition> rankingPosition;
	
	public Ranking() {
		rankingPosition = new ArrayList<>();
	}
	
	public abstract List<RankingPosition> getUpdatedRank(List<Player> players);
	
	public void updateRanking(List<Player> players) {
		this.rankingPosition = getUpdatedRank(players);
	}
	
	public List<RankingPosition> getRankingPosition() {
		return rankingPosition;
	}
	
}
