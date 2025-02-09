package com.rebeyka.acapi.random;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DiceSet<T> implements Comparable<DiceSet<T>> {

	private List<Die<T>> dice;

	public DiceSet(List<Die<T>> dice) {
		this.dice = dice;
	}

	public void rollAll() {
		dice.stream().forEach(die -> die.roll());
	}

	public void rerollAll() {
		dice.stream().forEach(die -> die.reroll());
	}

	public int getSum(Predicate<T> filter) {
		return dice.stream().filter(die -> filter.test(die.getValue())).mapToInt(Die::getIntValue).reduce(Integer::sum)
				.orElse(0);
	}

	public int getSum() {
		return getSum(f -> true);
	}

	public List<T> getValues() {
		return dice.stream().map(Die::getValue).collect(Collectors.toList());
	}

	public Map<T, Integer> getOcurrenceMap() {
		return dice.stream().collect(Collectors.toMap(Die::getValue, t -> 1, Integer::sum));
	}
	
	public int getCount() {
		return dice.size();
	}

	@Override
	public int compareTo(DiceSet<T> o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
