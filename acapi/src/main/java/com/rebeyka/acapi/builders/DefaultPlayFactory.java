package com.rebeyka.acapi.builders;

import java.util.List;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.Trigger;

public class DefaultPlayFactory implements PlayFactory {

	private Game game;
	
	public DefaultPlayFactory(Game game) {
		this.game = game;
	}
	
	@Override
	public Play copyOf(Play template, List<Playable> targets) {
		return new Play.Builder(template).targets(targets).game(game).build();
	}

	@Override
	public Play createGameEndPlay(Actionable gameEndActionable) {
		return new Play.Builder().game(game).condition(Checker.whenPlayable().always()).cost(null).name("GAME END")
				.actionable(gameEndActionable).build();
	}

	@Override
	public Play fromTrigger(Play triggeringPlay, Trigger trigger) {
		return new Play.Builder(trigger.getTriggeredPlay()).targets(triggeringPlay.getTargets()).game(game).triggeredBy(trigger)
				.origin(triggeringPlay.getOrigin()).build();
	}
	
}
