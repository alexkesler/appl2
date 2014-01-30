package org.kesler.appl2.gui.applicator;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import net.miginfocom.swing.MigLayout;

import org.kesler.appl2.util.ResourcesUtil;

import org.kesler.appl2.logic.FL;
import org.kesler.appl2.logic.IP;
import org.kesler.appl2.logic.applicator.ApplicatorIP;

import org.kesler.appl2.gui.AbstractDialog;

public class ApplicatorIPDialog extends AbstractDialog {

	private ApplicatorIP applicatorIP;

	private JLabel nameLabel;
	private JLabel represLabel;

	public ApplicatorIPDialog (JFrame parentFrame) {
		super(parentFrame,"Заявитель - индивидуальный предприниматель", true);
		this.applicatorIP = new ApplicatorIP();
		createGUI();
		setLocationRelativeTo(parentFrame);
	}

	public ApplicatorIPDialog (JDialog parentDialog) {
		super(parentDialog,"Заявитель - индивидуальный предприниматель", true);
		this.applicatorIP = new ApplicatorIP();
		createGUI();
		setLocationRelativeTo(parentDialog);
	}


	public ApplicatorIPDialog(JFrame parentFrame, ApplicatorIP applicatorIP) {
		super(parentFrame, "Заявитель - индивидуальный предприниматель", true);
		this.applicatorIP = applicatorIP;
		createGUI(); 
		setLocationRelativeTo(parentFrame);
	}

	public ApplicatorIPDialog(JDialog parentDialog, ApplicatorIP applicatorIP) {
		super(parentDialog, "Заявитель - индивидуальный предприниматель", true);
		this.applicatorIP = applicatorIP;
		createGUI(); 
		setLocationRelativeTo(parentDialog);
	}

	private void createGUI() {

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel dataPanel = new JPanel(new MigLayout("fill"));

		nameLabel = new JLabel();
		nameLabel.setBorder(BorderFactory.createEtchedBorder());

		JButton selectIPButton = new JButton("Выбрать");
		selectIPButton.setIcon(ResourcesUtil.getIcon("user_suit.png"));
		selectIPButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				selectApplicatorIP();
			}
		});

		represLabel = new JLabel();
		represLabel.setBorder(BorderFactory.createEtchedBorder());

		JButton selectRepresFLButton = new JButton("Выбрать");
		selectRepresFLButton.setIcon(ResourcesUtil.getIcon("group_add.png"));
		selectRepresFLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				selectRepresFL();
			}
		});


		JButton clearRepresFLButton = new JButton("Очистить");
		clearRepresFLButton.setIcon(ResourcesUtil.getIcon("group_delete.png"));
		clearRepresFLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				clearRepresFL();
			}
		});



		dataPanel.add(new JLabel("Индивидуальный предприниматель:"),"span, wrap");
		dataPanel.add(nameLabel,"grow, push, w 500");
		dataPanel.add(selectIPButton, "wrap");
	
		dataPanel.add(new JLabel("Представитель:"), "wrap");
		dataPanel.add(represLabel,"growx,pushx, w 500");
		dataPanel.add(selectRepresFLButton);
		dataPanel.add(clearRepresFLButton,"wrap");


		// Панель с кнопками
		JPanel buttonPanel = new JPanel();

		JButton okButton = new JButton("Ok");
		okButton.setIcon(ResourcesUtil.getIcon("accept.png"));
		this.getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				boolean checkResult = checkIP();
				if (checkResult) {
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


		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(dataPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setContentPane(mainPanel);
		this.pack();

		updateLabels();
	}

	public ApplicatorIP getApplicatorIP() {
		return applicatorIP;
	}

	private void updateLabels() {
		nameLabel.setText("<html>" + applicatorIP.getShortName() + "</html>");
		represLabel.setText(applicatorIP.getRepresFIO());
	}	

	private void selectApplicatorIP() {
		IP ip = IPListDialogController.getInstance().openSelectDialog(currentDialog);
		applicatorIP.setIP(ip);
		updateLabels();
	}

	private void selectRepresFL() {
		FL fl = FLListDialogController.getInstance().openSelectDialog(currentDialog);//Модальный вызов
		applicatorIP.setRepres(fl);
		updateLabels();
	}

	private void clearRepresFL() {
		applicatorIP.setRepres(null);
		updateLabels();
	}

	private boolean checkIP() {
		if(applicatorIP.getIP() == null) {
			JOptionPane.showMessageDialog(this, "ИП не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// if (applicatorIP.getRepres() == null) {
		// 	JOptionPane.showMessageDialog(this,"Представитель не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
		// 	return false;
		// }

		return true;
	}

}