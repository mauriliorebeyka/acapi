package com.rebeyka.acapi.random;

import java.util.Random;

public class Seed {

	private static Random GLOBAL_SEED = new Random(System.nanoTime());
	
	private Random instanceSeed;
	
	public Seed() {
		instanceSeed = new Random(GLOBAL_SEED.nextLong() + System.nanoTime());
	}
	
	public Seed(String seedString) {
		long salt = seedString.hashCode();
		instanceSeed = new Random(salt);
	}
	
	public Seed(long seed) {
		instanceSeed = new Random(seed);
	}
	
	public int nextInt() {
		return instanceSeed.nextInt();
	}
	
	public int nextInt(int bound) {
		return instanceSeed.nextInt(bound);
	}
	
	public double nextDouble() {
		return instanceSeed.nextDouble();
	}
	
	public static int randomInt() {
		return GLOBAL_SEED.nextInt();
	}
	
	public static int randomInt(int bound) {
		return GLOBAL_SEED.nextInt(bound);
	}
}
