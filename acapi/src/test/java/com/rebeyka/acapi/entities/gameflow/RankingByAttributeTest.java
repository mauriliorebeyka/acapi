package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class RankingByAttributeTest {

	private Player mockPlayer(String id, Attribute attr, String attrName) {
		Player p = mock(Player.class);
		when(p.getId()).thenReturn(id);
		when(p.getAttribute(attrName)).thenReturn(attr);
		return p;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void test() {
		String attrName = "VP";

		Attribute firstAttribute = mock(Attribute.class);
		Attribute firstTieAttribute = mock(Attribute.class);
		Attribute thirdAttribute = mock(Attribute.class);
		Attribute thirdTieAttribute = mock(Attribute.class);
		Attribute fifthAttribute = mock(Attribute.class);

		// --- Comparison behavior ---
		when(firstAttribute.compareTo(firstTieAttribute)).thenReturn(0);
		when(firstTieAttribute.compareTo(firstAttribute)).thenReturn(0);

		when(firstAttribute.compareTo(thirdAttribute)).thenReturn(1);
		when(firstAttribute.compareTo(thirdTieAttribute)).thenReturn(1);
		when(firstTieAttribute.compareTo(thirdAttribute)).thenReturn(1);
		when(firstTieAttribute.compareTo(thirdTieAttribute)).thenReturn(1);

		when(thirdAttribute.compareTo(thirdTieAttribute)).thenReturn(0);
		when(thirdTieAttribute.compareTo(thirdAttribute)).thenReturn(0);

		when(thirdAttribute.compareTo(fifthAttribute)).thenReturn(1);
		when(thirdTieAttribute.compareTo(fifthAttribute)).thenReturn(1);

		Player first = mockPlayer("Player 1", firstAttribute, attrName);
		Player firstTie = mockPlayer("Player 2", firstTieAttribute, attrName);

		Player third = mockPlayer("Player 3", thirdAttribute, attrName);
		Player thirdTie = mockPlayer("Player 4", thirdTieAttribute, attrName);

		Player fifth = mockPlayer("Player 5", fifthAttribute, attrName);

		Player sixth = mockPlayer("Player 6", null, attrName);

		RankingByAttribute ranking = new RankingByAttribute(attrName);

		ranking.updateRanking(List.of(first, firstTie, third, thirdTie, fifth, sixth));

		assertThat(ranking.getRankingPosition()).hasSize(4).extracting(RankingPosition::getPlayers).containsExactly(
				List.of(first.getId(),firstTie.getId()), List.of(third.getId(), thirdTie.getId()), List.of(fifth.getId()),
				List.of(sixth.getId()));
		assertThat(ranking.getRankingPosition()).extracting(RankingPosition::getPosition).containsExactly(1, 3, 5, 6);
		
		verify(firstAttribute, atLeast(1)).compareTo(any());
		verify(firstTieAttribute, atLeast(1)).compareTo(any());
		verify(thirdAttribute, atLeast(1)).compareTo(any());
		verify(thirdTieAttribute, atLeast(1)).compareTo(any());
	}

}
