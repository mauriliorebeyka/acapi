package com.rebeyka.acapi.actionables.check;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class PlayableCheck<BASE> extends AbstractCheck<BASE, Playable> {

	protected PlayableCheck(List<TestResult<BASE>> testResults, Function<BASE, Playable> function) {
		super(testResults, function);
	}

	private PlayableCheck<BASE> me() {
		return new PlayableCheck<BASE>(testResults, function);
	}
	
	public StringCheck<BASE, Playable, PlayableCheck<BASE>> id() {
		return new StringCheck<>(this, Playable::getId, "Playable ID");
	}
	
	public PlayableCheck<BASE> isPlayer() {
		addTest(p -> p instanceof Player, f -> f.getClass(), "Playable type", "is Player");
		return me();
	}
	
	public PlayableCheck<BASE> isCurrentPlayer() {
		addTest(p -> p.getGame().getGameFlow().getCurrentPlayer().equals(p), f -> f.getGame().getGameFlow().getCurrentPlayer(), "Current Player", "is");
		return me();
	}
	
	public PlayableCheck<BASE> isActivePlayer() {
		addTest(p -> p instanceof Player player && p.getGame().getGameFlow().isPlayerActive(player), f -> f, "Active Player", "is");
		return me();
	}
	
	public StringCheck<BASE, Playable, PlayableCheck<BASE>> attribute(String attribute) {
		return new StringCheck<>(this, p -> p.getAttribute(attribute).get(), "string attribute '%s'".formatted(attribute));
	}
	
	public IntegerCheck<BASE, Playable, PlayableCheck<BASE>> attributeAsInt(String attribute) {
		return new IntegerCheck<>(this, p -> ((SimpleIntegerAttribute)p.getAttribute(attribute)).getValue(), "integer attribute '%s'".formatted(attribute));
	}
}
