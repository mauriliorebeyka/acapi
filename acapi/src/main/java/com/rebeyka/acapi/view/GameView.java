package com.rebeyka.acapi.view;

import java.util.ArrayList;
import java.util.List;

public class GameView {

	private List<PlayAreaView> playAreaView;
	
	private List<PlayerView> playerView;
	
	private List<AttributeView<?>> attributeView;

	public GameView() {
		playAreaView = new ArrayList<>();
		playerView = new ArrayList<>();
		attributeView = new ArrayList<>();
	}
	
	public List<PlayAreaView> getPlayAreaView() {
		return playAreaView;
	}

	public void setPlayAreaView(List<PlayAreaView> playAreaView) {
		this.playAreaView = playAreaView;
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
