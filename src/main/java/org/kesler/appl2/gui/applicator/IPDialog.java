package org.kesler.appl2.gui.applicator;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.miginfocom.swing.MigLayout;

import org.kesler.appl2.util.ResourcesUtil;
import org.kesler.appl2.logic.IP;
import org.kesler.appl2.logic.FL;

import org.kesler.appl2.gui.AbstractDialog;

public class IPDialog extends AbstractDialog {

	private JLabel flLabel;
	private JTextArea innTextArea;
	private JTextArea ogrnipTextArea;

	private IP ip;

	public IPDialog(JFrame frame) {
		super(frame, "Создать", true);
		
		ip = new IP();
		result = NONE;
		
		createGUI();
		setLocationRelativeTo(frame);
	}

	public IPDialog(JFrame frame, IP ip) {
		super(frame, "Изменить", true);
		
		this.ip = ip;
		result = NONE;
		
		createGUI();
		setLocationRelativeTo(frame);
	}

	public IPDialog(JDialog dialog) {
		super(dialog, "Создать", true);
		
		ip = new IP();
		result = NONE;
		
		createGUI();
		this.setLocationRelativeTo(dialog);
	}

	public IPDialog(JDialog dialog, IP ip) {
		super(dialog, "Изменить", true);
		
		this.ip = ip;
		result = NONE;

		createGUI();
		this.setLocationRelativeTo(dialog);
	}


	public IP getIP() {
		return ip;
	}	

	private void createGUI() {

		JPanel mainPanel = new JPanel(new BorderLayout());

		// Панель данных
		JPanel dataPanel = new JPanel(new MigLayout("fill"));

		flLabel = new JLabel();
		flLabel.setBorder(BorderFactory.createEtchedBorder());

		JButton selectFLButton = new JButton("Выбрать");
		selectFLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				selectFL();
			}
		});


		innTextArea = new JTextArea();
		JScrollPane innTextAreaScrollPane = new JScrollPane(innTextArea);


		ogrnipTextArea = new JTextArea();
		JScrollPane ogrnipTextAreaScrollPane = new JScrollPane(ogrnipTextArea);

		//Собираем панель данных
		dataPanel.add(new JLabel("Физ лицо:"));
		dataPanel.add(flLabel, "grow, w 300");
		dataPanel.add(selectFLButton, "wrap");
		dataPanel.add(new JLabel("ИНН:"));
		dataPanel.add(innTextAreaScrollPane, "w 100, wrap");
		dataPanel.add(new JLabel("ОГРН: "));
		dataPanel.add(ogrnipTextAreaScrollPane, "w 150, wrap");

		// Панель кнопок
		JPanel buttonPanel = new JPanel();

		JButton okButton = new JButton();
		okButton.setIcon(ResourcesUtil.getIcon("accept.png"));
		this.getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				boolean checkResult = saveIPFromGUI();
				if(checkResult) {
					result = OK;
					setVisible(false);
				}
				
			}
		});

		JButton cancelButton = new JButton();
		cancelButton.setIcon(ResourcesUtil.getIcon("cancel.png"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				result = CANCEL;
				setVisible(false);
			}
		});
		// Собираем панель кнопок
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		// Собираем основную панель
		mainPanel.add(dataPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setContentPane(mainPanel);
		this.pack();
		
		loadGUIDataFromIP();

	}

	// Отображаем данные ИП
	private void loadGUIDataFromIP() {
		String fio = "Не определено";
		FL fl = ip.getFL();
		if (fl != null) fio = fl.getFIO();
		flLabel.setText(fio);

		innTextArea.setText(ip.getINN());
		ogrnipTextArea.setText(ip.getOGRNIP());
	}

	private boolean saveIPFromGUI() {
		if (innTextArea.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Ошибка", "Поле ИНН не может быть пустым",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// if (ogrnipTextArea.getText().isEmpty()) {
		// 	JOptionPane.showMessageDialog(this,"Ошибка", "Сокращенное наименование не может быть пустым",JOptionPane.ERROR_MESSAGE);
		// 	return false;
		// }


		ip.setINN(innTextArea.getText().trim());
		ip.setOGRNIP(ogrnipTextArea.getText().trim());

		return true;
	}

	private void selectFL() {
		FL selectedFL = FLListDialogController.getInstance().openSelectDialog(this);
		if(selectedFL != null) ip.setFL(selectedFL);
		loadGUIDataFromIP();
	}


}