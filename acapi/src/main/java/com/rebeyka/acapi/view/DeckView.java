package com.rebeyka.acapi.view;

import java.util.List;

public class DeckView {

	private List<PlayableView> cardView;
	
	private List<AttributeView<?>> valueView;

	public List<PlayableView> getCardView() {
		return cardView;
	}

	public void setCardView(List<PlayableView> cardView) {
		this.cardView = cardView;
	}

	public List<AttributeView<?>> getAttributeView() {
		return valueView;
	}

	public void setAttributesView(List<AttributeView<?>> valueView) {
		this.valueView = valueView;
	}
	
}
