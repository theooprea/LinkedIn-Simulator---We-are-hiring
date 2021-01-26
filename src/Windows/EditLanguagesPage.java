package Windows;

import Application.*;
import Exceptions.LanguageException;
import Helpers.LimbaStraina;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditLanguagesPage extends JFrame {
    private JPanel MainPanel;
    private JTextField LanguageField;
    private JTextField SkillField;
    private JButton addLanguageButton;
    private JButton editLanguageButton;
    private JButton removeLanguageButton;
    private JScrollPane LanguagesPane;
    private DefaultListModel<LimbaStraina> limbaStrainaDefaultListModel = new DefaultListModel<>();
    private JList<LimbaStraina> limbaStrainaJList;

    public EditLanguagesPage(Consumer user, ProfilePage parent) {
        super("Edit Languages Page");
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        // similar to education and experience edit pages excepting the dates parsing
        for (LimbaStraina limbaStraina : user.getLimbiStraine()) {
            limbaStrainaDefaultListModel.addElement(limbaStraina);
        }
        limbaStrainaJList = new JList<>(limbaStrainaDefaultListModel);
        LanguagesPane.setViewportView(limbaStrainaJList);

        removeLanguageButton.setEnabled(false);
        editLanguageButton.setEnabled(false);

        limbaStrainaJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (limbaStrainaJList.isSelectionEmpty()) {
                    removeLanguageButton.setEnabled(false);
                    editLanguageButton.setEnabled(false);
                    LanguageField.setText("");
                    SkillField.setText("");
                }
                else  {
                    removeLanguageButton.setEnabled(true);
                    editLanguageButton.setEnabled(true);
                    LimbaStraina selected = limbaStrainaJList.getSelectedValue();
                    LanguageField.setText(selected.limba);
                    SkillField.setText(selected.skill);
                }
            }
        });

        addLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LanguageField.getText().equals("") || SkillField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all data fields to proceed");
                }
                else {
                    LimbaStraina limbaStraina = null;
                    try {
                        limbaStraina = new LimbaStraina(LanguageField.getText(), SkillField.getText());
                    }
                    catch (LanguageException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    if (limbaStraina != null) {
                        limbaStrainaDefaultListModel.addElement(limbaStraina);
                        user.addLimbaStraina(limbaStraina);
                        parent.update(user);
                        limbaStrainaJList.clearSelection();
                    }
                }
            }
        });

        editLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (LanguageField.getText().equals("") || SkillField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all data fields to proceed");
                }
                else {
                    if (!SkillField.getText().equals("Beginner") && !SkillField.getText().equals("Advanced")  &&
                            !SkillField.getText().equals("Experienced")) {
                        JOptionPane.showMessageDialog(null, "The experience level for this language is invalid!");
                        LimbaStraina selected = limbaStrainaJList.getSelectedValue();
                        SkillField.setText(selected.skill);
                        return;
                    }
                    LimbaStraina selected = limbaStrainaJList.getSelectedValue();
                    int index = limbaStrainaDefaultListModel.indexOf(selected);
                    selected.limba = LanguageField.getText();
                    selected.skill = SkillField.getText();

                    limbaStrainaDefaultListModel.setElementAt(selected, index);
                    parent.update(user);
                    limbaStrainaJList.clearSelection();
                }
            }
        });

        removeLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LimbaStraina selected = limbaStrainaJList.getSelectedValue();
                limbaStrainaDefaultListModel.removeElement(selected);
                user.removeLimbaStraina(selected);
                parent.update(user);
                limbaStrainaJList.clearSelection();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
