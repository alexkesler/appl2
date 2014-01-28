package org.kesler.appl2.logic.reception.filter;

import org.kesler.appl2.logic.Reception;

public interface ReceptionsFilter {
	public boolean checkReception(Reception reception);
	@Override
	public String toString();
}