package org.kesler.appl2.gui.applicator;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.kesler.appl2.logic.UL;
import org.kesler.appl2.logic.applicator.ULModel;
import org.kesler.appl2.logic.applicator.ULModelStateListener;
import org.kesler.appl2.logic.ModelState;

import org.kesler.appl2.gui.GenericListDialog;
import org.kesler.appl2.gui.GenericListDialogController;

import org.kesler.appl2.gui.util.InfoDialog;
import org.kesler.appl2.gui.util.ProcessDialog;

public class ULListDialogController implements GenericListDialogController, ULModelStateListener {

	private static ULListDialogController instance = null;

	private GenericListDialog dialog;
	private ULModel model;

	private ProcessDialog processDialog = null;

	public static synchronized ULListDialogController getInstance() {
		if (instance == null) {
			instance = new ULListDialogController();
		}
		return instance;
	}

	private ULListDialogController() {
		model = ULModel.getInstance();
		model.addULModelStateListener(this);
	}

	public void openDialog(JDialog parentDialog) {
		dialog = new GenericListDialog<UL>(parentDialog, "Юр лица", this, GenericListDialog.VIEW_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readULsInSeparateThread();

		dialog.setVisible(true);
		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
	}

	public void openDialog(JFrame parentFrame) {
		dialog = new GenericListDialog<UL>(parentFrame, "Юр лица", this, GenericListDialog.VIEW_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readULsInSeparateThread();

		dialog.setVisible(true);

		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
	}

	public UL openSelectDialog(JDialog parentDialog) {
		dialog = new GenericListDialog<UL>(parentDialog, "Юр лица", this, GenericListDialog.SELECT_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readULsInSeparateThread();

		dialog.setVisible(true);

		UL ul = null;
		if (dialog.getResult() == GenericListDialog.OK) {
			int selectedIndex = dialog.getSelectedIndex();
			ul = model.getFilteredULs().get(selectedIndex);
		}

		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
		return ul;
	}

	public UL openSelectDialog(JFrame parentFrame) {
		dialog = new GenericListDialog<UL>(parentFrame, "Юр лица", this, GenericListDialog.SELECT_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readULsInSeparateThread();

		dialog.setVisible(true);

		UL ul = null;
		if (dialog.getResult() == GenericListDialog.OK) {
			int selectedIndex = dialog.getSelectedIndex();
			ul = model.getFilteredULs().get(selectedIndex);
		}

		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
		return ul;

	}


	@Override
	public void readItems() {
		model.readULs();
		model.filterULs();
		List<UL> uls = model.getFilteredULs();
		dialog.setItems(uls);
	}

	@Override
	public void filterItems(String filterString) {
		model.setFilterString(filterString);
		model.filterULs();
		List<UL> uls = model.getFilteredULs();
		dialog.setItems(uls);
	}

	@Override
	public boolean openAddItemDialog() {
		boolean result = false;
		ULDialog ulDialog = new ULDialog(dialog);
		ulDialog.setVisible(true);
		if (ulDialog.getResult() == ULDialog.OK) {
			filterItems("");
			UL ul = ulDialog.getUL();
			int index = model.addUL(ul);
			if (index != -1) {
				dialog.addedItem(index);
				result = true;
			}
			
		}
		// Освобождаем ресурсы
		ulDialog.dispose();
		ulDialog = null;

		return result;
	}

	@Override
	public boolean openEditItemDialog(int index ) {
		boolean result = false;
		UL ul = model.getAllULs().get(index);
		ULDialog ulDialog = new ULDialog(dialog, ul);
		ulDialog.setVisible(true);
		
		if (ulDialog.getResult() == ULDialog.OK) {
			model.updateUL(ul);
			dialog.updatedItem(index);
			result = true;
		}
		// Освобождаем ресурсы
		ulDialog.dispose();
		ulDialog = null;

		return result;
	}

	@Override
	public boolean removeItem(int index) {
		UL ul = model.getAllULs().get(index);
		model.deleteUL(ul);
		dialog.removedItem(index);
		return true;
	}

	@Override 
	public void ulModelStateChanged(ModelState state) {
		switch (state) {
			
			case CONNECTING:
				if (processDialog != null) processDialog.showProcess("Соединяюсь...");			
				break;

			case READING:
				if (processDialog != null) processDialog.showProcess("Читаю список юр лиц");
				break;	
			
			case WRITING:
				if (processDialog != null) processDialog.showProcess("Сохраняю изменения");
				break;	
			
			case UPDATED:
				// if (dialog != null) dialog.setItems(model.getAllOperators());
				if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
				new InfoDialog(dialog, "Обновлено", 500, InfoDialog.GREEN).showInfo();	
				break;

			case FILTERED:
				if (dialog != null) dialog.setItems(model.getFilteredULs());
				if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
				// new InfoDialog(dialog, "Обновлено", 500, InfoDialog.GREEN).showInfo();	
				break;

			
			case READY:
				if (processDialog != null) {processDialog.hideProcess(); processDialog=null;}	
				break;
			
			case ERROR:				
				if (processDialog != null) {processDialog.hideProcess(); processDialog=null;}
				new InfoDialog(dialog, "Ошибка базы данных", 1000, InfoDialog.RED).showInfo();
				break;

		}
	}

}