package com.rebeyka.acapi.view;

import java.util.List;

import com.rebeyka.acapi.entities.Player;

public class PlayerView {

	private List<DeckView> deckView;
	
	private List<AttributeView<Comparable<?>>> attributeView;
	
	public List<DeckView> getDeckView() {
		return deckView;
	}

	public void setDeckView(List<DeckView> deckView) {
		this.deckView = deckView;
	}

	public List<AttributeView<Comparable<?>>> getAttributeView() {
		return attributeView;
	}

	public void setAttributeView(List<AttributeView<Comparable<?>>> attributeView) {
		this.attributeView = attributeView;
	}
}
