package com.rebeyka.acapi.entities;

import java.util.HashMap;
import java.util.Map;

import com.rebeyka.acapi.view.VisibilityType;

public abstract class BasePlayable extends Playable {

private VisibilityType defaultVisibility;
	
	private Map<Player,VisibilityType> visibilityByPlayer;
	
	private Player owner;
	
	public BasePlayable(String id, Player owner) {
		super(id);
		defaultVisibility = VisibilityType.INHERIT;
		visibilityByPlayer = new HashMap<>();
		this.owner = owner;
	}

	public VisibilityType getDefaultVisibility() {
		return defaultVisibility;
	}
	
	public void setDefaultVisibility(VisibilityType visibilityType) {
		this.defaultVisibility = visibilityType;
	}
	
	public void setVisibilityForPlayer(Player player, VisibilityType visibility) {
		visibilityByPlayer.put(player, visibility);
	}
	
	public VisibilityType getvisibilityForPlayer(Player player) {
		return visibilityByPlayer.getOrDefault(player, defaultVisibility);
	}
	
	public Player getOwner() {
		return owner;
	}
}
