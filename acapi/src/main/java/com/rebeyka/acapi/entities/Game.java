package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.WinningCondition;
import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.entities.gameflow.DisabledRanking;
import com.rebeyka.acapi.entities.gameflow.GameFlow;
import com.rebeyka.acapi.entities.gameflow.LogEntry;
import com.rebeyka.acapi.entities.gameflow.NoPlayerGameFlow;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.Ranking;
import com.rebeyka.acapi.entities.gameflow.Timeline;
import com.rebeyka.acapi.entities.gameflow.Trigger;
import com.rebeyka.acapi.exceptions.DuplicatedPlayableException;
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

	private Ranking ranking;

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
		setRanking(new DisabledRanking());
	}

	public String getId() {
		return id;
	}

	public boolean declarePlay(Play play, Playable target) {
		return declarePlay(play, List.of(target), false);
	}

	// TODO there's a potential bug here where you can declare two plays that are
	// possible at the same time, but the second one will still be executed if it's
	// not possible anymore at execution time.
	public boolean declarePlay(Play play, List<Playable> targets, boolean skipQueue) {
		if (!play.isPossible()) {
			return false;
		}

		Play newPlay = new Play.Builder(play).targets(targets).game(this).build();
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
		return players.stream().flatMap(p -> p.getDeckNames().stream().map(n -> p.getDeck(n)))
				.filter(d -> d.getId().equals(deckName)).findFirst().get();
	}

	public Play findPlay(Player owner, String playName) {
		Stream<Play> playStream = owner.getDeckNames().stream().flatMap(d -> owner.getDeck(d).getCards().stream())
				.flatMap(c -> c.getPlays().stream());
		return Stream.concat(playStream, owner.getPlays().stream()).filter(p -> p.getName().equals(playName))
				.findFirst().orElseThrow(() -> new GameElementNotFoundException(
						"Could not find playId %s for Player %s".formatted(playName, owner.getId())));
	}

	public Playable findPlayable(String playableName) {
		Stream<Playable> playables = players.stream()
				.flatMap(p -> p.getDeckNames().stream().flatMap(d -> p.getDeck(d).getCards().stream()));
		return Stream.concat(playables, getDecks().values().stream().flatMap(d -> d.getCards().stream()))
				.filter(p -> p.getId().equals(playableName)).findFirst().orElseThrow(
						() -> new GameElementNotFoundException("Could not find playable %s".formatted(playableName)));
	}

	public boolean hasPlayable(String playableName) {
		Stream<Playable> playables = players.stream()
				.flatMap(p -> p.getDeckNames().stream().flatMap(d -> p.getDeck(d).getCards().stream()));
		return Stream.concat(playables, getDecks().values().stream().flatMap(d -> d.getCards().stream()))
				.anyMatch(p -> p.getId().equals(playableName));
	}

	public Player findPlayer(String playerName) {
		return players.stream().filter(p -> p.getId().equals(playerName)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException("Could not find player %s".formatted(playerName)));
	}

	public Card createCard(String id, Player owner) {
		if (hasPlayable(id)) {
			throw new DuplicatedPlayableException("Card with id %s already exists".formatted(id));
		}
		Card card = new Card(id, owner);
		card.setGame(this);
		return card;
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

	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
		this.ranking.updateRanking(players);
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
		if (timeline.executeNext()) {
			ranking.updateRanking(players);
			return true;
		}
		return false;
	}

	public boolean executeAll() {
		boolean oneExecution = false;
		while (executeNext()) {
			oneExecution = true;
		}
		return oneExecution;
	}

	public List<LogEntry> getLog() {
		return timeline.getLogMessages();
	}

	public List<LogEntry> getLog(int size) {
		return timeline.getLogMessages().stream().skip((Math.max(timeline.getLogMessages().size() - size, 0))).toList();
	}

	public void end() {
		timeline.clearNonExecutedActionables();
		afterTriggers.clear();
		beforeTriggers.clear();
		if (gameEndActionable != null) {
			Play.Builder gameEnd = new Play.Builder();
			gameEnd.game(this).condition(Checker.whenPlayable().always()).cost(null).name("GAME END")
					.actionable(gameEndActionable);
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

	public List<Actionable> getQueuedActionables() {
		return timeline.getQueuedActionables();
	}
}
