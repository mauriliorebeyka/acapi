package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

	private List<Player> players;

	private Map<String, Deck> decks;

	private Timeline timeline;

	private List<Trigger> pastTriggers;

	private List<Trigger> futureTriggers;

	public Game() {
		this.players = new ArrayList<>();
		this.decks = new HashMap<>();
		timeline = new Timeline(this);
		pastTriggers = new ArrayList<>();
		futureTriggers = new ArrayList<>();
	}

	public void deplarePlay(Player player, Play play) {
		if (play.getCondition().test(this)) {
			timeline.queue(play);
		}
	}

	public Deck findDeck(Playable c) {
		for (Deck d : decks.values()) {
			if (d.getCards().contains(c)) {
				return d;
			}
		}
		for (Deck d : players.stream().flatMap(p -> p.getDecks().values().stream()).toList()) {
			if (d.getCards().contains(c)) {
				return d;
			}
		}
		return null;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Deck getDeck(String name) {
		return decks.get(name);
	}

	public Map<String, Deck> getDecks() {
		return decks;
	}

	public void executeNext() {
		timeline.executeNext();
	}

	public void registerPastTrigger(Trigger t) {
		pastTriggers.add(t);
	}

	public void unregisterPastTrigger(Trigger t) {
		pastTriggers.remove(t);
	}

	public List<Actionable> getPastTriggerActionablesActionables(Actionable actionable) {
		return pastTriggers.stream().filter(t -> t.test(actionable.getPlayable())).map(t -> t.getActionable()).toList();
	}

	public void registerFutureTrigger(Trigger trigger) {
		futureTriggers.add(trigger);
	}

	public void unregisterFutureTrigger(Trigger trigger) {
		futureTriggers.remove(trigger);
	}

	public List<Actionable> getFutureTriggerActionables(Actionable actionable) {
		return futureTriggers.stream().filter(t -> t.test(actionable.getPlayable())).map(t -> t.getActionable())
				.toList();
	}
}
