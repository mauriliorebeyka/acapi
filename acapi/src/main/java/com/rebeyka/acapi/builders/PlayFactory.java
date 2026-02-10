package com.rebeyka.acapi.builders;

import java.util.List;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.Trigger;

public interface PlayFactory {
	Play copyOf (Play template, List<Playable> targets);
	Play createGameEndPlay (Actionable gameEndActionable);
	Play fromTrigger(Play triggeringPlay, Trigger trigger);
}
