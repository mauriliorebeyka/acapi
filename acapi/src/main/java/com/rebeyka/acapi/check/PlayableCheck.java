package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.BasePlayable;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.view.VisibilityType;

public class PlayableCheck<BASE> extends AbstractCheck<PlayableCheck<BASE>, BASE, Playable> {

	protected PlayableCheck(List<TestResult<BASE>> testResults, Function<BASE, Playable> function) {
		super(testResults, function, g -> function.apply(g).getGame());
	}

	@Override
	protected PlayableCheck<BASE> self() {
		return new PlayableCheck<>(testResults, this.function);
	}

	public StringCheck<BASE, PlayableCheck<BASE>> hasId() {
		return new StringCheck<>(this, p -> function.apply(p).getId(), "Playable ID", p -> function.apply(p).getGame());
	}

	public PlayableCheck<BASE> isPlayer() {
		return addTest(p -> p instanceof Player, f -> f.getClass(), "Playable type", "is Player");
	}

	public PlayableCheck<BASE> isCurrentPlayer() {
		return addTest(p -> p.equals(p.getGame().getGameFlow().getCurrentPlayer()),
				f -> f.getGame().getGameFlow().getCurrentPlayer(), "Player", "is current player");
	}

	public PlayableCheck<BASE> isActivePlayer() {
		return addTest(p -> p instanceof Player player && p.getGame().getGameFlow().isPlayerActive(player), "Player",
				"is active player");
	}

	public PlayableCheck<BASE> visibility(VisibilityType visibility) {
		return addTest(
				p -> visibility
						.equals(p instanceof BasePlayable bp ? bp.getDefaultVisibility() : VisibilityType.PUBLIC),
				"visibility type", "is");
	}

	public AttributeCheck<BASE, PlayableCheck<BASE>> attribute(String attributeName) {
		return new AttributeCheck<>(this, testResults, function, attributeName);
	}

}
