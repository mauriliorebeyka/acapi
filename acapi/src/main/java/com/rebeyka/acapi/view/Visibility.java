package com.rebeyka.acapi.view;

import java.util.HashMap;
import java.util.Map;

import com.rebeyka.acapi.entities.Player;

public class Visibility {

	private Map<Player, VisibilityType> visibility;
	
	public Visibility() {
		visibility = new HashMap<>();
	}
	
	public VisibilityType getVisibilityType(Player player) {
		return visibility.getOrDefault(player, VisibilityType.INHERIT);
	}
	
	public void setVisibilityType(Player player, VisibilityType visibilityType) {
		visibility.put(player, visibilityType);
	}
}
