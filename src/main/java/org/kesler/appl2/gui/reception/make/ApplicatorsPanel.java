package org.kesler.appl2.gui.reception.make;

import com.alee.laf.button.WebButton;
import com.alee.managers.popup.PopupWay;
import com.alee.managers.popup.WebButtonPopup;
import net.miginfocom.swing.MigLayout;
import org.kesler.appl2.logic.Applicator;
import org.kesler.appl2.logic.Reception;
import org.kesler.appl2.util.ResourcesUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Панель выбора заявителей
class ApplicatorsPanel extends JPanel {
    private JLabel serviceNameLabel;
    private ApplicatorsListModel applicatorsListModel;
    private LastApplicatorsListModel lastApplicatorsListModel;
    private int selectedApplicatorIndex;

    ApplicatorsPanel(final MakeReceptionViewController controller) {
        super(new MigLayout("fillx"));

        this.add(new JLabel("Услуга: "), "span, ay top");
        serviceNameLabel = new JLabel("Не определена");
        serviceNameLabel.setBorder(BorderFactory.createEtchedBorder());

        this.add(serviceNameLabel, "growx, wrap, gapbottom 10");

        this.add(new JLabel("Заявители: "),"wrap");

        // Добавляем список заявителей
        applicatorsListModel = new ApplicatorsListModel(controller.getApplicators());
        final JList applicatorsList = new JList(applicatorsListModel);

        // Запоминаем выбранного заявителя
        applicatorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        applicatorsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
                selectedApplicatorIndex = applicatorsList.getSelectedIndex();
            }
        });

        // Редактирование заявителя по двойному щелчку
        applicatorsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent ev) {
                if (ev.getClickCount() == 2) {
                    controller.editApplicator(selectedApplicatorIndex);
                }
            }
        });

        JScrollPane applicatorsListScrollPane = new JScrollPane(applicatorsList);


        this.add(applicatorsListScrollPane, "span, growx");


        // Кнопка добавления заявителя
        final JButton addButton = new JButton();
        addButton.setIcon(ResourcesUtil.getIcon("add.png"));

        final JPopupMenu applicatorSelectorPopupMenu = new JPopupMenu();
        JMenuItem flMenuItem = new JMenuItem("Физ. лицо");
        flMenuItem.setIcon(ResourcesUtil.getIcon("user.png"));
        flMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.addApplicatorFL();
            }
        });

        JMenuItem ipMenuItem = new JMenuItem("ИП");
        ipMenuItem.setIcon(ResourcesUtil.getIcon("user_suit.png"));
        ipMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.addApplicatorIP();
            }
        });

        JMenuItem ulMenuItem = new JMenuItem("Юр. лицо");
        ulMenuItem.setIcon(ResourcesUtil.getIcon("chart_organisation.png"));
        ulMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.addApplicatorUL();
            }
        });

        applicatorSelectorPopupMenu.add(flMenuItem);
        applicatorSelectorPopupMenu.add(ipMenuItem);
        applicatorSelectorPopupMenu.add(ulMenuItem);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                applicatorSelectorPopupMenu.show(addButton,addButton.getWidth(),0);
            }
        });

        // Кнопка редактирования заявителя
        JButton editButton = new JButton();
        editButton.setIcon(ResourcesUtil.getIcon("pencil.png"));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.editApplicator(selectedApplicatorIndex);
            }
        });

        // Кнопка удаления заявителя
        JButton deleteButton = new JButton();
        deleteButton.setIcon(ResourcesUtil.getIcon("delete.png"));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                controller.removeApplicator(selectedApplicatorIndex);
            }
        });

        // Кнопка выбора недавних списков заявителей
        WebButton selectApplicatorsFromLastReceptionsButton = new WebButton(ResourcesUtil.getIcon("book_previous.png"));
        selectApplicatorsFromLastReceptionsButton.setToolTipText("Выбрать из недавних");
        selectApplicatorsFromLastReceptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.readLastReceptions();
            }
        });
        final WebButtonPopup selectApplicatorsFromLastReceptionPopup = new WebButtonPopup(selectApplicatorsFromLastReceptionsButton, PopupWay.downRight);

        JPanel lastReceptionsPopupPanel = new JPanel(new MigLayout("fill"));
        lastApplicatorsListModel = new LastApplicatorsListModel();
        final JList lastApplicatorsList = new JList(lastApplicatorsListModel);
        lastApplicatorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane lastApplicatorsListScrollPane = new JScrollPane(lastApplicatorsList);
