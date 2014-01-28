package org.kesler.appl2.gui.main;

import java.util.List;
import java.util.Arrays;
import javax.swing.JOptionPane;

import org.kesler.appl2.logic.reception.ReceptionsModel;
import org.kesler.appl2.logic.Reception;
import org.kesler.appl2.logic.reception.ReceptionsModelStateListener;
import org.kesler.appl2.logic.Operator;
import org.kesler.appl2.logic.operator.OperatorsModel;
import org.kesler.appl2.logic.ModelState;
import org.kesler.appl2.logic.operator.OperatorsModelStateListener;
import org.kesler.appl2.gui.util.ProcessDialog;
import org.kesler.appl2.gui.util.InfoDialog;
import org.kesler.appl2.util.HibernateUtil;

import org.kesler.appl2.gui.services.ServicesDialogController;
import org.kesler.appl2.gui.operators.OperatorListDialogController;
import org.kesler.appl2.gui.statistic.StatisticViewController;
import org.kesler.appl2.gui.reception.MakeReceptionViewController;
import org.kesler.appl2.gui.reception.ReceptionStatusListDialogController;
import org.kesler.appl2.gui.applicator.FLListDialogController;
import org.kesler.appl2.gui.applicator.ULListDialogController;
import org.kesler.appl2.gui.reestr.ReestrViewController;



