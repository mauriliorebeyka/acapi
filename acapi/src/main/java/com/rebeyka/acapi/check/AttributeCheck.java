package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;

public class AttributeCheck<BASE, ROOT extends AbstractCheck<?, BASE, ?>>
		extends ValueCheck<AttributeCheck<BASE, ROOT>, BASE, Playable, ROOT> {

	private String attributeName;

	protected Function<Playable, Attribute<?>> attributeAcessor;

	protected Function<Attribute<?>, ?> valueAcessor;

	protected AttributeCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Playable> function,
			String attributeName) {
		super(root, a -> function.apply(a), attributeName, g -> function.apply(g).getGame());
		this.attributeName = attributeName;
		this.attributeAcessor = p -> p.getAttribute(attributeName);
		this.valueAcessor = a -> a.getValue();
	}

	public AttributeCheck<BASE, ROOT> self(Function<Playable, Attribute<?>> attributeAcessor,
			Function<Attribute<?>, ?> valueAcessor) {
		AttributeCheck<BASE, ROOT> newInstance = new AttributeCheck<BASE, ROOT>(root, testResults, function,
				attributeName);
		newInstance.attributeAcessor = attributeAcessor;
		newInstance.valueAcessor = valueAcessor;
		return newInstance;
	}

	@Override
	protected AttributeCheck<BASE, ROOT> self(boolean newInstance) {
		if (newInstance) {
			AttributeCheck<BASE, ROOT> newInstanceCheck = new AttributeCheck<>(root, testResults, function,
					attributeName);
			newInstanceCheck.attributeAcessor = this.attributeAcessor;
			newInstanceCheck.valueAcessor = this.valueAcessor;
			return newInstanceCheck;
		}
		return this;
	}

	public AttributeCheck<BASE, ROOT> raw() {
		return self(p -> p.getRawAttribute(attributeName), valueAcessor);
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

	public ROOT sameValueAs(Object value) {
		addTest(p -> valueAcessor.apply(attributeAcessor.apply(p)).equals(value),
				p -> valueAcessor.apply(attributeAcessor.apply(p)), attributeName, "has the same value as");
		return root;
	}

	public IntegerCheck<BASE, ROOT> asInt() {
		return new IntegerCheck<>(root,
				p -> (int) valueAcessor.apply(attributeAcessor.apply(function.apply(p))),
				"integer attribute %s".formatted(attributeName), gameAcessor);
	}

	public StringCheck<BASE, ROOT> asString() {
		return new StringCheck<BASE, ROOT>(root,
				p -> (String) valueAcessor.apply(attributeAcessor.apply(function.apply(p))),
				"String attribute %s".formatted(attributeName), gameAcessor);
	}

}
