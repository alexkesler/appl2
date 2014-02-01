package org.kesler.appl2.gui.reception.make;

import net.miginfocom.swing.MigLayout;
import org.kesler.appl2.util.ResourcesUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Панель выбора услуги
 */
public class ServicePanel extends JPanel{

        JTextField receptionCodeTextField;
        JLabel serviceNameLabel;
        JCheckBox byRecordCheckBox;

        ServicePanel(final MakeReceptionViewController controller) {
            super(new MigLayout("fillx"));

            receptionCodeTextField = new JTextField(15);
            /// При потере фокуса запоминаем код дела
            receptionCodeTextField.addFocusListener(new java.awt.event.FocusListener() {
                public void focusGained(java.awt.event.FocusEvent ev) {}
                public void focusLost(java.awt.event.FocusEvent ev) {
                    controller.setReceptionCode(receptionCodeTextField.getText());
                }
            });

            JButton regenerateReceptionCodeButton = new JButton();
            regenerateReceptionCodeButton.setIcon(ResourcesUtil.getIcon("arrow_refresh.png"));
            regenerateReceptionCodeButton.setToolTipText("Пересчитать код дела");
            regenerateReceptionCodeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.regenerateReceptionCode();
                }
            });



            serviceNameLabel = new JLabel();
            serviceNameLabel.setBorder(BorderFactory.createEtchedBorder());
            JButton selectServiceButton = new JButton("Выбрать");
            selectServiceButton.setIcon(ResourcesUtil.getIcon("book_previous.png"));
            selectServiceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    controller.selectService();
                }
            });

            byRecordCheckBox = new JCheckBox("По записи");
            byRecordCheckBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    controller.setReceptionByRecord(byRecordCheckBox.isSelected());
                }
            });

            this.add(new JLabel("Код дела: "),"span, split 3");
            this.add(receptionCodeTextField);
            this.add(regenerateReceptionCodeButton, "wrap");
            this.add(new JLabel("Услуга: "),"ay top");
            this.add(serviceNameLabel,"pushx, grow");
            this.add(selectServiceButton, "right, wrap");
            this.add(byRecordCheckBox,"span,wrap");

        }

        void setReceptionCode(String receptionCode) {
            receptionCodeTextField.setText(receptionCode);
        }

        void setServiceName(String serviceName) {
            serviceNameLabel.setText("<html>"+serviceName+"</html>");
        }

        void setByRecord(boolean byRecord) {
            byRecordCheckBox.setSelected(byRecord);
        }



}
