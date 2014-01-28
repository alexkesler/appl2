package org.kesler.appl2.gui.reestr.column;

import java.text.SimpleDateFormat;

import org.kesler.appl2.logic.Reception;

public class OpenDateReestrColumn extends ReestrColumn {

	public OpenDateReestrColumn() {
		name = "Создан";
		alias = "openDate";
		width = 17;
	}

	public String getValue(Reception reception) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

		return dateFormat.format(reception.getOpenDate());
	}
}