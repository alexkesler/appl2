package org.kesler.appl2.gui.reestr.column;

import org.kesler.appl2.logic.Reception;

public abstract class ReestrColumn {
	protected String name;
	protected String alias;
	protected int    width;

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public int getWidth() {
		return width;
	}

	public abstract Object getValue(Reception reception);


}


