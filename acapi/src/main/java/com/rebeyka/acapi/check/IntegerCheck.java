package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

/**
 * An integer value checker that can be used in two ways:
 * 
 * 1. As a child checker (default behavior):
 *    - AttributeCheck.asInt().biggerThan(10) returns AttributeCheck
 *    - Methods chain back to the parent ROOT type
 * 
 * 2. As a standalone checker:
 *    - Use IntegerCheck.standalone(...).biggerThan(10) to continue chaining
 *    - Methods chain on IntegerCheck itself
 *    - No separate class needed - IntegerCheck handles both modes!
 * 
 * @param <BASE> The base type from which an integer value is extracted
 * @param <ROOT> The root checker type (parent checker when used as child, IntegerCheck when standalone)
 */
public class IntegerCheck<BASE, ROOT extends AbstractCheck<?, BASE, ?>>
		extends RootCheck<IntegerCheck<BASE, ROOT>, BASE, Integer, ROOT> {

	protected IntegerCheck(ROOT root, Function<BASE, Integer> function, String testedField, Function<BASE,Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}

	/**
	 * Constructor for self-rooting mode (standalone checker).
	 * Pass testResults and the self-rooting will be set up.
	 */
	protected IntegerCheck(List<TestResult<BASE>> testResults, Function<BASE, Integer> function, String testedField,
			Function<BASE, Game> gameAcessor) {
		super(testResults, function, testedField, gameAcessor);
	}
	
	@Override
	protected IntegerCheck<BASE, ROOT> self() {
		return new IntegerCheck<>(root, function, testedField, gameAcessor);
	}

	public ROOT biggerThan(int value) {
		return addValueTest(i -> i > value, "is bigger than %s".formatted(value));
	}

	public ROOT lessThan(int value) {
		return addValueTest(i -> i < value, "is less than %s".formatted(value));
	}

	public ROOT between(int start, int end) {
		return addValueTest(i -> i >= start && i <= end, "between %s and %s".formatted(start, end));
	}

	/**
	 * Factory method to create a standalone IntegerCheck that chains on itself.
	 * No need for a separate class - IntegerCheck handles both modes!
	 * 
	 * Example:
	 * var checker = IntegerCheck.standalone(s -> s.length(), "length", null);
	 * var result = checker.biggerThan(5).lessThan(20);
	 * 
	 * @param <B> The base type that the function extracts an integer from
	 * @param function The function to extract an integer value from the base type
	 * @param testedField The name of the field being tested
	 * @param gameAcessor Function to access the Game from the base type
	 * @return A new IntegerCheck configured to self-root and chain on itself
	 */
	@SuppressWarnings("unchecked")
	public static <B> IntegerCheck<B, IntegerCheck<B, ?>> standalone(Function<B, Integer> function, String testedField,
			Function<B, Game> gameAcessor) {
		IntegerCheck<B, IntegerCheck<B, ?>> checker = (IntegerCheck<B, IntegerCheck<B, ?>>) (Object) new IntegerCheck<>(new ArrayList<>(), function, testedField, gameAcessor);
		checker.root = checker;
		return checker;
	}
}
