package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.gameflow.GameFlow;
import com.rebeyka.acapi.entities.gameflow.GameFlowBuilder;
import com.rebeyka.acapi.entities.gameflow.NoPlayerGameFlow;
import com.rebeyka.acapi.entities.gameflow.Timeline;

public class Game {

	private List<Player> players;

	private Map<String, Deck> decks;

	private Timeline timeline;

	private List<Trigger> pastTriggers;

	private List<Trigger> futureTriggers;

	private List<GameFlow> playerOrder;
	
	private int currentGameFlow;

	public Game(List<Player> players) {
		this.players = players;
		this.decks = new HashMap<>();
		timeline = new Timeline(this);
		pastTriggers = new ArrayList<>();
		futureTriggers = new ArrayList<>();
		List<GameFlow> gameFlow = new ArrayList<>();
		gameFlow.add(new NoPlayerGameFlow(new GameFlowBuilder().withGame(this).withPlayers(players)));
		setCurrentGameFlow(0);
		playerOrder = gameFlow;
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

	public Attribute<?> getModifiedPlayerAttribute(Player player, String attributeName) {
		// TODO Implement modifiers
		return player.getAttribute(attributeName);
	}

	public Deck getDeck(String name) {
		return decks.get(name);
	}

	public Map<String, Deck> getDecks() {
		return decks;
	}

	public GameFlow getPlayerOrder() {
		return playerOrder.get(getCurrentGameFlow());
	}

	public void setPlayerOrder(List<GameFlow> playerOrder) {
		this.playerOrder = playerOrder;
	}
	
	public void setPlayerOrder(GameFlow playerOrder) {
		this.playerOrder = new ArrayList<>();
		this.playerOrder.add(playerOrder);
	}
	
	private int getCurrentGameFlow() {
		return currentGameFlow;
	}

	private void setCurrentGameFlow(int currentGameFlow) {
		this.currentGameFlow = currentGameFlow;
	}

	public boolean executeNext() {
		return timeline.executeNext();
	}

	public boolean executeAll() {
		boolean oneExecution = false; 
		while (executeNext()) {
			oneExecution = true;
		}
		return oneExecution;
	}
	
	public void registerPastTrigger(Trigger t) {
		pastTriggers.add(t);
	}

	public void unregisterPastTrigger(Trigger t) {
		pastTriggers.remove(t);
	}

	public List<Actionable> getPastTriggerActionablesActionables(Actionable actionable) {
		return pastTriggers.stream().filter(t -> t.test(actionable)).map(t -> t.getActionable()).toList();
	}

	public void registerFutureTrigger(Trigger trigger) {
		futureTriggers.add(trigger);
	}

	public void unregisterFutureTrigger(Trigger trigger) {
		futureTriggers.remove(trigger);
	}

	public List<Actionable> getFutureTriggerActionables(Actionable actionable) {
		return futureTriggers.stream().filter(t -> t.test(actionable)).map(t -> t.getActionable()).toList();
	}
}
