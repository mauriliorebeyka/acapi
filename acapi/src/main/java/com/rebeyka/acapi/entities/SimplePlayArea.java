package com.rebeyka.acapi.entities;

import java.util.Set;

public class SimplePlayArea<T extends BasePlayable> extends PlayArea<Set<T>, T>{

	public SimplePlayArea(String id, Player owner) {
		super(id, owner);
	}

}
