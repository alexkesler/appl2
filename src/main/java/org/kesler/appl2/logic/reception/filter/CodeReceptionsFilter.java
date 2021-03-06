package org.kesler.appl2.logic.reception.filter;

import org.kesler.appl2.logic.Reception;

public class CodeReceptionsFilter implements ReceptionsFilter {

	private String filterCode;


	public CodeReceptionsFilter(String filterCode) {
		this.filterCode = filterCode;
	}

	public String getCode() {
		return filterCode;
	}

	public void setCode(String filterCode) {
		this.filterCode = filterCode;
	}

	@Override
	public boolean checkReception(Reception reception) {
		if (reception == null) {
			throw new IllegalArgumentException();
		}

		boolean fit = false;
		String receptionCode = reception.getReceptionCode();
		if (receptionCode != null && receptionCode.toLowerCase().contains(filterCode.toLowerCase())) {
			fit = true;
		}

		return fit;
	}

	@Override
	public String toString() {
		String filterString  = "По коду дела: (" + filterCode + ")";


		return filterString;
	}

}