package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class BacktrackingGameCheck<BASE, ROOT extends Checkable<BASE>> extends AbstractCheck<ROOT, BASE, Game> {

	protected BacktrackingGameCheck(Checkable<BASE> root, Function<BASE, Game> function) {
		super(root, function);
	}

	public ROOT allPlayersPassed() {
		return addTest(g -> g.getGameFlow().allPlayersPassed(), "all players passed", "passed");
	}
}
