package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unchecked")
public abstract class AbstractCheck<SELF extends AbstractCheck<SELF, ROOT, BASE, T>, ROOT extends AbstractCheck<?, ?, BASE, ?>, BASE, T>
		implements Checkable<BASE> {

	private static final Logger LOG = LogManager.getLogger();

	protected Function<BASE, T> function;

	protected List<TestResult<BASE>> testResults = new ArrayList<>();

	private boolean negate;

	protected ROOT root;

	protected AbstractCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		LOG.trace("New Instance {}",this);
		this.testResults = new ArrayList<TestResult<BASE>>(testResults);
		this.function = function;
		this.negate = false;
		this.root = (ROOT) this;
	}

	protected AbstractCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		this(testResults, function);
		LOG.trace("Using ROOT {}", root);
		this.root = root;
	}

	protected abstract SELF self(List<TestResult<BASE>> testResults);

	public ROOT always() {
		return addTest(_ -> true, "always", "true");
	}

	public ROOT isExactly(T other) {
		return addTest(p -> p == other, other.toString(), "exactly");
	}

	public ROOT isEqualsTo(T other) {
		return addTest(p -> p.equals(other), other.toString(), "equals to");
	}

	public SELF not() {
		negate = !negate;
		return (SELF) this;
	}

	@SafeVarargs
	public final ROOT anyOf(AbstractCheck<?, ?, T, ?>... checks) {
		Predicate<T> any = _ -> false;
		String message = "any of (";
		for (AbstractCheck<?, ?, T, ?> check : checks) {
			any = check.testResults.stream().map(TestResult::getPredicate).reduce(any, Predicate::or);
			message = check.testResults.stream().map(TestResult::getBaseMessage).reduce(message,
					(l, r) -> l + r + ", ");
		}
		message = message.substring(0, message.length() - 2) + ")";
		return addTest(any, "", message);
	}

	@SafeVarargs
	public final ROOT allOf(AbstractCheck<?, ?, T, ?>... checks) {
		Predicate<T> all = _ -> true;
		String message = "all of (";
		for (AbstractCheck<?, ?, T, ?> check : checks) {
			all = check.testResults.stream().map(TestResult::getPredicate).reduce(all, Predicate::and);
			message = check.testResults.stream().map(TestResult::getBaseMessage).reduce(message,
					(l, r) -> l + r + ", ");
		}
		message = message.substring(0, message.length() - 2) + ")";
		return addTest(all, "", message);
	}

	public ROOT custom(Predicate<T> custom) {
		return addTest(custom, "", "passes custom check");
	}

	protected ROOT addTest(Predicate<T> predicate, Function<T, Object> valueExtractor, String field,
			String description) {
		LOG.trace("Adding {}test {} {} to {} - {} tests", negate ? "NOT " : "", field, description,this,testResults.size()+1);
		Predicate<BASE> finalPredicate = t -> predicate.test(function.apply(t));
		if (negate) {
			finalPredicate = finalPredicate.negate();
			description = "not " + description;
		}
		Function<BASE, ?> finalValue = t -> valueExtractor.apply(function.apply(t));
		List<TestResult<BASE>> newTests = new ArrayList<>(testResults);
		newTests.add(new TestResult<BASE>(finalPredicate, finalValue, field, description));
		return (ROOT) root.self(newTests);
	}

	protected ROOT addTest(Predicate<T> predicate, String field, String description) {
		return addTest(predicate, v -> v, field, description);
	}

	public final boolean check(BASE testedValue) {
		LOG.trace("Testing {}",this);
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
