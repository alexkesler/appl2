package org.kesler.appl2.gui.reception.make;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
// import java.beans.PropertyChangeEvent;
// import java.beans.PropertyChangeListener;

import com.alee.laf.button.WebButton;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import net.miginfocom.swing.MigLayout;
import com.alee.extended.date.WebDateField;

import org.kesler.appl2.util.ResourcesUtil;

import org.kesler.appl2.logic.Reception;
import org.kesler.appl2.logic.Applicator;

class MakeReceptionView extends JDialog{

	public static final int SERVICE_STATE = 0;
	public static final int APPLICATORS_STATE = 1;
	public static final int DATA_STATE = 2;
	public static final int PRINT_STATE = 3;

	private MakeReceptionViewController controller;
	private JButton backButton;
	private JButton nextButton;
	private JButton readyButton;
	private JButton cancelButton;

    private JDialog currentDialog;

	private JTabbedPane tabbedPane;

	private ServicePanel servicePanel;
	private ApplicatorsPanel applicatorsPanel;
	private DataPanel dataPanel;
	private PrintPanel printPanel;



	public MakeReceptionView(MakeReceptionViewController controller, JFrame parentFrame) {
		super(parentFrame, "Прием заявителя", true);
		this.controller = controller;
        currentDialog = this;
		createGUI();
		setLocationRelativeTo(parentFrame);
	}

	public MakeReceptionView(MakeReceptionViewController controller, JDialog parentDialog) {
		super(parentDialog, "Прием заявителя", true);
		this.controller = controller;
        currentDialog = this;
		createGUI();
		setLocationRelativeTo(parentDialog);
	}

	public void showView() {
		setVisible(true);
		dispose();
	}

	private void createGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel padPanel = new JPanel(new GridLayout(1,1));

		// создаем панель с вкладками
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		//tabbedPane.setEnabled(false);


		servicePanel = new ServicePanel(controller);
		applicatorsPanel = new ApplicatorsPanel(controller);
		dataPanel = new DataPanel(controller);
		printPanel = new PrintPanel(controller);

		tabbedPane.add("Выбор услуги", servicePanel);
		tabbedPane.add("Заявители", applicatorsPanel);
		tabbedPane.add("Ввод данных", dataPanel);
		tabbedPane.add("Печать", printPanel);

		padPanel.add(tabbedPane);

		// Создаем панель кнопок, добавляем кнопки
		JPanel buttonPanel = new JPanel();

		backButton = new JButton("Назад");
		backButton.setIcon(ResourcesUtil.getIcon("resultset_previous.png"));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				controller.back();
			}
		});

		nextButton = new JButton("Далее");
		nextButton.setIcon(ResourcesUtil.getIcon("resultset_next.png"));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				controller.next();
			}
		});

		readyButton = new JButton("Сохранить");
		readyButton.setIcon(ResourcesUtil.getIcon("tick.png"));
		readyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				controller.ready();
			}
		});

		cancelButton = new JButton("Отмена");
		cancelButton.setIcon(ResourcesUtil.getIcon("cross.png"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				controller.cancel();
			}
		});

		buttonPanel.add(backButton);
		buttonPanel.add(nextButton);
		buttonPanel.add(readyButton); 
		buttonPanel.add(cancelButton);


		mainPanel.add(padPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.add(mainPanel, BorderLayout.CENTER);

		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent ev) {
                int res = JOptionPane.showConfirmDialog(currentDialog,"Закрыть без сохранения?","Внимание",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if (res == JOptionPane.YES_OPTION) controller.cancel();
            }
        });

		this.setSize(700, 700);
		this.setLocationRelativeTo(null);
	}



	// отработка нажатия общих кнопок

	JButton getBackButton() {
		return backButton;
	}

	JButton getNextButton() {
		return nextButton;
	}

	JButton getReadyButton() {
		return readyButton;
	}

	JTabbedPane getTabbedPane() {
		return tabbedPane;
	}


	// возвращает панели для доступа к их содержимому
	ServicePanel getServicePanel() {
		return servicePanel;
	}

	ApplicatorsPanel getApplicatorsPanel() {
		return applicatorsPanel;
	}

	DataPanel getDataPanel() {
		return dataPanel;
	}

    PrintPanel getPrintPanel() {return printPanel;}


}