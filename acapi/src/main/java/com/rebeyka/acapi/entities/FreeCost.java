package com.rebeyka.acapi.entities;

import java.util.List;

import com.rebeyka.acapi.actionables.FreeCostActionable;

public class FreeCost extends Cost {

	public FreeCost(Playable origin) {
		setCostActionable(new FreeCostActionable(this));
	}

	@Override
	public boolean isPaid(List<Playable> playables) {
		return true;
	}
}
