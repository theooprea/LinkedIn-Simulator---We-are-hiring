package Windows;

import Application.Consumer;
import Application.Experience;
import Exceptions.InvalidDatesException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class EditExperiencePage extends JFrame {
    private JPanel MainPanel;
    private JTextField PositionField;
    private JTextField CompanyField;
    private JTextField StartDateField;
    private JTextField EndDateField;
    private JButton addExperienceButton;
    private JButton editExperienceButton;
    private JButton removeExperienceButton;
    private JScrollPane ExperiencePane;
    private DefaultListModel<Experience> experienceDefaultListModel = new DefaultListModel<>();
    private JList<Experience> experienceJList;
    public EditExperiencePage(Consumer user, ProfilePage parent) {
        super("Edit Experience Page");
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        // constructing the default list model, the jlist and the jscrollpane
        for (Experience experience : user.getExperience()) {
            experienceDefaultListModel.addElement(experience);
        }
        experienceJList = new JList<>(experienceDefaultListModel);
        ExperiencePane.setViewportView(experienceJList);

        removeExperienceButton.setEnabled(false);
        editExperienceButton.setEnabled(false);

        // the listeners are similar to the education ones
        experienceJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (experienceJList.isSelectionEmpty()) {
                    removeExperienceButton.setEnabled(false);
                    editExperienceButton.setEnabled(false);
                    PositionField.setText("");
                    CompanyField.setText("");
                    StartDateField.setText("");
                    EndDateField.setText("");
                }
                else {
                    removeExperienceButton.setEnabled(true);
                    editExperienceButton.setEnabled(true);
                    Experience selected = experienceJList.getSelectedValue();
                    PositionField.setText(selected.getPozitie());
                    CompanyField.setText(selected.getCompanie());
                    StartDateField.setText(selected.getStartString());
                    EndDateField.setText(selected.getEndString());
                }
            }
        });

        removeExperienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Experience selected = experienceJList.getSelectedValue();
                experienceDefaultListModel.removeElement(selected);
                user.remove(selected);
                parent.update(user);
                experienceJList.clearSelection();
            }
        });

        editExperienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PositionField.getText().equals("") || CompanyField.getText().equals("") ||
                    StartDateField.getText().equals("") || EndDateField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all data fields to proceed");
                }
                else {
                    Experience selected = experienceJList.getSelectedValue();
                    int index = experienceDefaultListModel.indexOf(selected);
                    selected.setPozitie(PositionField.getText());
                    selected.setCompanie(CompanyField.getText());

                    String[] dateStart = StartDateField.getText().split("\\.");
                    int yearS = Integer.parseInt(dateStart[2]);
                    int monthS = Integer.parseInt(dateStart[1]);
                    int dayS = Integer.parseInt(dateStart[0]);
                    Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                    Date endDate = null;
                    if (!EndDateField.getText().equals("null")) {
                        String[] dateEnd = EndDateField.getText().split("\\.");
                        int yearE = Integer.parseInt(dateEnd[2]);
                        int monthE = Integer.parseInt(dateEnd[1]);
                        int dayE = Integer.parseInt(dateEnd[0]);
                        endDate = new Date(yearE - 1900, monthE - 1, dayE);
                    }
                    if (endDate != null && startDate.after(endDate)) {
                        JOptionPane.showMessageDialog(null, "The dates are invalid chronologically!");
                        return;
                    }
                    selected.setStart(startDate);
                    selected.setEnd(endDate);
                    experienceDefaultListModel.setElementAt(selected, index);
                    parent.update(user);
                    experienceJList.clearSelection();
                }
            }
        });

        addExperienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PositionField.getText().equals("") || CompanyField.getText().equals("") ||
                        StartDateField.getText().equals("") || EndDateField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all data fields to proceed");
                }
                else {
                    Experience experience = null;
                    try {
                        String[] dateStart = StartDateField.getText().split("\\.");
                        int yearS = Integer.parseInt(dateStart[2]);
                        int monthS = Integer.parseInt(dateStart[1]);
                        int dayS = Integer.parseInt(dateStart[0]);
                        Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                        Date endDate = null;
                        if (!EndDateField.getText().equals("null")) {
                            String[] dateEnd = EndDateField.getText().split("\\.");
                            int yearE = Integer.parseInt(dateEnd[2]);
                            int monthE = Integer.parseInt(dateEnd[1]);
                            int dayE = Integer.parseInt(dateEnd[0]);
                            endDate = new Date(yearE - 1900, monthE - 1, dayE);
                        }
                        experience = new Experience(startDate, endDate, PositionField.getText(), CompanyField.getText());
                    }
                    catch (InvalidDatesException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    if (experience != null) {
                        experienceDefaultListModel.addElement(experience);
                        user.add(experience);
                        parent.update(user);
                        experienceJList.clearSelection();
                    }
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();

    }
}
