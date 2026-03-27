package com.rebeyka.acapi.entities;

import java.util.Collection;
import java.util.stream.Stream;

import com.rebeyka.acapi.view.VisibilityType;

public abstract class PlayArea<C extends Collection<T>,T extends BasePlayable> {

	private String id;

	protected C playables;

	protected VisibilityType visibilityType;
	
	private Player owner;
	
	public PlayArea(String id, Player owner) {
		this.id = id;
		visibilityType = VisibilityType.PUBLIC;
		this.owner = owner;
	}

	public C getAll() {
		return playables;
	}

	public Stream<BasePlayable> getAllPlayables() {
		return playables.stream().map(p -> (BasePlayable)p);
	}
	
	public T remove(String playableId) {
		T playable = get(playableId);
		if (playable != null) {
			playables.remove(playable);
		}
		return playable;
	}
	
	public T get(String playableId) {
		return playables.stream().filter(p -> p.getId().equals(playableId)).findAny().orElse(null);
	}
	
	public void add(T playable) {
		playables.add(playable);
	}
	
	public boolean contains(String playableId) {
		return getAllPlayables().anyMatch(p -> p.getId().equals(playableId));
	}
	
	public boolean contains(BasePlayable playable) {
		return playables.contains(playable);
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
