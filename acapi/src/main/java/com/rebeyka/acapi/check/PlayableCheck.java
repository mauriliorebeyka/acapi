package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;

public class PlayableCheck<BASE> extends AbstractCheck<PlayableCheck<BASE>, BASE, Playable> {

	protected PlayableCheck(List<TestResult<BASE>> testResults, Function<BASE, Playable> function) {
		super(testResults, function);
	}
	
	public StringCheck<BASE, Playable, PlayableCheck<BASE>> id() {
		return new StringCheck<>(this, p -> function.apply(p).getId(), "Playable ID");
	}
	
	public PlayableCheck<BASE> isPlayer() {
		addTest(p -> p instanceof Player, f -> f.getClass(), "Playable type", "is Player");
		return me();
	}
	
	public PlayableCheck<BASE> isCurrentPlayer() {
		addTest(p -> p.equals(p.getGame().getGameFlow().getCurrentPlayer()), f -> f.getGame().getGameFlow().getCurrentPlayer(), "Player", "is current player");
		return me();
	}
	
	public PlayableCheck<BASE> isActivePlayer() {
		addTest(p -> p instanceof Player player && p.getGame().getGameFlow().isPlayerActive(player), "Player", "is active player");
		return me();
	}
	
	public StringCheck<BASE, Playable, PlayableCheck<BASE>> attribute(String attribute) {
		return new StringCheck<>(this, p -> function.apply(p).getAttribute(attribute, Types.string()).getValue(), "string attribute %s".formatted(attribute));
	}
	
	public IntegerCheck<BASE, Playable, PlayableCheck<BASE>> attributeAsInt(String attribute) {
		return new IntegerCheck<>(this, p -> (function.apply(p).getAttribute(attribute, Types.integer())).getValue(),"integer attribute %s".formatted(attribute));
	}
}
