package com.rebeyka.acapi.builders;

import java.util.List;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.gameflow.Play;

public class DefaultPlayFactory implements PlayFactory {

	@Override
	public Play copyOf(Play template, List<Playable> targets, Game game) {
		return new Play.Builder(template).targets(targets).game(game).build();
	}

	@Override
	public Play createGameEndPlay(Game game, Actionable gameEndActionable) {
		Play.Builder gameEnd = new Play.Builder();
		gameEnd.game(game).condition(Checker.whenPlayable().always()).cost(null).name("GAME END")
				.actionable(gameEndActionable);
		return gameEnd.build();
	}
}
