package com.rebeyka.acapi.random;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DieBuilder<T> {

	private Seed seed;

	private List<DieFace<T>> faces;
	
	public DieBuilder<T> withSeed(Seed seed) {
		this.seed = seed;
		return this;
	}
	
	public DieBuilder<T> withRandomSeed() {
		this.seed = new Seed();
		return this;
	}

	public DieBuilder<T> withFaces(List<T> values) {
		List<DieFace<T>> faces = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			faces.add(new DieFace<T>(values.get(i), 1, i + 1));
		}
		this.faces = faces;
		return this;
	}

	@SuppressWarnings("unchecked")
	public DieBuilder<T> withFaces(T... values) {
		return withFaces(Stream.of(values).collect(Collectors.toList()));
	}

	public DieBuilder<T> addFace(T face, double weight, int intValue) {
		if (faces == null) {
			faces = new ArrayList<>();
		}
		faces.add(new DieFace<>(face, weight, intValue));
		return this;
	}

	public DieBuilder<T> addFace(T face, double weight) {
		return addFace(face, weight, 0);
	}

	public DieBuilder<T> addFace(T face) {
		return addFace(face, 1, 0);
	}

	@SuppressWarnings("unchecked")
	public DieBuilder<T> addFaces(T... faces) {
		Stream.of(faces).forEach(this::addFace);
		return this;
	}
	
	public DieBuilder<T> withWeightedValue(T value, double weight) {
		for (int i = 0; i < faces.size(); i++) {
			DieFace<T> face = faces.get(i);
			if (face.getValue().equals(value)) {
				face.setWeight(weight);
			}
		}
		return this;
	}

	public static Die<Integer> buildBasicDie(int numberOfFaces) {
		return new DieBuilder<Integer>().withRandomSeed().withFaces(IntStream.range(1, numberOfFaces+1).boxed().toList()).build();
	}

	public static DiceSet<Integer> buildBasicDiceSet(int numberOfDie, int numberOfFaces) {
		List<Die<Integer>> dice = new ArrayList<>();
		for (int i=0; i<numberOfDie; i++) {
			dice.add(buildBasicDie(numberOfFaces));
		}
		return new DiceSet<Integer>(dice);
	}
	
	public static Coin buildCoin() {
		DieBuilder<CoinSides> builder = new DieBuilder<CoinSides>().withRandomSeed().withFaces(CoinSides.values());
		return new Coin(builder.faces, builder.seed);
	}

	public Die<T> build() {
		if (seed == null) {
			throw new IllegalArgumentException("Seed not provided");
		}
		if (faces == null || faces.size() == 0) {
			throw new IllegalArgumentException("Die has no defined faces");
		}
		return new Die<T>(faces, seed);
	}

	public DiceSet<T> buildDice(int amount) {
		List<Die<T>> dice = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			dice.add(build());
		}
		return new DiceSet<T>(dice);
	}
}
