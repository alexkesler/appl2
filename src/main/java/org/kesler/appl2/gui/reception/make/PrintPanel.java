package org.kesler.appl2.gui.reception.make;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// панель для печати запроса
class PrintPanel extends JPanel {

    PrintPanel(final MakeReceptionViewController controller) {
        super(new MigLayout());

        JButton printButton = new JButton("Распечатать запрос");
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.printRequest();
            }
        });

        this.add(printButton);
    }

}
