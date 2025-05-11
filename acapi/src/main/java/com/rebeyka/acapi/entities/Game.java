package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.WinningCondition;
import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.gameflow.GameFlow;
import com.rebeyka.acapi.entities.gameflow.NoPlayerGameFlow;
import com.rebeyka.acapi.entities.gameflow.Timeline;
import com.rebeyka.acapi.exceptions.GameElementNotFoundException;

public class Game {

	private String id;

	private List<Player> players;

	private Map<String, Deck> decks;

	private Timeline timeline;

	private List<Trigger> afterTriggers;

	private List<Trigger> beforeTriggers;

	private List<GameFlow> gameFlow;

	private int currentGameFlow;

	private WinningCondition gameEndActionable;

	private List<Playable> selectedChoices;

	public Game(String id, List<Player> players) {
		this.id = id;
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
		this.selectedChoices = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public boolean declarePlay(Play play, Playable target) {
		return declarePlay(play, List.of(target), false);
	}

	public boolean declarePlay(Play play, List<Playable> targets, boolean skipQueue) {
		if (!play.getCondition().test(this)) {
			return false;
		}

		Play newPlay = new Play.Builder(play).withTargets(targets).withGame(this).build();
		timeline.queue(newPlay, skipQueue);
		return true;
	}

	public Deck findDeck(Playable c) {
		for (Deck d : decks.values()) {
			if (d.getCards().contains(c)) {
				return d;
			}
		}
		for (Deck d : players.stream().flatMap(p -> p.getDeckNames().stream().map(n -> p.getDeck(n))).toList()) {
			if (d.getCards().contains(c)) {
				return d;
			}
		}
		return null;
	}

	public Deck findDeck(String deckName) {
		return players.stream().flatMap(p -> p.getDeckNames().stream().map(n -> p.getDeck(n))).filter(d -> d.getId().equals(deckName))
				.findFirst().get();
	}

	public Play findPlay(Player owner, String playId) {
		Stream<Play> playStream = owner.getDeckNames().stream().flatMap(d -> owner.getDeck(d).getCards().stream())
				.flatMap(c -> c.getPlays().stream());
		return Stream.concat(playStream, owner.getPlays().stream()).filter(p -> p.getId().equals(playId)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException(
						"Could not find playId %s for Player %s".formatted(playId, owner.getId())));
	}

	public Player findPlayer(String playerName) {
		return players.stream().filter(p -> p.getId().equals(playerName)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException("Could not find player %s".formatted(playerName)));
	}

	public List<Player> getPlayers() {
		return players;
	}

//	public Attribute<?> getModifiedPlayerAttribute(Player player, String attributeName) {
//		// TODO Implement modifiers
//		return player.getAttribute(attributeName);
//	}

	public Deck getDeck(String name) {
		return decks.get(name);
	}

	public Map<String, Deck> getDecks() {
		return decks;
	}

	public GameFlow getGameFlow() {
		return gameFlow.get(currentGameFlow);
	}

	public void setGameFlow(List<GameFlow> gameFlow) {
		this.gameFlow = gameFlow;
	}

	public void setGameFlow(GameFlow gameFlow) {
		this.gameFlow = new ArrayList<>();
		this.gameFlow.add(gameFlow);
	}

	private void setCurrentGameFlow(int currentGameFlow) {
		this.currentGameFlow = currentGameFlow;
	}

	public WinningCondition getGameEndActionable() {
		return gameEndActionable;
	}

	public void setGameEndActionable(WinningCondition gameEndActionable) {
		this.gameEndActionable = gameEndActionable;
	}

	public List<Playable> getSelectedChoices() {
		return this.selectedChoices;
	}

	public void setSelectedChoices(List<Playable> choices) {
		this.selectedChoices = choices;
	}

	public void setSelectedChoices(Playable singleChoice) {
		this.selectedChoices = List.of(singleChoice);
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
			Play.Builder gameEnd = new Play.Builder();
			gameEnd.withGame(this).withCondition(_ -> true).withCost(null).withId("GAME END")
					.withActionable(() -> gameEndActionable);
			timeline.queue(gameEnd.build());
		}
	}

	public void registerAfterTrigger(Trigger t) {
		afterTriggers.add(t);
	}

	public void unregisterAfterTrigger(Trigger t) {
		afterTriggers.remove(t);
	}

	public List<Play> getAfterTriggerActionables(Actionable triggeringActionable) {
		return afterTriggers.stream().filter(t -> t.test(triggeringActionable))
				.map(t -> t.getTriggeredPlay(triggeringActionable.getParent())).toList();
	}

	public void registerBeforeTrigger(Trigger trigger) {
		beforeTriggers.add(trigger);
	}

	public void unregisterBeforeTrigger(Trigger trigger) {
		beforeTriggers.remove(trigger);
	}

	public List<Play> getBeforeTriggerActionables(Actionable triggeringActionable) {
		return beforeTriggers.stream().filter(t -> t.test(triggeringActionable))
				.map(t -> t.getTriggeredPlay(triggeringActionable.getParent())).toList();
	}
	
	public int countActionables(String actionableId, String actionableIdBound) {
		List<Actionable> actionables = timeline.getExecutedActionables();
		int count = 0;
		for (int i = actionables.size() - 1; i >= 0
				&& !actionables.get(i).getActionableId().equals(actionableIdBound); i--) {
			if (actionables.get(i).getActionableId().equals(actionableId)) {
				count++;
			}
		}
		return count;
	}
}
