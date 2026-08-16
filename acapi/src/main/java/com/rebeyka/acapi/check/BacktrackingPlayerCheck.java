package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Player;

public class BacktrackingPlayerCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>>
		extends AbstractCheck<ROOT, BASE, Player> {

	public BacktrackingPlayerCheck(AbstractCheck<?,BASE,?> root, Function<BASE, Player> function) {
		super(root, function);
	}

	public BacktrackingStringCheck<BASE, ROOT> id() {
		return new BacktrackingStringCheck<>(this, f -> function.apply(f).getId());
	}

	public ROOT isCurrentPlayer() {
		return addTest(p -> p.getGame().getGameFlow().isCurrentPlayer(p), "is", "current player");
	}

	public ROOT isActivePlayer() {
		return addTest(p -> p.getGame().getGameFlow().isPlayerActive(p), "is", "active player");
	}

}
