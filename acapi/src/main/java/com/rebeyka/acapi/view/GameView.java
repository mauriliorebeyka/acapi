package com.rebeyka.acapi.view;

import java.util.List;

public class GameView {

	private List<DeckView> deckView;
	
	private List<PlayerView> playerView;
	
	private List<ValueView<?>> attributeView;

	public List<DeckView> getDeckView() {
		return deckView;
	}

	public void setDeckView(List<DeckView> deckView) {
		this.deckView = deckView;
	}

	public List<PlayerView> getPlayerView() {
		return playerView;
	}

	public void setPlayerView(List<PlayerView> playerView) {
		this.playerView = playerView;
	}

	public List<ValueView<?>> getAttributeView() {
		return attributeView;
	}

	public void setAttributeView(List<ValueView<?>> attributeView) {
		this.attributeView = attributeView;
	}
}
