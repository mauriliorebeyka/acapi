package com.rebeyka.acapi.entities;

public abstract class Cost {

	public abstract CostActionable generateActionable();

	public abstract boolean isPaid();

	public static Cost FREE = new FreeCost();
}
