package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;

public class RankingByAttributeTest {

	@Test
	public void test() {
		Player player1 = new Player("Player 1");
		player1.setAttribute("VP", Types.integer(), 5);
		Player player2 = new Player("Player 2");
		player2.setAttribute("VP", Types.integer(), 8);
		Player player3 = new Player("Player 3");
		player3.setAttribute("VP", Types.integer(), 8);
		Player player4 = new Player("ZERO VP");
		Player player5 = new Player("Player 5");
		player5.setAttribute("VP", Types.integer(), 10);
		List<Player> list = List.of(player1, player2, player3, player4, player5);
		Ranking ranking = new RankingByAttribute(List.of("VP","Deaths"));
		ranking.updateRanking(list);
		assertThat(ranking.getRankingPosition()).hasSize(4).extracting(RankingPosition::getPlayers)
				.containsExactly(List.of(player5.getId()), List.of(player2.getId(), player3.getId()), List.of(player1.getId()), List.of(player4.getId()));
		assertThat(ranking.getRankingPosition()).extracting(RankingPosition::getPosition).containsExactly(1,2,4,5);
	}
}
