package org.kesler.appl2.util;

import org.kesler.appl2.dao.DAOFactory;

public class CounterUtil {

	public static int getNextCount () {
		int count = 1;

		Counter currentCount = DAOFactory.getInstance().getCounterDAO().getItemById(1L);
		if (currentCount == null) {
			currentCount = new Counter();
			currentCount.setId(1L);
			currentCount.setValue(count);
			DAOFactory.getInstance().getCounterDAO().addItem(currentCount);
		} else {
			count = currentCount.getValue();
			count++;
			currentCount.setValue(count);
			DAOFactory.getInstance().getCounterDAO().updateItem(currentCount);
		}

		return count;
	}

}