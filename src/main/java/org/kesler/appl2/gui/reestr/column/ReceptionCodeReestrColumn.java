package org.kesler.appl2.gui.reestr.column;

import org.kesler.appl2.logic.Reception;

public class ReceptionCodeReestrColumn extends ReestrColumn {

	public ReceptionCodeReestrColumn() {
		name = "Код дела";
		alias = "receptionCode";
		width = 10;
	}

	public String getValue(Reception reception) {
		
		String receptionCode = reception.getReceptionCode();

		if (receptionCode == null) receptionCode = "Не опр.";

		return receptionCode;

	}
}