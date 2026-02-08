package com.rebeyka.acapi.check;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractCheck<SELF extends AbstractCheck<SELF, BASE, T>, BASE, T> implements Checkable<BASE> {

	private static final Logger LOG = LogManager.getLogger();

	protected Function<BASE, T> function;

	protected List<TestResult<BASE>> testResults = new ArrayList<>();
	
	private boolean negate;
	
	protected AbstractCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		this.testResults = testResults == null ? new ArrayList<>() : testResults;
		this.function = function;
		this.negate = false;
	}

	@SuppressWarnings("unchecked")
	protected SELF me(boolean newInstance) {
		if (newInstance) {
			try {
				return (SELF) this.getClass().getDeclaredConstructor(List.class, Function.class)
						.newInstance(testResults, this.function);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				LOG.atWarn().withThrowable(e).log("Failed to create new instance of {}", this.getClass());
			}
		}
		return (SELF) this;
	}

	protected SELF me() {
		return me(true);
	}

	public SELF always() {
		addTest(_ -> true, "always", "always");
		return me();
	}
	
	public SELF is(T other) {
		addTest(p -> p == other, "value %s".formatted(other.toString()), "is the same object");
		return me();
	}

	public SELF not() {
		negate = true;
		return me(false);
	}

	public SELF custom(Predicate<T> custom) {
		addTest(custom, "", "passes custom check");
		return me();
	}
	
	protected void addTest(Predicate<T> predicate, Function<T, Object> valueExtractor, String field,
			String description) {
		Predicate<BASE> finalPredicate = t -> predicate.test(function.apply(t));
		if (negate) {
			finalPredicate = finalPredicate.negate();
			description = "not "+description;
		}
		Function<BASE, Object> finalValue = t -> valueExtractor.apply(function.apply(t));
		testResults.add(new TestResult<BASE>(finalPredicate, finalValue, field, description));
	}

	protected void addTest(Predicate<T> predicate, String field, String description) {
		addTest(predicate, t -> t, field, description);
	}
	
	public final boolean check(BASE testedValue) {
		if (testResults.isEmpty()) {
			LOG.warn("No checks configured, failing check for {}",testedValue);
			return false;
		}
		LOG.debug("Beginning checks for {}, total of {} checks", testedValue, testResults.size());
		if (LOG.isTraceEnabled()) {
			testResults.stream().forEach(t -> LOG.trace(t.getMessage(testedValue)));
		}
		long passedTests = testResults.stream().map(p -> p.test(testedValue)).filter(b -> b == true).count();
		LOG.debug("Ending checks for {}, {}/{} checks passed", testedValue, passedTests,testResults.size());
		return passedTests == testResults.size();
	}

}
