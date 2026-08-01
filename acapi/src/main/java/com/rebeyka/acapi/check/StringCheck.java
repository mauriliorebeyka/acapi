package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

/**
 * A string value checker that can be used in two ways:
 * 
 * 1. As a child checker (default behavior): -
 * PlayableCheck.hasId().contains("ID") returns PlayableCheck - Methods chain
 * back to the parent ROOT type
 * 
 * 2. As a standalone checker: - Use StringCheck.standalone(...).contains("ID")
 * to continue chaining - Methods chain on StringCheck itself - No separate
 * class needed - StringCheck handles both modes!
 * 
 * @param <BASE> The base type from which a string value is extracted
 * @param <ROOT> The root checker type (parent checker when used as child,
 *               StringCheck when standalone)
 */
public class StringCheck<BASE, ROOT extends AbstractCheck<?, BASE, ?>>
		extends RootCheck<StringCheck<BASE, ROOT>, BASE, String, ROOT> {

	protected StringCheck(ROOT root, Function<BASE, String> function, String testedField,
			Function<BASE, Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}

	/**
	 * Constructor for self-rooting mode (standalone checker). Pass testResults and
	 * the self-rooting will be set up.
	 */
	protected StringCheck(List<TestResult<BASE>> testResults, Function<BASE, String> function, String testedField,
			Function<BASE, Game> gameAcessor) {
		super(testResults, function, testedField, gameAcessor);
	}

	@Override
	protected StringCheck<BASE, ROOT> self() {
		return new StringCheck<>(root, function, testedField, gameAcessor);
	}

	public ROOT contains(String value) {
		return addValueTest(s -> s.contains(value), "contains %s".formatted(value));
	}

}
