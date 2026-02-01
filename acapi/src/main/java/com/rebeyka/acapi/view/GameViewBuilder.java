package com.rebeyka.acapi.view;

import java.util.ArrayList;
import java.util.List;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Deck;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.LogEntry;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.RankingPosition;

public class GameViewBuilder {

	private Game game;

	public GameViewBuilder(Game game) {
		this.game = game;
	}

	public GameView buildView(Player pov) {
		GameView gameView = new GameView();

		List<AttributeView<?>> gameAttributes = new ArrayList<>();

		gameAttributes.add(new AttributeView<String>("ID", game.getId()));
		gameAttributes.add(new AttributeView<Integer>("Round", game.getGameFlow().getRound()));
		if (game.getGameFlow().getCurrentPlayer() != null) {
			gameAttributes
					.add(new AttributeView<Player>("Current Player", game.getGameFlow().getCurrentPlayer()));
		}
		if (game.getGameFlow().getFirstPlayer() != null) {
			gameAttributes.add(new AttributeView<Player>("First Player", game.getGameFlow().getFirstPlayer()));
		}
		gameAttributes.add(new AttributeView<List<Player>>("Active Players",
				game.getGameFlow().getActivePlayers().stream().toList()));
		gameAttributes.add(new AttributeView<List<RankingPosition>>("Ranking",game.getRanking().getRankingPosition()));
		
		gameAttributes.add(new AttributeView<List<LogEntry>>("Log", game.getLog(10)));
		gameAttributes.add(new AttributeView<List<String>>("Queue", game.getQueuedActionables().stream().map(Actionable::getActionableId).toList()));

		gameView.setAttributeView(gameAttributes);

		List<PlayerView> playerView = game.getPlayers().stream().map(p -> buildPlayerView(p, pov)).toList();
		gameView.setPlayerView(playerView);

		return gameView;
	}

	public PlayerView buildPlayerView(Player player, Player pov) {
		PlayerView playerView = new PlayerView();

		playerView.setAttributeView(new ArrayList<>());
		playerView.getAttributeView().add(new AttributeView<Comparable<?>>("ID", player.getId()));
		playerView.getAttributeView()
				.addAll(player.getAttributes().stream().map(attribute -> player.getAttribute(attribute))
						.map(attribute -> new AttributeView<Comparable<?>>(attribute.getName(), attribute.getValue()))
						.toList());
		if (player.getPlays().stream().anyMatch(Play::isPossible)) {
			playerView.getAttributeView().add(new AttributeView<List<String>>("Available Plays",
					player.getPlays().stream().map(Play::getName).toList()));
		}

		playerView.setDeckView(player.getDeckNames().stream().map(deck -> player.getDeck(deck))
				.filter(deck -> deck.getVisibilityType() != VisibilityType.HIDDEN).map(deck -> buildDeckView(pov, deck))
				.toList());

		return playerView;
	}

	public DeckView buildDeckView(Player pov, Deck deck) {
		DeckView deckView = new DeckView();

		List<AttributeView<?>> deckAttributes = new ArrayList<>();
		deckAttributes.add(new AttributeView<String>("ID", deck.getId()));
		deckAttributes.add(new AttributeView<Integer>("Size", deck.getCards().size()));
		deckView.setAttributesView(deckAttributes);

		deckView.setCardView(deck.getCards().stream().filter(card -> isVisible(card, pov, deck))
				.map(this::buildPlayableView).toList());

		return deckView;
	}

	public boolean isVisible(Playable card, Player pov, Deck deck) {
		return switch (card.getvisibilityForPlayer(pov)) {
		case VisibilityType.HIDDEN -> false;
		case VisibilityType.INHERIT -> deck.getVisibilityType().equals(VisibilityType.PUBLIC);
		case VisibilityType.PRIVATE -> card.getOwner().equals(deck.getOwner());
		case VisibilityType.PUBLIC -> true;
		default -> false;
		};
	}

	public PlayableView buildPlayableView(Playable card) {
		PlayableView playableView = new PlayableView();
		playableView.setAttributeView(new ArrayList<>());
		playableView.getAttributeView().add(new AttributeView<Comparable<?>>("ID", card.getId()));
		playableView.getAttributeView().addAll(card.getAttributes().stream()
				.map(attr -> new AttributeView<Comparable<?>>(attr, card.getAttribute(attr).getValue())).toList());
		if (card.getPlays().stream().anyMatch(Play::isPossible)) {
			playableView.getAttributeView().add(new AttributeView<List<String>>("Available Plays",
					card.getPlays().stream().map(Play::getName).toList()));
		}
		return playableView;
	}
}
