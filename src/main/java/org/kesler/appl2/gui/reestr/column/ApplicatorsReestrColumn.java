package org.kesler.appl2.gui.reestr.column;

import org.kesler.appl2.logic.Reception;

public class ApplicatorsReestrColumn extends ReestrColumn {

	public ApplicatorsReestrColumn() {
		name = "Заявители";
		alias = "applicators";
		width = 40;
	}

	public String getValue(Reception reception) {
		return reception.getApplicatorsNames();
	}
}