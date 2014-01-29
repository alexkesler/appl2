package org.kesler.appl2.gui.applicator;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.kesler.appl2.logic.IP;
import org.kesler.appl2.logic.applicator.IPModel;
import org.kesler.appl2.logic.applicator.IPModelStateListener;
import org.kesler.appl2.logic.ModelState;

import org.kesler.appl2.gui.GenericListDialog;
import org.kesler.appl2.gui.GenericListDialogController;

import org.kesler.appl2.gui.util.InfoDialog;
import org.kesler.appl2.gui.util.ProcessDialog;

public class IPListDialogController implements GenericListDialogController, IPModelStateListener {

	private static IPListDialogController instance = null;

	private GenericListDialog dialog;
	private IPModel model;

	private ProcessDialog processDialog = null;

	public static synchronized IPListDialogController getInstance() {
		if (instance == null) {
			instance = new IPListDialogController();
		}
		return instance;
	}

	private IPListDialogController() {
		model = IPModel.getInstance();
		model.addIPModelStateListener(this);
	}

	public void openDialog(JDialog parentDialog) {
		dialog = new GenericListDialog<IP>(parentDialog, "ИП", this, GenericListDialog.VIEW_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readIPsInSeparateThread();

		dialog.setVisible(true);
		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
	}

	public void openDialog(JFrame parentFrame) {
		dialog = new GenericListDialog<IP>(parentFrame, "ИП", this, GenericListDialog.VIEW_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readIPsInSeparateThread();

		dialog.setVisible(true);

		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
	}

	public IP openSelectDialog(JDialog parentDialog) {
		dialog = new GenericListDialog<IP>(parentDialog, "ИП", this, GenericListDialog.SELECT_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readIPsInSeparateThread();

		dialog.setVisible(true);

		IP ip = null;
		if (dialog.getResult() == GenericListDialog.OK) {
			int selectedIndex = dialog.getSelectedIndex();
			ip = model.getFilteredIPs().get(selectedIndex);
		}

		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
		return ip;
	}

	public IP openSelectDialog(JFrame parentFrame) {
		dialog = new GenericListDialog<IP>(parentFrame, "ИП", this, GenericListDialog.SELECT_FILTER_MODE);

		processDialog = new ProcessDialog(dialog);
		model.setFilterString("");
		model.readIPsInSeparateThread();

		dialog.setVisible(true);

		IP ip = null;
		if (dialog.getResult() == GenericListDialog.OK) {
			int selectedIndex = dialog.getSelectedIndex();
			ip = model.getFilteredIPs().get(selectedIndex);
		}

		// освобождаем ресурсы
		dialog.dispose();
		dialog = null;
		return ip;

	}


	@Override
	public void readItems() {
		model.readIPs();
		model.filterIPs();
		List<IP> ips = model.getFilteredIPs();
		dialog.setItems(ips);
	}

	@Override
	public void filterItems(String filterString) {
		model.setFilterString(filterString);
		model.filterIPs();
		List<IP> ips = model.getFilteredIPs();
		dialog.setItems(ips);
	}

	@Override
	public boolean openAddItemDialog() {
		boolean result = false;
		IPDialog ipDialog = new IPDialog(dialog);
		ipDialog.setVisible(true);
		if (ipDialog.getResult() == IPDialog.OK) {
			filterItems("");
			IP ip = ipDialog.getIP();
			int index = model.addIP(ip);
			if (index != -1) {
				dialog.addedItem(index);
				result = true;
			}
			
		}
		// Освобождаем ресурсы
		ipDialog.dispose();
		ipDialog = null;

		return result;
	}

	@Override
	public boolean openEditItemDialog(int index ) {
		boolean result = false;
		IP ip = model.getAllIPs().get(index);
		IPDialog ipDialog = new IPDialog(dialog, ip);
		ipDialog.setVisible(true);
		
		if (ipDialog.getResult() == IPDialog.OK) {
			model.updateIP(ip);
			dialog.updatedItem(index);
			result = true;
		}
		// Освобождаем ресурсы
		ipDialog.dispose();
		ipDialog = null;

		return result;
	}

	@Override
	public boolean removeItem(int index) {
		IP ip = model.getAllIPs().get(index);
		model.deleteIP(ip);
		dialog.removedItem(index);
		return true;
	}

	@Override 
	public void ipModelStateChanged(ModelState state) {
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
				if (dialog != null) dialog.setItems(model.getFilteredIPs());
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