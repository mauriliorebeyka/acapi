package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.WinningCondition;
import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.gameflow.GameFlow;
import com.rebeyka.acapi.entities.gameflow.NoPlayerGameFlow;
import com.rebeyka.acapi.entities.gameflow.Timeline;

public class Game {

	private List<Player> players;

	private Map<String, Deck> decks;

	private Timeline timeline;

	private List<Trigger> afterTriggers;

	private List<Trigger> beforeTriggers;

	private List<GameFlow> gameFlow;

	private int currentGameFlow;

	private WinningCondition gameEndActionable;

	public Game(List<Player> players) {
		this.players = players;
		this.decks = new HashMap<>();
		timeline = new Timeline(this);
		afterTriggers = new ArrayList<>();
		beforeTriggers = new ArrayList<>();
		List<GameFlow> gameFlow = new ArrayList<>();
		gameFlow.add(new NoPlayerGameFlow(new GameFlowBuilder(this)));
		setCurrentGameFlow(0);
		this.gameFlow = gameFlow;
		this.players.stream().forEach(p -> p.setGame(this));
	}

	public void declarePlay(Player player, Play play) {
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

	public Play findPlay(Player owner, String playId) {
		return owner.getPlays().stream().filter(p -> p.getId().equals(playId)).findFirst().orElse(null);
	}

	public Player findPlayer(String playerName) {
		return players.stream().filter(p -> p.getId().equals(playerName)).findFirst().orElse(null);
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

	public GameFlow getGameFlow() {
		return gameFlow.get(getCurrentGameFlow());
	}

	public void setGameFlow(List<GameFlow> gameFlow) {
		this.gameFlow = gameFlow;
	}

	public void setGameFlow(GameFlow gameFlow) {
		this.gameFlow = new ArrayList<>();
		this.gameFlow.add(gameFlow);
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

	public List<String> getLog() {
		return timeline.getLogMessages();
	}
	
	public void end() {
		timeline.clearNonExecutedActionables();
		afterTriggers.clear();
		beforeTriggers.clear();
		if (gameEndActionable != null) {
			timeline.queue(gameEndActionable);
		}
	}

	public void registerAfterTrigger(Trigger t) {
		afterTriggers.add(t);
	}

	public void unregisterAfterTrigger(Trigger t) {
		afterTriggers.remove(t);
	}

	public List<Actionable> getAfterTriggerActionables(Actionable triggeringActionable) {
		return afterTriggers.stream().filter(t -> t.test(triggeringActionable)).map(t -> t.getActionableToTrigger())
				.toList();
	}

	public void registerBeforeTrigger(Trigger trigger) {
		beforeTriggers.add(trigger);
	}

	public void unregisterBeforeTrigger(Trigger trigger) {
		beforeTriggers.remove(trigger);
	}

	public List<Actionable> getBeforeTriggerActionables(Actionable triggeringActionable) {
		return beforeTriggers.stream().filter(t -> t.test(triggeringActionable)).map(t -> t.getActionableToTrigger())
				.toList();
	}

	public WinningCondition getGameEndActionable() {
		return gameEndActionable;
	}

	public void setGameEndActionable(WinningCondition gameEndActionable) {
		this.gameEndActionable = gameEndActionable;
	}
}
