package com.rebeyka.acapi.view;

import java.util.ArrayList;
import java.util.List;

public class GameView {

	private List<DeckView> deckView;
	
	private List<PlayerView> playerView;
	
	private List<AttributeView<?>> attributeView;

	public GameView() {
		deckView = new ArrayList<>();
		playerView = new ArrayList<>();
		attributeView = new ArrayList<>();
	}
	
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

	public List<AttributeView<?>> getAttributeView() {
		return attributeView;
	}

	public void setAttributeView(List<AttributeView<?>> attributeView) {
		this.attributeView = attributeView;
	}
	
}
