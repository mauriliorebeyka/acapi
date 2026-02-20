package com.rebeyka.acapi.view;

import java.util.List;

public class PlayerView {

	private List<PlayAreaView> playAreaView;
	
	private List<AttributeView<?>> attributeView;

	public List<PlayAreaView> getPlayAreaView() {
		return playAreaView;
	}

	public void setPlayAreaView(List<PlayAreaView> playAreaView) {
		this.playAreaView = playAreaView;
	}

	public List<AttributeView<?>> getAttributeView() {
		return attributeView;
	}

	public void setAttributeView(List<AttributeView<?>> attributeView) {
		this.attributeView = attributeView;
	}
}
