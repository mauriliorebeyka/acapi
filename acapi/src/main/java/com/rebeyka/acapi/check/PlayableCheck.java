package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.BasePlayable;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.view.VisibilityType;

public class PlayableCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<PlayableCheck<BASE, ROOT>, ROOT, BASE, Playable> {

	protected PlayableCheck(ROOT root, Function<BASE, Playable> function) {
		super(root, function, g -> function.apply(g).getGame());
	}

	protected PlayableCheck(List<TestResult<BASE>> testResults, Function<BASE, Playable> function) {
		super(testResults, function, g -> function.apply(g).getGame());
	}

	@Override
	protected PlayableCheck<BASE, ROOT> self() {
		return new PlayableCheck<>(root, this.function);
	}

	public StringCheck<BASE, ROOT> hasId() {
		return new StringCheck<>(root, p -> function.apply(p).getId(), gameAcessor);
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

	public PlayerCheck<BASE, ROOT> controller() {
		return new PlayerCheck<BASE, ROOT>(testResults,
				p -> function.apply(p).getGame().find().playArea(function.apply(p)).getOwner(), gameAcessor, root);
	}

	public PlayerCheck<BASE, ROOT> owner() {
		return new PlayerCheck<BASE, ROOT>(testResults,
				p -> function.apply(p) instanceof BasePlayable bp ? bp.getOwner() : (Player) function.apply(p),
				gameAcessor, root);
	}

	public ROOT visibility(VisibilityType visibility) {
		return addTest(
				p -> visibility
						.equals(p instanceof BasePlayable bp ? bp.getDefaultVisibility() : VisibilityType.PUBLIC),
				"visibility type", "is");
	}

	public AttributeCheck<BASE, PlayableCheck<BASE, ROOT>> attribute(String attributeName) {
		return new AttributeCheck<>(this, testResults, function, attributeName);
	}

}
