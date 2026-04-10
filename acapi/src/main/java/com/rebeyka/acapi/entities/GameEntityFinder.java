package com.rebeyka.acapi.entities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.exceptions.GameElementNotFoundException;

public class GameEntityFinder {

	private static final Logger LOG = LogManager.getLogger();
	
	private Game game;
	
	public GameEntityFinder(Game game) {
		this.game = game;
	}
	
	public PlayArea<?, ? extends BasePlayable> playArea(BasePlayable playable) {
		return getAllPlayAreas().filter(playArea -> playArea.contains(playable)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException("Could not find Play Area %s".formatted(playable.getId())));
	}

	public PlayArea<?, ? extends BasePlayable> playArea(String playAreaName) {
		return getAllPlayAreas().filter(d -> d.getId().equals(playAreaName)).findFirst().orElseThrow(
				() -> new GameElementNotFoundException("Could not find Play Area %s".formatted(playAreaName)));
	}

	public Play play(Player owner, String playName) {
		return owner.getAllPlays().filter(p -> p.getName().equals(playName))
				.findFirst().orElseThrow(() -> new GameElementNotFoundException(
						"Could not find playId %s for Player %s".formatted(playName, owner.getId())));
	}

	public Playable playable(String playableName) {
		return playables(List.of(playableName)).get(0);
	}

	public List<Playable> playables(String... playableNames) {
		return playables(Arrays.asList(playableNames));
	}
	
	public List<Playable> playables(List<String> playableNames) {
		List<Playable> playables = getAllPlayables().filter(p -> playableNames.contains(p.getId())).map(p -> (Playable)p).toList();
		if (playables.isEmpty()) {
			throw new GameElementNotFoundException("No playables found when searching for %s".formatted(playableNames));
		}
		if (playables.size() < playableNames.size()) {
			LOG.warn("Return less playables than searched, {} from {}",playables.size(),playableNames.size());
		}
		return playables;
	}
	
	public boolean hasPlayable(String playableName) {
		return getAllPlayables().anyMatch(p -> p.getId().equals(playableName));
	}

	public Player player(String playerName) {
		return game.getPlayers().stream().filter(p -> p.getId().equals(playerName)).findFirst()
				.orElseThrow(() -> new GameElementNotFoundException("Could not find player %s".formatted(playerName)));
	}

	
	private Stream<Playable> getAllPlayables() {
		return Stream.concat(game.getPlayAreas().values().stream().flatMap(PlayArea::getAllPlayables),
				game.getPlayers().stream().flatMap(Player::getAllPlayables));
	}

	private Stream<PlayArea<?, ? extends BasePlayable>> getAllPlayAreas() {
		return Stream.concat(game.getPlayAreas().values().stream(), game.getPlayers().stream().flatMap(Player::getPlayAreas));
	}

}
