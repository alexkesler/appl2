package org.kesler.appl2.gui;

import java.util.List;

public interface GenericListDialogController<T> {
	
	boolean openAddItemDialog();
	boolean openEditItemDialog(int index);
	boolean removeItem(int index);

	void filterItems(String filter);
	
	void readItems();
}	

