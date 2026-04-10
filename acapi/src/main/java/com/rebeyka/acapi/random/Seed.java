package com.rebeyka.acapi.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Seed {

	private static Seed GLOBAL_SEED = new Seed();
	
	private Random instanceSeed;
	
	private List<Long> memory;
	
	private int memoryPosition;
	
	public Seed() {
		this(System.nanoTime());
	}
	
	public Seed(String seedString) {
		this(seedString.hashCode());
	}
	
	public Seed(long seed) {
		memory = new ArrayList<>();
		memoryPosition = 0;
		instanceSeed = new Random(seed);
	}
	
	public double nextDouble() {
		return Double.longBitsToDouble(nextValue());
	}

	public int nextInt(int bound) {
		int value = (int)nextValue();
		value = value < 0 ? -value : value;
		return (int)value % bound;
	}
	
	public boolean xInY(double x, double y) {
		if (y <= 0) {
			throw new IllegalArgumentException("Chance must be greater than 0");
		}
		double value = nextDouble();
		return value < x / y;
	}
	
	public boolean oneIn(double y) {
		return xInY(1, y);
	}
	
	public void rollback() {
		if (memoryPosition == 0) {
			throw new IllegalStateException("No value to rollback to");
		}
		memoryPosition--;
	}
	
	private long nextValue() {
		if (memoryPosition == memory.size()) {
			memory.add(Double.doubleToRawLongBits(instanceSeed.nextDouble()));
		}
		memoryPosition++;
		
		return memory.get(memoryPosition-1);
	}
	
	public static int randomInt(int bound) {
		return GLOBAL_SEED.instanceSeed.nextInt(bound);
	}
}
