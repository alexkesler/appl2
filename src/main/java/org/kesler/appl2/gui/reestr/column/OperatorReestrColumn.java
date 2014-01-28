package org.kesler.appl2.gui.reestr.column;

import org.kesler.appl2.logic.Reception;

public class OperatorReestrColumn extends ReestrColumn {
	
	public OperatorReestrColumn() {
		name = "Оператор";
		alias = "operator";
		width = 20;
	}

	public String getValue(Reception reception) {
		if (reception.getOperator() == null) {
			return "Не опеределен";
		}
		return reception.getOperator().getShortFIO();
	}
}