package com.rebeyka.acapi.view;

import java.util.List;

public class PlayerView {

	private List<DeckView> deckView;
	
	private List<AttributeView<?>> attributeView;
	
	public List<DeckView> getDeckView() {
		return deckView;
	}

	public void setDeckView(List<DeckView> deckView) {
		this.deckView = deckView;
	}

	public List<AttributeView<?>> getAttributeView() {
		return attributeView;
	}

	public void setAttributeView(List<AttributeView<?>> attributeView) {
		this.attributeView = attributeView;
	}
}
