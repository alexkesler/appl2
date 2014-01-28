package org.kesler.appl2.gui.reestr.column;

import org.kesler.appl2.logic.Reception;

public class ServiceReestrColumn extends ReestrColumn {

	public ServiceReestrColumn() {
		name = "Услуга";
		alias = "service";
		width = 70;
	}

	public String getValue(Reception reception) {
		return reception.getServiceName();
	}

}