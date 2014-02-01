package org.kesler.appl2.gui.reception.make;

import com.alee.extended.date.WebDateField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

// панель для ввода даннных по услуге
class DataPanel extends JPanel {

    // JLabel realtyObjectNameLabel;

    WebDateField toIssueWebDateField;

    // JTextField rosreestrCodeTextField;

    JCheckBox resultInMFCCheckBox;

    DataPanel(final MakeReceptionViewController controller) {
        super(new MigLayout("fillx"));

        // realtyObjectNameLabel = new JLabel("Не определено");
        // realtyObjectNameLabel.setBorder(BorderFactory.createEtchedBorder());

        // JButton selectRealtyObjectButton = new JButton();
        // selectRealtyObjectButton.setIcon(ResourcesUtil.getIcon("book_previous.png"));
        // selectRealtyObjectButton.addActionListener(new ActionListener() {
        // 	public void actionPerformed(ActionEvent ev) {
        // 		controller.selectRealtyObject();
        // 	}
        // });

        toIssueWebDateField = new WebDateField();
        toIssueWebDateField.addDateSelectionListener(new com.alee.extended.date.DateSelectionListener() {
            public void dateSelected(Date date) {
                controller.setToIssueDate(date);
            }
        });


        // rosreestrCodeTextField = new JTextField(15);
        // rosreestrCodeTextField.addFocusListener(new java.awt.event.FocusListener() {
        // 	public void focusGained(java.awt.event.FocusEvent ev) {}
        // 	public void focusLost(java.awt.event.FocusEvent ev) {
        // 		controller.setRosreestrCode(rosreestrCodeTextField.getText());
        // 	}
        // });


        resultInMFCCheckBox = new JCheckBox("Результат получать в МФЦ");
        resultInMFCCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.setResultInMFC(resultInMFCCheckBox.isSelected());
            }
        });


        this.add(new JLabel("Срок выдачи результата"));
        this.add(toIssueWebDateField, "w 150, wrap");

        // this.add(new JLabel("Код дела Росреестра: "));
        // this.add(rosreestrCodeTextField, "wrap");

        this.add(resultInMFCCheckBox,"wrap");

    }

    // public void setRealtyObjectName(String name) {
    // 	realtyObjectNameLabel.setText("<html>" + name + "</html>");
    // }

    Date getToIssueDate() {
        Date toIssueDate = toIssueWebDateField.getDate();
        return toIssueDate;
    }

    void setToIssueDate(Date toIssueDate) {
        toIssueWebDateField.setDate(toIssueDate);
    }


    void setResultInMFC(boolean resultInMFC) {
        resultInMFCCheckBox.setSelected(resultInMFC);
    }

}
