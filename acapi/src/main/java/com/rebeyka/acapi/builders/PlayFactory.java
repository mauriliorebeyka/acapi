package com.rebeyka.acapi.builders;

import java.util.List;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.gameflow.Play;

public interface PlayFactory {
	Play copyOf (Play template, List<Playable> targets, Game game);
	Play createGameEndPlay (Game game, Actionable gameEndActionable);
}
