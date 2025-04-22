package com.rebeyka.acapi.actionables.check;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class AbstractCheck<BASE, T> {

	private static final Logger LOG = LogManager.getLogger();

	protected Function<BASE, T> function;

	protected final List<TestResult<BASE>> testResults;

	protected AbstractCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		this.testResults = testResults;
		this.function = function;
	}

	protected void addTest(Predicate<T> predicate, Function<T, Object> valueExtractor, String field,
			String description) {
		Predicate<BASE> finalPredicate = t -> predicate.test(function.apply(t));
		Function<BASE, Object> finalValue = t -> valueExtractor.apply(function.apply(t));
		testResults.add(new TestResult<BASE>(finalPredicate, finalValue, field, description));
	}

	public final boolean check(BASE testedValue) {
		LOG.debug("Beginning checks for {}, total of {} checks", testedValue, testResults.size());
		if (testResults.isEmpty()) {
			return false;
		}
		if (LOG.isTraceEnabled()) {
			testResults.stream().forEach(t -> LOG.trace(t.getMessage(testedValue)));
		}
		long passedTests = testResults.stream().map(p -> p.test(testedValue)).filter(b -> b == true).count();
		LOG.debug("{} checks passed",passedTests);
		return passedTests == testResults.size();
	}

}
