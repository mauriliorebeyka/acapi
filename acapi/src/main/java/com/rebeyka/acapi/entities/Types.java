package com.rebeyka.acapi.entities;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.rebeyka.acapi.random.DiceSet;

public class Types {

	public static final <T> TypeToken<T> of(Class<T> clazz) {
		return TypeToken.of(clazz);
	}
	
	public static final <T> TypeToken<DiceSet<T>> diceSetOf(TypeToken<T> typeToken) {
		return new TypeToken<DiceSet<T>>() {
			private static final long serialVersionUID = -2517161861049526012L;
		}.where(new TypeParameter<T>() {
		}, typeToken);
	}

	public static final <T> TypeToken<DiceSet<T>> diceSetOf(Class<T> clazz) {
		return diceSetOf(TypeToken.of(clazz));
	}

	@SuppressWarnings("unchecked")
	public static final <T> TypeToken<DiceSet<T>> diceSetOf(DiceSet<T> base) {
		T value = base.getValues().getFirst();
		Class<T> clazz = (Class<T>) value.getClass();
		return diceSetOf(clazz);
	}
	
	public static final TypeToken<Integer> integer() {
		return TypeToken.of(Integer.class);
	}

	public static final TypeToken<Integer> INT = integer();
	
	public static TypeToken<String> string() {
		return TypeToken.of(String.class);
	}
	
	public static final TypeToken<String> STRING = string();
}
