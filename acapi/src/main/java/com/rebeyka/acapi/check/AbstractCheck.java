package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unchecked")
public abstract class AbstractCheck<ROOT extends AbstractCheck<?,BASE,?>, BASE, T>
		extends Checkable<BASE> {

	private static final Logger LOG = LogManager.getLogger();

	protected Function<BASE, T> function;

	protected ROOT root;

	protected AbstractCheck(AbstractCheck<?,BASE,?> base, Function<BASE, T> function) {
		LOG.trace("New instance {} with root {} and {} tests",this,base,base != null ? base.testResults.size() : 0);
		this.testResults = new ArrayList<>();
		this.function = function;
		this.negate = false;
		if (base == null) {
			this.root = (ROOT) this;
			this.negate = false;
		} else {
			this.root = (ROOT)((AbstractCheck<?,BASE,?>)base).root;
			this.testResults.addAll(base.testResults);
			this.negate = base.negate;
		}
	}
	
	protected AbstractCheck(Function<BASE, T> function) {
		this(null, function);
	}
	
	public ROOT always() {
		return addTest(_ -> true, "always", "true");
	}

	public ROOT isExactly(T other) {
		return addTest(p -> p == other, other == null ? "" : other.toString(), "exactly");
	}

	public ROOT isEqualsTo(T other) {
		return addTest(p -> p.equals(other), other.toString(), "equals to");
	}

	public ROOT not() {
		AbstractCheck<?,BASE,?> newRoot = ((RootChecker<BASE,?>)root).self();
		newRoot.negate = !newRoot.negate;
		newRoot.testResults = new ArrayList<>(testResults);
		return (ROOT)newRoot;
	}

	@SafeVarargs
	public final ROOT anyOf(AbstractCheck<?, T, ?>... checks) {
		Predicate<T> any = _ -> false;
		String message = "any of (";
		for (AbstractCheck<?, T, ?> check : checks) {
			any = check.testResults.stream().map(TestResult::getPredicate).reduce(any, Predicate::or);
			message = check.testResults.stream().map(TestResult::getBaseMessage).reduce(message,
					(l, r) -> l + r + ", ");
		}
		message = message.substring(0, message.length() - 2) + ")";
		return addTest(any, "", message);
	}

	@SafeVarargs
	public final ROOT allOf(AbstractCheck<?, T, ?>... checks) {
		Predicate<T> all = _ -> true;
		String message = "all of (";
		for (AbstractCheck<?, T, ?> check : checks) {
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
		AbstractCheck<?,BASE,?> newRoot = ((RootChecker) root).self();
		newRoot.testResults.addAll(root.testResults);
		newRoot.testResults.addAll(newTests);
		LOG.trace("new Root {} now contain {} tests",newRoot, newRoot.testResults.size());
		newRoot.negate = false;
		return (ROOT) newRoot;
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
