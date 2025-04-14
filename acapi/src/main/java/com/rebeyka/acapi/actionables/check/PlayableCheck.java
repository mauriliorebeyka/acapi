package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class PlayableCheck<BASE> extends AbstractCheck<BASE, Playable> {

	protected PlayableCheck(Map<String, Predicate<BASE>> tests, Function<BASE, Playable> function) {
		super(tests, function);
	}

	private PlayableCheck<BASE> me() {
		return new PlayableCheck<BASE>(tests, function);
	}
	
	public StringCheck<BASE, Playable, PlayableCheck<BASE>> id() {
		return new StringCheck<>(tests, Playable::getId, "Playable ID", this);
	}
	
	public PlayableCheck<BASE> isPlayer() {
		tests.put("IS PLAYER", p -> function.apply(p) instanceof Player);
		return me();
	}
	
	public PlayableCheck<BASE> isCurrentPlayer() {
		addTest("CURRENT PLAYER", p -> p.getGame().getGameFlow().getCurrentPlayer().equals(p));
		return me();
	}
	
	public PlayableCheck<BASE> isActivePlayer() {
		addTest("ACTIVE PLAYER", p -> p instanceof Player player && p.getGame().getGameFlow().isPlayerActive(player));
		return me();
	}
	
	public StringCheck<BASE, Playable, PlayableCheck<BASE>> attribute(String attribute) {
		return new StringCheck<>(tests,p -> p.getAttribute(attribute).get(), "Origin attribute %s".formatted(attribute), this);
	}
	
	public IntegerCheck<BASE, Playable, PlayableCheck<BASE>> attributeAsInt(String attribute) {
		return new IntegerCheck<>(tests, p -> ((SimpleIntegerAttribute)p.getAttribute(attribute)).getValue(), attribute, this);
	}
}
