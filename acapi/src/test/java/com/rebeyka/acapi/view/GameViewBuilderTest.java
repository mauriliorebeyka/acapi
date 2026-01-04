package com.rebeyka.acapi.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Card;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;

public class GameViewBuilderTest {

	private GameViewBuilder gameViewBuilder;

	private Player player;

	private Player opponent;

	@BeforeEach
	public void setup() {
		player = new Player("Player");
		player.setAttribute("name", Types.string(), "main player");
		opponent = new Player("Opponent");
		opponent.setAttribute("name", Types.string(), "opponent");
		Game game = new Game("TEST", List.of(player, opponent));

		gameViewBuilder = new GameViewBuilder(game);
	}

	@Test
	public void testGameViewBuilder() {
		GameView view = gameViewBuilder.buildView(player);
		assertThat(view.getAttributeView()).extracting(AttributeView::getAttributeName, AttributeView::getAttributeValue).containsExactly(
				tuple("ID", "TEST"), tuple("Round", 1), tuple("Current Player", null), tuple("First Player", player),
				tuple("Active Players", Collections.emptyList()));
		assertThat(view.getPlayerView()).hasSize(2).flatExtracting(PlayerView::getAttributeView)
				.extracting(AttributeView::getAttributeName, AttributeView::getAttributeValue)
				.containsExactly(tuple("ID", "Player"), tuple("name", "main player"), tuple("ID", "Opponent"), tuple("name", "opponent"));
	}

	@Test
	public void testPlayerBuilder() {
		Card playerCard = new Card("ONE", player);
		playerCard.setAttribute("power", Types.integer(), 1);
		player.getDeck("main").add(playerCard);
		player.getDeck("main").setVisibilityType(VisibilityType.PUBLIC);

	}
}
