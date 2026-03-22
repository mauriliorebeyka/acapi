package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.WinningCondition;
import com.rebeyka.acapi.builders.DefaultPlayFactory;
import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.builders.PlayFactory;
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
import com.rebeyka.acapi.modifiers.Modifier;

public class Game {

	private static final Logger LOG = LogManager.getLogger();

	private String id;

	private List<Player> players;

	private Map<String, PlayArea<? extends Collection<? extends BasePlayable>, ? extends BasePlayable>> playAreas;

	private Timeline timeline;

	private List<Play> queuedPlays;

	private Multimap<Integer, Trigger> afterTriggers;

	private Multimap<Integer, Trigger> beforeTriggers;

	private List<GameFlow> gameFlow;

	private int currentGameFlow;

	private WinningCondition gameEndActionable;

	private List<Playable> selectedChoices;

	private Ranking ranking;

	private PlayFactory playFactory;

	private List<Modifier<?>> modifiers;

	public Game(String id, List<Player> players) {
		this.id = id;
		this.players = players;
		this.playAreas = new HashMap<>();
		this.timeline = new Timeline(this);
		this.queuedPlays = new LinkedList<Play>();
		this.afterTriggers = ArrayListMultimap.create();
		this.beforeTriggers = ArrayListMultimap.create();
		List<GameFlow> gameFlow = new ArrayList<>();
		gameFlow.add(new NoPlayerGameFlow(new GameFlowBuilder(this)));
		setCurrentGameFlow(0);
		this.gameFlow = gameFlow;
		this.players.stream().forEach(p -> p.setGame(this));
		this.selectedChoices = new ArrayList<>();
		setRanking(new DisabledRanking());
		this.playFactory = new DefaultPlayFactory(this);
		this.modifiers = new ArrayList<>();
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public void setPlayFactory(PlayFactory playFactory) {
		this.playFactory = playFactory;
	}

	public String getId() {
		return id;
	}

	public boolean declarePlay(Play play, Playable target) {
		return declarePlay(play, List.of(target), false);
	}

	public boolean declarePlay(Play play, List<Playable> targets, boolean skipQueue) {
		LOG.info("{} declaring play {} with skipQueue {}", play.getOrigin(), play.getName(), skipQueue);
		Play newPlay = playFactory.copyOf(play, targets);
		if (!timeline.hasNext() || skipQueue) {
			if (play.isPossible()) {
				timeline.queue(newPlay, skipQueue);
				return true;
			}
		} else {
			LOG.info("Timeline busy, so queueing for the future");
			queuedPlays.add(newPlay);
			return true;
		}
		return false;
	}

	public PlayArea<? extends Collection<?>, ? extends BasePlayable> findPlayArea(Playable playable) {
		return Stream.concat(players.stream().flatMap(Player::getPlayAreas), playAreas.values().stream())
				.filter(playArea -> playArea.getAllPlayables().anyMatch(p -> playable == p)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException("Could not find Play Area %s".formatted(playable)));
	}

	public PlayArea<? extends Collection<?>, ? extends BasePlayable> findPlayArea(String playAreaName) {
		return players.stream().flatMap(Player::getPlayAreas).filter(d -> d.getId().equals(playAreaName)).findFirst()
				.orElseThrow(
						() -> new GameElementNotFoundException("Could not find Play Area %s".formatted(playAreaName)));
	}

	public Play findPlay(Player owner, String playName) {
		Stream<Play> playStream = owner.getAllPlayables().flatMap(c -> c.getPlays().stream());
		return Stream.concat(playStream, owner.getPlays().stream()).filter(p -> p.getName().equals(playName))
				.findFirst().orElseThrow(() -> new GameElementNotFoundException(
						"Could not find playId %s for Player %s".formatted(playName, owner.getId())));
	}

	public Playable findPlayable(String playableName) {
		Stream<BasePlayable> playables = players.stream().flatMap(Player::getAllPlayables);
		return Stream.concat(playables, getAllPlayables()).filter(p -> p.getId().equals(playableName)).findFirst()
				.orElseThrow(
						() -> new GameElementNotFoundException("Could not find playable %s".formatted(playableName)));
	}

	public boolean hasPlayable(String playableName) {
		Stream<BasePlayable> playables = players.stream()
				.flatMap(p -> p.getPlayAreas().flatMap(PlayArea::getAllPlayables));
		return Stream.concat(playables, getAllPlayables()).anyMatch(p -> p.getId().equals(playableName));
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

	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> Attribute<T> getModifiedAttribute(Playable playable,
			Attribute<T> attribute) {
		Attribute<T> newAttribute = attribute;
		for (Modifier<T> mod : modifiers.stream().filter(m -> m.valid(playable, attribute.getName()))
				.map(m -> (Modifier<T>) m).toList()) {
			newAttribute = mod.apply(newAttribute);
		}
		return newAttribute;
	}

	public List<Modifier<?>> getModifiers() {
		return modifiers;
	}

	public Map<String, PlayArea<? extends Collection<? extends BasePlayable>, ? extends BasePlayable>> getPlayAreas() {
		return playAreas;
	}

	private Stream<BasePlayable> getAllPlayables() {
		return playAreas.values().stream().flatMap(PlayArea::getAllPlayables);
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
			while (!timeline.hasNext() && !queuedPlays.isEmpty()) {
				LOG.debug("Timeline finished last queued actionable, checking for more queued plays");
				Play nextPlay = queuedPlays.removeFirst();
				if (nextPlay.isPossible()) {
					timeline.queue(nextPlay);
				}
			}
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
			Play gameEnd = playFactory.createGameEndPlay(gameEndActionable);
			timeline.queue(gameEnd);
		}
	}

	public void registerAfterTrigger(int priority, Trigger trigger) {
		if (!afterTriggers.containsValue(trigger)) {
			afterTriggers.put(priority, trigger);
		}
	}

	public void registerAfterTrigger(Trigger trigger) {
		registerAfterTrigger(0, trigger);
	}

	public void unregisterAfterTrigger(Trigger trigger) {
		Integer key = afterTriggers.entries().stream().filter(e -> e.getValue().equals(trigger)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException("Could not find trigger")).getKey();
		afterTriggers.remove(key, trigger);
	}

	public List<Play> getAfterTriggerActionables(Actionable triggeringActionable) {
		return afterTriggers.values().stream().filter(t -> t.test(triggeringActionable))
				.map(t -> playFactory.fromTrigger(triggeringActionable.getParent(), t)).toList();
	}

	public void registerBeforeTrigger(int priority, Trigger trigger) {
		if (!beforeTriggers.containsValue(trigger)) {
			beforeTriggers.put(priority, trigger);
		}
	}

	public void registerBeforeTrigger(Trigger trigger) {
		registerBeforeTrigger(0, trigger);
	}

	public void unregisterBeforeTrigger(Trigger trigger) {
		Integer key = beforeTriggers.entries().stream().filter(e -> e.getValue().equals(trigger)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException("Could not find trigger")).getKey();
		beforeTriggers.remove(key, trigger);
	}

	public List<Play> getBeforeTriggerActionables(Actionable triggeringActionable) {
		return beforeTriggers.values().stream().filter(t -> t.test(triggeringActionable))
				.map(t -> playFactory.fromTrigger(triggeringActionable.getParent(), t)).toList();
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

	public List<Play> getQueuedPlays() {
		return queuedPlays;
	}
}
