package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class PlayableCheck extends TypedActionableCheck<Playable> {

	protected PlayableCheck(Map<String, Predicate<Actionable>> tests, Function<Actionable, Playable> function) {
		super(tests, function);
	}

	private PlayableCheck me() {
		return new PlayableCheck(tests, function);
	}
	
	public PlayableCheck isPlayer() {
		tests.put("IS PLAYER", p -> function.apply(p) instanceof Player);
		return me();
	}
	
	public PlayableCheck isCurrentPlayer() {
		tests.put("CURRENT PLAYER", p -> p.getParent().getGame().getGameFlow().getCurrentPlayer().equals(function.apply(p)));
		return me();
	}
	
	public PlayableCheck isActivePlayer() {
		tests.put("ACTIVE PLAYER", p -> p.getParent().getGame().getGameFlow().isPlayerActive((Player)function.apply(p)));
		return me();
	}
	
	public StringCheck<Playable, PlayableCheck> attribute(String attribute) {
		return new StringCheck<>(tests,p -> p.getParent().getOrigin().getAttribute(attribute).get(), "Origin attribute %s".formatted(attribute), this);
	}
	
	public IntegerCheck<Playable, PlayableCheck> attributeAsInt(String attribute) {
		return new IntegerCheck<Playable, PlayableCheck>(tests, p -> ((SimpleIntegerAttribute)p.getParent().getOrigin().getAttribute(attribute)).getValue(), this);
	}
}
