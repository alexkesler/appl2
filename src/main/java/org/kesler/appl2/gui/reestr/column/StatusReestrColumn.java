package org.kesler.appl2.gui.reestr.column;

import org.kesler.appl2.logic.Reception;

public class StatusReestrColumn extends ReestrColumn {
	
	public StatusReestrColumn() {
		name = "Состояние";
		alias = "status";
		width = 20;
	}

	public String getValue(Reception reception) {
		return reception.getStatusName();
	}
}