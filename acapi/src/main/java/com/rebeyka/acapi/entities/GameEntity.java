package com.rebeyka.acapi.entities;

public abstract class GameEntity {

	private String id;
	
	private Game game;
	
	public GameEntity(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	protected void setGame(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
}
