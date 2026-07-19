package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;

public class AttributeCheck<BASE, ROOT extends AbstractCheck<?, BASE, ?>>
		extends ValueCheck<AttributeCheck<BASE, ROOT>, BASE, Playable, ROOT> {

	private String attributeName;

	protected Function<Playable, Attribute<?>> attributeAcessor;

	protected Function<Attribute<?>, ?> attributeValueAcessor;

	protected AttributeCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Playable> function,
			String attributeName) {
		super(root, a -> function.apply(a), attributeName, g -> function.apply(g).getGame());
		this.attributeName = attributeName;
		this.prepareFunctions(p -> p.getAttribute(attributeName), v -> v.getValue());
	}

	protected AttributeCheck<BASE, ROOT> self(Function<Playable, Attribute<?>> attributeAcessor,
			Function<Attribute<?>, ?> valueAcessor) {
		AttributeCheck<BASE, ROOT> newInstance = new AttributeCheck<>(root, testResults, function, attributeName);
		newInstance.prepareFunctions(attributeAcessor, valueAcessor);
		;
		return newInstance;
	}

	@Override
	protected AttributeCheck<BASE, ROOT> self() {
		AttributeCheck<BASE, ROOT> newInstanceCheck = new AttributeCheck<>(root, testResults, function, attributeName);
		newInstanceCheck.attributeAcessor = this.attributeAcessor;
		newInstanceCheck.attributeValueAcessor = this.attributeValueAcessor;
		newInstanceCheck.prepareFunctions(this.attributeAcessor, this.attributeValueAcessor);
		return newInstanceCheck;
	}

	private void prepareFunctions(Function<Playable, Attribute<?>> attributeAcessor,
			Function<Attribute<?>, ?> valueAcessor) {
		this.attributeAcessor = attributeAcessor;
		this.attributeValueAcessor = valueAcessor;
		this.valueAcessor = attributeAcessor.andThen(attributeValueAcessor);
	}

	public AttributeCheck<BASE, ROOT> raw() {
		return self(p -> p.getRawAttribute(attributeName), attributeValueAcessor);
	}

	public AttributeCheck<BASE, ROOT> initial() {
		return self(attributeAcessor, v -> v.getInitialValue());
	}

	public AttributeCheck<BASE, ROOT> min() {
		return self(attributeAcessor, v -> v.getMinValue());
	}

	public AttributeCheck<BASE, ROOT> max() {
		return self(attributeAcessor, v -> v.getMaxValue());
	}

	public ROOT exists() {
		addTest(p -> attributeAcessor.apply(p) != null, attributeName, "exists");
		return root;
	}

}
