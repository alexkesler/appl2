package org.kesler.appl2.gui.services;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.miginfocom.swing.MigLayout;

import org.kesler.appl2.logic.Service;
import org.kesler.appl2.util.ResourcesUtil;

public class ServiceDialog extends JDialog {

	public static int NONE = -1;
	public static int OK = 0;
	public static int CANCEL = 1;

	private JDialog parentDialog;
	private int result;

	private Service service;

    private JTextField codeTextField;
    private JTextArea shortNameTextArea;
	private JTextArea fullNameTextArea;
	private JCheckBox enabledCheckBox;

	public ServiceDialog(JDialog parentDialog) {
		super(parentDialog,"Создать", true);
		this.parentDialog = parentDialog;
		result = NONE;

		service = new Service();
		//service.setName("Новая услуга");
		service.setEnabled(true);
		createGUI();
	}

	public ServiceDialog(JDialog parentDialog, Service service) {
		super(parentDialog,"Изменить",true);
		this.parentDialog = parentDialog;
		result = NONE;

		this.service = service;
		createGUI();
	}

	public int getResult() {
		return result;
	}

	public Service getService() {
		return service;
	}

	private void createGUI() {

		// Основная панель
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Панель данных
		JPanel dataPanel = new JPanel(new MigLayout("fill"));

        codeTextField = new JTextField(10);

		shortNameTextArea = new JTextArea();
		shortNameTextArea.setLineWrap(true);
		shortNameTextArea.setWrapStyleWord(true);
		JScrollPane shortNameTextAreaScrollPane = new JScrollPane(shortNameTextArea);


		fullNameTextArea = new JTextArea();
		fullNameTextArea.setLineWrap(true);
		fullNameTextArea.setWrapStyleWord(true);
		JScrollPane fullNameTextAreaScrollPane = new JScrollPane(fullNameTextArea);
		//nameTextAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		enabledCheckBox = new JCheckBox("Действующая");

        dataPanel.add(new JLabel("Номер соглашения"));
        dataPanel.add(codeTextField, "wrap");
		dataPanel.add(new JLabel("Сокращенное наименование: "), "wrap");
		dataPanel.add(shortNameTextAreaScrollPane, "span, h 50, grow");
		dataPanel.add(new JLabel("Полное наименование (для запроса): "), "wrap");
		dataPanel.add(fullNameTextAreaScrollPane, "span, pushy, grow");
		dataPanel.add(enabledCheckBox);

		// Панель кнопок
		JPanel buttonPanel = new JPanel();

		JButton okButton = new JButton("Ok");
		okButton.setIcon(ResourcesUtil.getIcon("accept.png"));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (saveServiceFromGUI()) { // если удалось сохранить услугу
					result = OK;
					setVisible(false);					
				} 
			}
		});

		JButton cancelButton = new JButton("Отмена");
		cancelButton.setIcon(ResourcesUtil.getIcon("cancel.png"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				result = CANCEL;
				setVisible(false);
			}
		});

		// собираем панель кнопок

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		// собираем главную панель
		mainPanel.add(dataPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setContentPane(mainPanel);
		this.setSize(500, 300);
		this.setLocationRelativeTo(parentDialog);

		loadGUIFromService();

		shortNameTextArea.requestFocus();

	}

	private void loadGUIFromService() {

        String code = service.getCode();
        if(code == null) code = "";

        codeTextField.setText(code);


        String shortName = service.getShortName();
		if (shortName == null) shortName = "";

		shortNameTextArea.setText(shortName);

        String fullName = service.getFullName();
		if (fullName == null) fullName = "";

		fullNameTextArea.setText(fullName);

		Boolean enabled = service.getEnabled();
		if (enabled == null) enabled = false;

		enabledCheckBox.setSelected(enabled);
	}

	private boolean saveServiceFromGUI() {

        String code = codeTextField.getText();
        service.setCode(code);

		String shortName = shortNameTextArea.getText();
		if (!shortName.isEmpty()) {
			service.setShortName(shortName);
		} else {
			JOptionPane.showMessageDialog(this, "Сокр наименование услуги не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		String fullName = fullNameTextArea.getText();
		if (!fullName.isEmpty()) {
			service.setFullName(fullName);
		} else {
			JOptionPane.showMessageDialog(this, "Полное наименование услуги не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		service.setEnabled(enabledCheckBox.isSelected());

		return true;
	}

}