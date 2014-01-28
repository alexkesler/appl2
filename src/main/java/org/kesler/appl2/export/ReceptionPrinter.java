package org.kesler.appl2.export;

import org.kesler.appl2.logic.Reception;

public abstract class ReceptionPrinter {

	protected Reception reception;

	ReceptionPrinter(Reception reception) {
		this.reception = reception;
	}

	public abstract void printReception();

	protected void saveFile() {
		
	}

}