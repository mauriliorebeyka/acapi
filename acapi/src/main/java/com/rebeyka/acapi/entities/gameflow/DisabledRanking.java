package com.rebeyka.acapi.entities.gameflow;

import java.util.List;

import com.rebeyka.acapi.entities.Player;

public class DisabledRanking extends Ranking {

	@Override
	public List<RankingPosition> getUpdatedRank(List<Player> players) {
		List<String> playerIds = players.stream().map(Player::getId).toList();
		return List.of(new RankingPosition(1,playerIds,List.of("")));
	}

}
