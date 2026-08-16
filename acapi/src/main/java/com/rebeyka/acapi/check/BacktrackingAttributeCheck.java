package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;

public class BacktrackingAttributeCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>>
		extends ValueCheck<BASE, ROOT, Playable> {

	private String attributeName;

	protected Function<Playable, Attribute<?>> attributeAcessor;

	protected Function<Attribute<?>, ?> attributeValueAcessor;

	protected BacktrackingAttributeCheck(AbstractCheck<?,BASE,?> root, Function<BASE, Playable> function,
			String attributeName) {
		super(root, function);
		this.attributeName = attributeName;
		this.prepareFunctions(p -> p.getAttribute(attributeName), v -> v.getValue());
	}

	protected BacktrackingAttributeCheck<BASE, ROOT> self(Function<Playable, Attribute<?>> attributeAcessor,
			Function<Attribute<?>, ?> valueAcessor) {
		BacktrackingAttributeCheck<BASE, ROOT> newInstance = new BacktrackingAttributeCheck<>(this, function, attributeName);
		newInstance.prepareFunctions(attributeAcessor, valueAcessor);
		;
		return newInstance;
	}

//	@Override
//	protected BacktrackingAttributeCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
//		BacktrackingAttributeCheck<BASE, ROOT> newInstanceCheck = new BacktrackingAttributeCheck<>(root, testResults, function, attributeName);
//		newInstanceCheck.attributeAcessor = this.attributeAcessor;
//		newInstanceCheck.attributeValueAcessor = this.attributeValueAcessor;
//		newInstanceCheck.prepareFunctions(this.attributeAcessor, this.attributeValueAcessor);
//		return newInstanceCheck;
//	}

	private void prepareFunctions(Function<Playable, Attribute<?>> attributeAcessor,
			Function<Attribute<?>, ?> valueAcessor) {
		this.attributeAcessor = attributeAcessor;
		this.attributeValueAcessor = valueAcessor;
		this.valueAcessor = attributeAcessor.andThen(attributeValueAcessor);
	}

	public BacktrackingAttributeCheck<BASE, ROOT> raw() {
		return self(p -> p.getRawAttribute(attributeName), attributeValueAcessor);
	}

	public BacktrackingAttributeCheck<BASE, ROOT> initial() {
		return self(attributeAcessor, v -> v.getInitialValue());
	}

	public BacktrackingAttributeCheck<BASE, ROOT> min() {
		return self(attributeAcessor, v -> v.getMinValue());
	}

	public BacktrackingAttributeCheck<BASE, ROOT> max() {
		return self(attributeAcessor, v -> v.getMaxValue());
	}

	public ROOT exists() {
		addTest(p -> attributeAcessor.apply(p) != null, attributeName, "exists");
		return root;
	}

}
