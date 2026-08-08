package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.BasePlayable;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.view.VisibilityType;

public class BacktrackingPlayableCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends GameEntityCheck<BASE, ROOT, Playable> {

	protected BacktrackingPlayableCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Playable> function) {
		super(root, testResults, function);
	}

	protected BacktrackingPlayableCheck(List<TestResult<BASE>> testResults, Function<BASE, Playable> function) {
		super(testResults, function);
	}

	@Override
	protected BacktrackingPlayableCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new BacktrackingPlayableCheck<>(root, testResults, this.function);
	}

	public ROOT isPlayer() {
		return addTest(p -> p instanceof Player, f -> f.getClass(), "Playable type", "is Player");
	}

	public ROOT isCurrentPlayer() {
		return addTest(p -> p instanceof Player player && player.getGame().getGameFlow().isCurrentPlayer(player),
				"current player", "is");
	}

	public ROOT isActivePlayer() {
		return addTest(p -> p instanceof Player player && player.getGame().getGameFlow().isPlayerActive(player),
				"active player", "is");
	}

	public BacktrackingPlayerCheck<BASE, ROOT> controller() {
		return new BacktrackingPlayerCheck<BASE, ROOT>(root, testResults,
				p -> function.apply(p).getGame().find().playArea(function.apply(p)).getOwner());
	}

	public BacktrackingPlayerCheck<BASE, ROOT> owner() {
		return new BacktrackingPlayerCheck<BASE, ROOT>(root, testResults,
				p -> function.apply(p) instanceof BasePlayable bp ? bp.getOwner() : (Player) function.apply(p));
	}

	public ROOT visibility(VisibilityType visibility) {
		return addTest(
				p -> visibility
						.equals(p instanceof BasePlayable bp ? bp.getDefaultVisibility() : VisibilityType.PUBLIC),
				"visibility type", "is");
	}

	public BacktrackingAttributeCheck<BASE, BacktrackingPlayableCheck<BASE, ROOT>> attribute(String attributeName) {
		return new BacktrackingAttributeCheck<>(this, testResults, function, attributeName);
	}

}
