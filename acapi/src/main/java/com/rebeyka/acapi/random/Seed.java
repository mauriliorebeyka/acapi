package com.rebeyka.acapi.random;

import java.util.Random;

public class Seed {

	private static Random GLOBAL_SEED = new Random(System.nanoTime());
	
	private Random instanceSeed;
	
	//TODO Add a way to get either the long or String for the seed
	private String seedString;
	
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
