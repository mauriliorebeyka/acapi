package com.rebeyka.acapi.entities;

import java.util.Collection;

import com.rebeyka.acapi.view.VisibilityType;

public abstract class PlayArea {

	private String id;

	protected Collection<Playable> playables;

	protected VisibilityType visibilityType;
	
	private Player owner;
	
	public PlayArea(String id, Player owner) {
		this.id = id;
		visibilityType = VisibilityType.PUBLIC;
		this.owner = owner;
	}

	public Collection<Playable> getAllPlayables() {
		return playables;
	}

	public String getId() {
		return id;
	}
	
	public VisibilityType getVisibilityType() {
		return visibilityType;
	}

	public void setVisibilityType(VisibilityType visibilityType) {
		if (visibilityType.equals(VisibilityType.INHERIT)) {
			throw new IllegalArgumentException("PlayArea cannot inherit visibility");
		}
		this.visibilityType = visibilityType;
	}

	public Player getOwner() {
		return owner;
	}
	
	@Override
	public String toString() {
		return "Play Area: %s".formatted(id);
	}
}
