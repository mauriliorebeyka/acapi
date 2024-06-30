package com.rebeyka.acapi.entities;

import com.rebeyka.acapi.actionables.CostActionable;

public abstract class Cost {

	public abstract CostActionable generateActionable();

	public abstract boolean isPaid();

	public static Cost FREE = new FreeCost();
}
