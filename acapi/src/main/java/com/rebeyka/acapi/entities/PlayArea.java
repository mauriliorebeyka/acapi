package com.rebeyka.acapi.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.rebeyka.acapi.exceptions.GameElementNotFoundException;
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

	public Stream<Playable> getAllPlayables() {
		return playables.stream().map(p -> (Playable)p);
	}
	
	public void add(T playable) {
		playables.add(playable);
	}
	
	public void add(Playable playable) {
		addAll(List.of(playable));
	}
	
	public void addAll(Collection<Playable> newPlayables) {
		//TODO make a better check here if we're trying to sneakily add something that's not from the same type
		playables.addAll(newPlayables.stream().map(p -> (T)p).toList());
	}
	
	public boolean contains(String playableId) {
		return getAllPlayables().anyMatch(p -> p.getId().equals(playableId));
	}
	
	public boolean contains(Playable playable) {
		return playables.contains(playable);
	}
	
	public T get(String playableId) {
		return getAllPlayables().filter(p -> p.getId().equals(playableId)).map(p -> (T)p).findFirst().orElseThrow(() -> new GameElementNotFoundException("Could not find playable %s".formatted(playableId)));
	}
	
	public void remove(Playable playable) {
		if (contains(playable)) {
			playables.remove(playable);
		}
	}
	
	public void remove(String playableId) {
		if (contains(playableId)) {
			playables.remove(get(playableId));
		}
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
