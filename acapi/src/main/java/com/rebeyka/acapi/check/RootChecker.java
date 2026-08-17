package com.rebeyka.acapi.check;

public interface RootChecker<BASE,ROOT extends Checkable<BASE>> {
    
	public ROOT self();
}