//            lastApplicatorsListScrollPane.setSize(300,200);

        JButton selectFromLastReceptionsButton = new JButton("Выбрать");
        selectFromLastReceptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = lastApplicatorsList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Reception selectedReception = lastApplicatorsListModel.getLastReceptions().get(selectedIndex);
                    controller.copyApplicatorsFromReception(selectedReception);
                    selectApplicatorsFromLastReceptionPopup.hidePopup();
                }
            }
        });

        lastReceptionsPopupPanel.add(lastApplicatorsListScrollPane,"w 300, h 200, wrap");
        lastReceptionsPopupPanel.add(selectFromLastReceptionsButton);

        selectApplicatorsFromLastReceptionPopup.setContent(lastReceptionsPopupPanel);





        this.add(addButton,"split");
        this.add(editButton);
        this.add(deleteButton);
        this.add(selectApplicatorsFromLastReceptionsButton, "wrap");

    }

    // Модель данных для JList заявителей
    class ApplicatorsListModel<Applicator> extends AbstractListModel {
        List<Applicator> applicators;

        ApplicatorsListModel(List<Applicator> applicators) {
            this.applicators = applicators;
        }

        @Override
        public Applicator getElementAt(int index) {
            return applicators.get(index);
        }

        @Override
        public int getSize() {
            return applicators.size();
        }

        public void applicatorAdded(int index) {
            fireIntervalAdded(this,index,index);
        }

        public void applicatorUpdated(int index) {
            fireContentsChanged(this,index,index);
        }

        public void applicatorRemoved(int index) {
            fireIntervalRemoved(this,index,index);
        }

        void setApplicators(List<Applicator> applicators) {
            this.applicators = applicators;
            fireContentsChanged(this, 0, applicators.size()-1);
        }
    }


    class LastApplicatorsListModel extends AbstractListModel {
        List<Reception> lastReceptions;
        LastApplicatorsListModel() {
            lastReceptions = new ArrayList<Reception>();
        }

        void setLastReceptions(List<Reception> lastReceptions) {
            this.lastReceptions = lastReceptions;
            fireContentsChanged(this,0,lastReceptions.size()-1);
        }

        List<Reception> getLastReceptions() {
            return lastReceptions;
        }

        @Override
        public Object getElementAt(int index) {
            return "<html>" + lastReceptions.get(index).getApplicatorsNames() + "</html>";
        }

        @Override
        public int getSize() {
            return lastReceptions.size();
        }

    }




    ////// Методы для установки и обновления содержимого ApplicatorsPanel

    // Вызывается контроллером при добавлении заявителя
    void applicatorAdded(int index) {
        applicatorsListModel.applicatorAdded(index);
    }

    void applicatorUpdated(int index) {
        applicatorsListModel.applicatorUpdated(index);
    }

    void applicatorRemoved(int index) {
        applicatorsListModel.applicatorRemoved(index);
    }

    // Вызывается ApplicatorsReceptionViewState
    void setApplicators(List<Applicator> applicators) {
        applicatorsListModel.setApplicators(applicators);
    }

    void setServiceName(String serviceName) {
        serviceNameLabel.setText("<html>" + serviceName + "</html>");
    }

    void setLastReceptions(List<Reception> receptions) {
        lastApplicatorsListModel.setLastReceptions(receptions);
    }



}
