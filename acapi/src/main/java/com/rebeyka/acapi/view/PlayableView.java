package com.rebeyka.acapi.view;

import java.util.List;

public class PlayableView {

	private List<AttributeView<Comparable<?>>> attributeView;

	public List<AttributeView<Comparable<?>>> getAttributeView() {
		return attributeView;
	}

	public void setAttributeView(List<AttributeView<Comparable<?>>> attributeView) {
		this.attributeView = attributeView;
	}
}
