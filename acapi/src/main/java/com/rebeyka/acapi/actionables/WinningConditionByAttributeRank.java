package com.rebeyka.acapi.actionables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class WinningConditionByAttributeRank extends WinningCondition {

	private String attributeName;
	
	private List<Player> rank;
	
	public WinningConditionByAttributeRank(Game game, String attributeName) {
		super(game);
		this.attributeName = attributeName;
	}

	@Override
	public void execute() {
		Comparator<Player> comparator = Comparator.comparing(p -> p.getAttribute(attributeName));
		rank = new ArrayList<>(game.getPlayers());
		rank.sort(comparator.reversed());
	}

	@Override
	public String getMessage() {
		StringBuilder fullRank = new StringBuilder();
		rank.forEach(p -> fullRank.append("%s - %s %s ".formatted(p, p.getAttribute(attributeName).getValue(), attributeName)));
		Player winner = rank.get(0);
		Attribute<?> attribute = winner.getAttribute(attributeName);
		return "Winner is %s with %s %s. Full rank is %s".formatted(winner,attribute.getValue(),attributeName, fullRank);
	}

	@Override
	public Supplier<Actionable> supply() {
		return () -> new WinningConditionByAttributeRank(game, attributeName);
	}
}
