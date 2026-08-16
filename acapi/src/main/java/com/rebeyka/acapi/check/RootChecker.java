package com.rebeyka.acapi.check;

public interface RootChecker<BASE,ROOT extends AbstractCheck<?,BASE,?>> {
	
	public ROOT self();
}
