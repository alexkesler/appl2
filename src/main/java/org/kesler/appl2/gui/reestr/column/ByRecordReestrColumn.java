package org.kesler.appl2.gui.reestr.column;

import org.kesler.appl2.logic.Reception;

public class ByRecordReestrColumn extends ReestrColumn {
	
	public ByRecordReestrColumn() {
		name = "По записи";
		alias = "byRecord";
		width = 10;
	}

	public String getValue(Reception reception) {
		String value = "";
		if (reception.isByRecord() == null) {
			value = "Не опр";
		} else if (reception.isByRecord()) {
			value = "Да";
		} else {
			value = "Нет";
		}

		return value;
	}
}