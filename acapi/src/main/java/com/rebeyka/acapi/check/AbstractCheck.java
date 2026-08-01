package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.entities.Game;

public abstract class AbstractCheck<SELF extends AbstractCheck<SELF, ROOT, BASE, T>, ROOT extends AbstractCheck<?,?,BASE,?>, BASE, T> implements Checkable<BASE> {

	private static final Logger LOG = LogManager.getLogger();

	protected Function<BASE, T> function;

	protected List<TestResult<BASE>> testResults = new ArrayList<>();
	
	private boolean negate;
	
	protected Function<BASE, Game> gameAcessor;
	
	protected ROOT root;
	
	protected AbstractCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function, Function<BASE, Game> gameAcessor) {
		this.testResults = testResults;
		this.function = function;
		this.negate = false;
		this.gameAcessor = gameAcessor;
	}

	protected AbstractCheck(ROOT root, Function<BASE, T> function, Function<BASE, Game> gameAcessor) {
		this(root.testResults, function, gameAcessor);
		this.root = root;
	}
	
	protected AbstractCheck() {
		this(new ArrayList<>(), b -> (T) b, null);
	}
	
	protected abstract SELF self();

	public SELF always() {
		addTest(_ -> true, "always", "always");
		return self();
	}
	
	public ROOT isExactly(T other) {
		addTest(p -> p == other, "value %s".formatted(other.toString()), "is the same object");
		return (ROOT)root.self();
	}

	public ROOT isEqualsTo(T other) {
		addTest(p -> p.equals(other), "value %s".formatted(other.toString()), "is equal to");
		return (ROOT)root.self();
	}
	
	public SELF not() {
		negate = true;
		return (SELF)this;
	}

	public ROOT anyOf(List<AbstractCheck<SELF,ROOT,BASE,T>> checks) {
		Predicate<BASE> any = _ -> false;
		for (AbstractCheck<SELF,ROOT,BASE,T> check : checks) {
			Predicate<BASE> or = base -> check.check(base);
			any = any.or(or);
		}
		testResults.add(new TestResult<BASE>(any,f -> f,"any of",""));
		return (ROOT)root.self();
	}
	
	public ROOT allOf(List<AbstractCheck<SELF,ROOT,BASE,T>> checks) {
		Predicate<BASE> all = _ -> true;
		for (AbstractCheck<SELF,ROOT,BASE,T> check : checks) {
			Predicate<BASE> and = base -> check.check(base);
			all = all.and(and);
		}
		testResults.add(new TestResult<BASE>(all, f -> f,"all of",""));
		return (ROOT)root.self();
	}
	
	public ROOT custom(Predicate<T> custom) {
		return addTest(custom, "", "passes custom check");
	}

	protected ROOT addTest(Predicate<T> predicate, Function<T, Object> valueExtractor, String field, String description) {
		Predicate<BASE> finalPredicate = t -> predicate.test(function.apply(t));
		if (negate) {
			finalPredicate = finalPredicate.negate();
			description = "not "+description;
		}
		Function<BASE, ?> finalValue = t -> valueExtractor.apply(function.apply(t));
		testResults.add(new TestResult<BASE>(finalPredicate, finalValue, field, description));
		return (ROOT)root.self();
	}
	
	protected ROOT addTest(Predicate<T> predicate, String field, String description) {
		return addTest(predicate, v -> v, field, description);
	}
	
	public final boolean check(BASE testedValue) {
		if (testResults.isEmpty()) {
			LOG.warn("No checks configured, failing check for {}",testedValue);
			return false;
		}
		if (LOG.isTraceEnabled()) {
			testResults.stream().forEach(t -> LOG.trace(t.getMessage(testedValue)));
		}
		long passedTests = testResults.stream().map(p -> p.test(testedValue)).filter(b -> b == true).count();
		return passedTests == testResults.size();
	}

	public GameCheck<BASE,ROOT> game() {
		return new GameCheck<BASE,ROOT>(root, gameAcessor);
	}
	
	public TimelineCheck<BASE, ? extends AbstractCheck<?,?,BASE,T>, T> happened() {
		return happened("");
	}
	
	public TimelineCheck<BASE, ? extends AbstractCheck<?,?,BASE,T>, T> happened(String actionableId) {
		return new TimelineCheck<>(this, gameAcessor, actionableId);
	}
	
	
}
