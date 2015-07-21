package com.krispdev.resilience.gui2.objects.basic.viewable;

import com.krispdev.resilience.gui2.interfaces.Visible;
import com.krispdev.resilience.gui2.objects.basic.Component;
import com.krispdev.resilience.gui2.objects.basic.Container;
import com.krispdev.resilience.gui2.objects.geometry.Shape;

public class VisibleComponent extends Component implements Visible{

	public VisibleComponent(Container container, Shape area) {
		super(container, area);
	}

	@Override
	public void draw(float x, float y) {}

	@Override
	public boolean onComponentClick(float x, float y, int btn) {
		return false;
	}

}
