package com.rebeyka.acapi.entities;

import java.util.Collections;
import java.util.List;

public class Game {

	private List<Player> players;

	private Timeline timeline;

	public Game(List<Player> players) {
		this.players = players;
		timeline = new Timeline(this);
	}

	public void deplarePlay(Player player, Play play) {
		timeline.queue(play);
	}

	public List<Actionable> getTriggeredActionables(Actionable trigger) {
		return Collections.emptyList();
	}

	public List<Actionable> getTriggeringActionables(Actionable trigger) {
		return Collections.emptyList();
	}
}