/**
* Управляет основным окном приложения
*/
public class MainViewController implements MainViewListener, 
								CurrentOperatorListener, 
								OperatorsModelStateListener, 
								ReceptionsModelStateListener{
	private static MainViewController instance;

	private MainView mainView;
	private ReceptionsModel receptionsModel;
	private OperatorsModel operatorsModel;
	private LoginDialog loginDialog;

	private ProcessDialog processDialog;

	private MainViewController() {
		this.receptionsModel = new ReceptionsModel();
		this.operatorsModel = OperatorsModel.getInstance();

		operatorsModel.addOperatorsModelStateListener(this);
		receptionsModel.addReceptionsModelStateListener(this);
		
		mainView = new MainView(this);
		mainView.addMainViewListener(this);
		
		CurrentOperator.getInstance().addCurrentOperatorListener(this);
	}

	/**
	* Всегда возвращает один и тот же экземпляр контроллера (паттерн Одиночка)
	*/
	public static synchronized MainViewController getInstance() {
		if (instance == null) {
			instance = new MainViewController();
		}
		return instance;
	}

	/**
	* Открывает основное окно приложения
	*/
	public void openMainView() {
		mainView.setVisible(true);
		setMainViewAccess(null);
	}

	/**
	* Обрабатывает команды основного вида, определенные в классе {@link org.kesler.appl2.gui.main.MainViewCommand}
	*/
	@Override
	public void performMainViewCommand(MainViewCommand command) {
		switch (command) {
			case Login: 
				login();
				break;
			case Logout:
				logout();
				break;	
			case NewReception: 
				openMakeReceptionView();
				break;
			case UpdateReceptions: 
				readReceptions();
				break;
			case OpenReceptionsReestr: 
				openReceptionsReestr();
				break;
			case OpenStatistic: 
				openStatistic();
				break;
			case FLs: 
				openFLs();
				break;
			case ULs: 
				openULs();
				break;
			case Services: 
				openServicesView();
				break;
			case ReceptionStatuses: 
				openReceptionStatuses();
				break;
			case Operators: 
				openOperators();
				break;
			case Options:	
				openOptions();
				break;
			case Exit:
				System.exit(0);	

		}
	}


	private void setMainViewAccess(Operator operator) {

		// по умолчанию все элементы неактивны
		for (MainViewCommand command: MainViewCommand.values()) {
			mainView.getActionByCommand(command).setEnabled(false);
		}

		// Элемент Закрыть всегда активен
		mainView.getActionByCommand(MainViewCommand.Exit).setEnabled(true);
		mainView.getActionByCommand(MainViewCommand.Options).setEnabled(true);

		
		if (operator != null) { // оператор назначен

			mainView.getActionByCommand(MainViewCommand.Logout).setEnabled(true);
			mainView.getActionByCommand(MainViewCommand.NewReception).setEnabled(true);
			mainView.getActionByCommand(MainViewCommand.UpdateReceptions).setEnabled(true);

			if (operator.isControler()) { // для контролера
				mainView.getActionByCommand(MainViewCommand.ReceptionStatuses).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.OpenReceptionsReestr).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.OpenStatistic).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.FLs).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.ULs).setEnabled(true);
			}

			if (operator.isAdmin()) { // для администратора
				mainView.getActionByCommand(MainViewCommand.ReceptionStatuses).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.OpenReceptionsReestr).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.OpenStatistic).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.FLs).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.ULs).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.Services).setEnabled(true);
				mainView.getActionByCommand(MainViewCommand.Operators).setEnabled(true);
				
			}

		} else { // если оператор не назначен
			mainView.getActionByCommand(MainViewCommand.Login).setEnabled(true);			
		}

	}


	private void readReceptions() {
		processDialog = new ProcessDialog(mainView);
		receptionsModel.readReceptionsAndApplyFiltersInSeparateThread();

	}

	@Override 
	public void receptionsModelStateChanged(ModelState state) {
		switch (state) {
			case CONNECTING:
				if (processDialog != null) processDialog.showProcess("Соединяюсь...");
			break;

			case READING:
				if (processDialog != null) processDialog.showProcess("Получаю список приемов");
			break;	

			case WRITING:
				if (processDialog != null) processDialog.showProcess("Сохраняю");
			break;	

			case READY:
				if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
			break;	
			
//			case UPDATED:
//				if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
//				mainView.setReceptions(receptionsModel.getAllReceptions());
//			break;

            case FILTERED:
                if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
                mainView.setReceptions(receptionsModel.getFilteredReceptions());
                break;

			case ERROR:
				if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
				new InfoDialog(mainView, "Ошибка базы данных", 1000, InfoDialog.RED).showInfo();
				
			break;		

		}
	}

	private void login() {			
			
		loginDialog = new LoginDialog(mainView);

		processDialog = new ProcessDialog(loginDialog);
		operatorsModel.readOperatorsInSeparateThread();

		loginDialog.showDialog();

		// делаем проверку на итог - назначаем оператора
		if (loginDialog.getResult() == LoginDialog.OK) {
			Operator operator = loginDialog.getOperator();
			CurrentOperator.getInstance().setOperator(operator);
            mainView.setConnected(true);
			new InfoDialog(mainView, "<html>Добро пожаловать, <p><i>" + 
										operator.getFirstName() + 
										" " + operator.getParentName() + "</i>!</p></html>", 1000, InfoDialog.STAR).showInfo();
		} else {
			CurrentOperator.getInstance().resetOperator();
			HibernateUtil.closeConnection();
            mainView.setConnected(false);
		}
		// Освобождаем ресурсы
		loginDialog.dispose();
		loginDialog = null;

			

	}

	public void operatorsModelStateChanged(ModelState state) {
		switch (state) {
			case CONNECTING:
					
					if (processDialog != null) processDialog.showProcess("Соединяюсь...");
					break;
			
			case READING:
					if (processDialog != null) processDialog.showProcess("Читаю список операторов из базы...");
					break;

			case UPDATED:
					if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
					List<Operator> operators = operatorsModel.getActiveOperators();
					if (loginDialog != null) loginDialog.setOperators(operators);
					break;

			case READY:
					break;


			case ERROR:
					if (processDialog != null) {processDialog.hideProcess(); processDialog = null;}
					new InfoDialog(loginDialog, "Ошибка базы данных", 1000, InfoDialog.RED).showInfo();
					break;	
			
		}
	}


	private void logout() {
		CurrentOperator.getInstance().resetOperator();
		HibernateUtil.closeConnection();
        mainView.setConnected(false);
	}

	private void openMakeReceptionView() {
		MakeReceptionViewController.getInstance().openView(mainView);
	}

	private void openServicesView() {
		ServicesDialogController.getInstance().openEditDialog(mainView);
	}

	private void openStatistic() {
		StatisticViewController.getInstance().openView();
	}

	private void openOperators() {
		OperatorListDialogController.getInstance().showDialog(mainView);		
	}

	private void openReceptionStatuses() {
		ReceptionStatusListDialogController.getInstance().openDialog(mainView);
	}

	private void openFLs() {
		FLListDialogController.getInstance().openDialog(mainView);
	}

	private void openULs() {
		ULListDialogController.getInstance().openDialog(mainView);
	}

	private void openReceptionsReestr() {
		ReestrViewController.getInstance().openView(mainView);
	}

	private void openOptions() {
		OptionsDialog optionsDialog = new OptionsDialog(mainView);
		optionsDialog.showDialog();
	}

    public void editReception(Reception reception) {
        MakeReceptionViewController.getInstance().openView(mainView, reception);
    }


	/**
	* Обрабатывет событие смены оператора
	*/
	@Override
	public void currentOperatorChanged(Operator operator) {

		if (operator != null) {
			mainView.setCurrentOperatorLabel(operator.getFIO());	
		} else {
			mainView.setCurrentOperatorLabel("");
		}

		setMainViewAccess(operator);
	}

}