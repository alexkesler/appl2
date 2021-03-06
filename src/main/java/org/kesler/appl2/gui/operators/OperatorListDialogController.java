package org.kesler.appl2.gui.operators;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.kesler.appl2.gui.GenericListDialogController;
import org.kesler.appl2.gui.GenericListDialog;
import org.kesler.appl2.logic.operator.OperatorsModel;
import org.kesler.appl2.logic.operator.OperatorsModelStateListener;
import org.kesler.appl2.logic.Operator;
import org.kesler.appl2.logic.ModelState;

import org.kesler.appl2.gui.util.InfoDialog;
import org.kesler.appl2.gui.util.ProcessDialog;

public class OperatorListDialogController implements GenericListDialogController, OperatorsModelStateListener {

	private static OperatorListDialogController instance;

	private OperatorsModel model;
	private GenericListDialog<Operator> dialog;

	private ProcessDialog processDialog = null;

	public static synchronized OperatorListDialogController getInstance() {
		if (instance == null) {
			instance = new OperatorListDialogController();
		}
		return instance;
	}

	private OperatorListDialogController() {

		model = OperatorsModel.getInstance();
		model.addOperatorsModelStateListener(this);

	}

	public void showDialog(JFrame parentFrame) {

		dialog = new GenericListDialog<Operator>(parentFrame, "Операторы", this);
		// List<Operator> operators = model.getAllOperators();
		// dialog.setItems(operators);
		processDialog = new ProcessDialog(dialog);
		model.readOperatorsInSeparateThread();

		dialog.setVisible(true);


		// Освобождаем ресурсы
		dialog.dispose();
		dialog = null;

	}

	// @Override
	// public List<Operator> getAllItems() {
	// 	return model.getAllOperators();
	// }

	@Override
	public boolean openAddItemDialog() {
		boolean result = false;
		OperatorDialog operatorDialog = new OperatorDialog(dialog);
		operatorDialog.setVisible(true);

		if (operatorDialog.getResult() == OperatorDialog.OK) {
			Operator operator = operatorDialog.getOperator();
			int index = model.addOperator(operator);
			dialog.addedItem(index);
			result = true;
		}

		// Освобождаем ресурсы
		operatorDialog.dispose();
		operatorDialog = null;

		return result;
	}

	@Override
	public boolean openEditItemDialog(int index) {

		boolean result = false;
		Operator operator = model.getAllOperators().get(index);
		OperatorDialog operatorDialog = new OperatorDialog(dialog, operator);
		operatorDialog.setVisible(true);

		if (operatorDialog.getResult() == OperatorDialog.OK) {
			model.updateOperator(operator);
			dialog.updatedItem(index);
			result = true;
		}

		// Освобождаем ресурсы
		operatorDialog.dispose();
		operatorDialog = null;

		return result;

	}

	@Override
	public boolean removeItem(int index) {

		boolean result = false;

		Operator operator = model.getAllOperators().get(index);
		
		int confirmResult = JOptionPane.showConfirmDialog(dialog, "Удалить оператора: " + operator + "?", "Удалить?", JOptionPane.YES_NO_OPTION);
		if (confirmResult == JOptionPane.OK_OPTION) { 			
			model.removeOperator(operator);
			dialog.removedItem(index);
			result = true;
		}

		return result;

	}

	@Override
	public void readItems() {
		processDialog = new ProcessDialog(dialog);
		model.readOperatorsInSeparateThread();
	}

	@Override
	public void filterItems(String filter) {
		
	}

	@Override
	public void operatorsModelStateChanged(ModelState state) {
		switch (state) {
			
			case CONNECTING:
				if (processDialog != null) processDialog.showProcess("Соединяюсь...");			
				break;

			case READING:
				if (processDialog != null) processDialog.showProcess("Читаю список операторов");
				break;	
			
			case WRITING:
				if (processDialog != null) processDialog.showProcess("Сохраняю изменения");
				break;	
			
			case UPDATED:
				if (dialog != null) dialog.setItems(model.getAllOperators());
				if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
				new InfoDialog(dialog, "Обновлено", 500, InfoDialog.GREEN).showInfo();	
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