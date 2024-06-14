package com.rebeyka.acapi.random;

import java.util.List;
import java.util.Random;

import com.rebeyka.acapi.exceptions.DieAlreadyRolledException;
import com.rebeyka.acapi.exceptions.DieNotRolledException;

public class Die<T> {

	private Random seed;

	private DieFace<T> rolledValue;

	private boolean rolled;

	private List<DieFace<T>> dieFaces;

	public Die(List<DieFace<T>> dieFaces, Random seed) {
		this.dieFaces = dieFaces;
		this.seed = seed;
		this.rolledValue = null;
		this.rolled = false;
	}

	public Die<T> roll(boolean rerollAllowed) {
		if (!rolled || rerollAllowed) {
			rolled = true;
			rolledValue = null;
			double roll = seed.nextDouble();
			double sum = 0;
			double unweightedRatio = 1 / dieFaces.stream().mapToDouble(DieFace::getWeight).sum();
			for (int i = 1; i <= dieFaces.size() && rolledValue == null; i++) {
				sum += dieFaces.get(i - 1).getWeight() * unweightedRatio;
				if (roll < sum) {
					rolledValue = dieFaces.get(i - 1);
				}
			}
		} else {
			throw new DieAlreadyRolledException();
		}
		return this;
	};

	public Die<T> roll() {
		return roll(false);
	}

	public Die<T> reroll() {
		return roll(true);
	}

	public void reset() {
		rolled = false;
		rolledValue = null;
	}

	public T getValue() {
		if (!rolled) {
			throw new DieNotRolledException();
		}
		return rolledValue.getValue();
	}

	public int getIntValue() {
		if (!rolled) {
			throw new DieNotRolledException();
		}
		return rolledValue.getIntValue();
	}

	public boolean isRolled() {
		return rolled;
	}
}
