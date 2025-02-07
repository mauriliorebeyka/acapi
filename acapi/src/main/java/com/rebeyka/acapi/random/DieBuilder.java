package com.rebeyka.acapi.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DieBuilder<T> {

	private Random seed;

	private List<DieFace<T>> faces;
	
	private static Random globalSeed = new Random(System.nanoTime());

	public DieBuilder<T> withSeed(Random seed) {
		this.seed = seed;
		return this;
	}

	public DieBuilder<T> withSeed(String seedString) {
		long salt = seedString.hashCode();
		this.seed = new Random(salt);
		return this;
	}
	
	public DieBuilder<T> withRandomSeed() {
		long salt = 0;
		synchronized (globalSeed) {
			salt = globalSeed.nextLong();
		}
		this.seed = new Random(System.nanoTime() + salt);
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

	public DieBuilder<T> withFaces(T... values) {
		return withFaces(Stream.of(values).collect(Collectors.toList()));
	}

	public DieBuilder<T> withFaces(int numberOfFaces) {
		return withFaces(IntStream.range(1, numberOfFaces + 1).boxed().map(i -> (T) i).collect(Collectors.toList()));
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
		return new DieBuilder<Integer>().withRandomSeed().withFaces(numberOfFaces).build();
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
