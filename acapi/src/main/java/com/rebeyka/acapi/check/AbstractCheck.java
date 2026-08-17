package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractCheck<ROOT extends Checkable<BASE> & RootChecker<BASE, ROOT>, BASE, T>
		extends Checkable<BASE> {

	private static final Logger LOG = LogManager.getLogger();

	protected Function<BASE, T> function;

	protected ROOT root;

	@SuppressWarnings("unchecked")
	protected AbstractCheck(Checkable<BASE> base, Function<BASE, T> function) {
		LOG.trace("New instance {} with root {} and {} tests", this, base, base != null ? base.testResults.size() : 0);
		this.testResults = new ArrayList<>();
		this.function = function;
		this.negate = false;
		this.root = (ROOT) this;
		if (base != null) {
			this.root = (ROOT) ((AbstractCheck<?, BASE, ?>) base).root;
			this.testResults.addAll(base.testResults);
			this.negate = base.negate;
		}
	}

	public ROOT always() {
		return addTest(_ -> true, "always", "true");
	}

	public ROOT isExactly(T other) {
		return addTest(p -> p == other, other == null ? "null" : other.toString(), "exactly");
	}

	public ROOT isEqualsTo(T other) {
		return addTest(p -> p.equals(other), other == null ? "null" : other.toString(), "equals to");
	}

	public ROOT not() {
		ROOT newRoot = root.self();
		newRoot.negate = !negate;
		newRoot.testResults = new ArrayList<>(testResults);
		return newRoot;
	}

	@SafeVarargs
	public final ROOT anyOf(Checkable<T>... checks) {
		Predicate<T> any = Stream.of(checks).flatMap(c -> c.testResults.stream()).map(TestResult::getPredicate).reduce(
				Predicate::or).orElse(_ -> false);
		String message = Stream.of(checks).flatMap(c -> c.testResults.stream()).map(TestResult::getBaseMessage)
				.collect(Collectors.joining(", ","any of (",")"));
		return addTest(any, "", message);
	}

	@SafeVarargs
	public final ROOT allOf(Checkable<T>... checks) {
		Predicate<T> all = Stream.of(checks).flatMap(c -> c.testResults.stream()).map(TestResult::getPredicate).reduce(
				Predicate::and).orElse(_ -> false);
		String message = Stream.of(checks).flatMap(c -> c.testResults.stream()).map(TestResult::getBaseMessage)
				.collect(Collectors.joining(", ","any of (",")"));
		return addTest(all, "", message);
	}

	public ROOT custom(Predicate<T> custom) {
		return addTest(custom, "", "passes custom check");
	}

	protected ROOT addTest(Predicate<T> predicate, Function<T, Object> valueExtractor, String field,
			String description) {
		LOG.trace("Adding {}test {} {} to {} - {} tests", negate ? "NOT " : "", field, description, this,
				testResults.size() + 1);
		Predicate<BASE> finalPredicate = t -> predicate.test(function.apply(t));
		if (negate) {
			finalPredicate = finalPredicate.negate();
			description = "not " + description;
		}
		Function<BASE, ?> finalValue = t -> valueExtractor.apply(function.apply(t));
		List<TestResult<BASE>> newTests = new ArrayList<>(testResults);
		newTests.add(new TestResult<BASE>(finalPredicate, finalValue, field, description));
		ROOT newRoot = root.self();
		newRoot.testResults.addAll(root.testResults);
		newRoot.testResults.addAll(newTests);
		LOG.trace("new Root {} now contain {} tests", newRoot, newRoot.testResults.size());
		newRoot.negate = false;
		return newRoot;
	}

	protected ROOT addTest(Predicate<T> predicate, String field, String description) {
		return addTest(predicate, v -> v, field, description);
	}

	public final boolean check(BASE testedValue) {
		LOG.trace("Testing {}", this);
		if (testedValue == null) {
			throw new IllegalArgumentException("tested value must not be null");
		}
		if (testResults.isEmpty()) {
			LOG.warn("No checks configured, failing check for {}", testedValue);
			return false;
		}
		if (LOG.isTraceEnabled()) {
			testResults.stream().forEach(t -> LOG.trace(t.getMessage(testedValue)));
		}
		long passedTests = testResults.stream().map(p -> p.test(testedValue)).filter(b -> b == true).count();
		return passedTests == testResults.size();
	}

}
