package Windows;

import Application.Consumer;
import Application.Education;
import Exceptions.InvalidDatesException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class EditEducationPage extends JFrame {
    private JPanel MainPanel;
    private JTextField GPAField;
    private JTextField EndDateField;
    private JTextField StartDateField;
    private JTextField EducationField;
    private JTextField InstitutionField;
    private JScrollPane EducationPane;
    private JButton addEducationButton;
    private JButton editEducationButton;
    private JButton removeEducationButton;
    private DefaultListModel<Education> educationDefaultListModel = new DefaultListModel<>();
    private JList<Education> educationJList;
    public EditEducationPage(Consumer user, ProfilePage parent) {
        super("Edit Education Page");
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        removeEducationButton.setEnabled(false);
        editEducationButton.setEnabled(false);

        // add the user's educations to the default list model, then the DLM to the JList and set the ScrollPane's
        // viewport view to the JList
        for (Education education : user.getEducation()) {
            educationDefaultListModel.addElement(education);
        }
        educationJList = new JList<>(educationDefaultListModel);
        EducationPane.setViewportView(educationJList);

        // on selecting (or deselecting) an education set the input fields and buttons accordingly
        educationJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (educationJList.isSelectionEmpty()) {
                    removeEducationButton.setEnabled(false);
                    editEducationButton.setEnabled(false);
                    InstitutionField.setText("");
                    EducationField.setText("");
                    StartDateField.setText("");
                    EndDateField.setText("");
                    GPAField.setText("");
                }
                else {
                    removeEducationButton.setEnabled(true);
                    editEducationButton.setEnabled(true);
                    Education selected = educationJList.getSelectedValue();
                    InstitutionField.setText(selected.getInstitution());
                    EducationField.setText(selected.getEducation());
                    StartDateField.setText(selected.getStartString());
                    EndDateField.setText(selected.getEndString());
                    GPAField.setText(selected.getMedie().toString());
                }
            }
        });

        addEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checking not to have empty input
                if (InstitutionField.getText().equals("") || EducationField.getText().equals("") || StartDateField.getText().equals("")
                    || EndDateField.getText().equals("") || GPAField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all data fields to proceed");
                }
                else {
                    // creating a new education with the given input
                    Education education = null;
                    try {
                        // parsing dates
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
                        education = new Education(startDate, endDate, InstitutionField.getText(), EducationField.getText(), Double.parseDouble(GPAField.getText()));
                    }
                    catch (InvalidDatesException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    if (education != null) {
                        educationDefaultListModel.addElement(education);
                        user.add(education);
                        parent.update(user);
                        educationJList.clearSelection();
                    }
                }
            }
        });

        editEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (InstitutionField.getText().equals("") || EducationField.getText().equals("") || StartDateField.getText().equals("")
                        || EndDateField.getText().equals("") || GPAField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all data fields to proceed");
                }
                else {
                    Education selected = educationJList.getSelectedValue();
                    int index = educationDefaultListModel.indexOf(selected);
                    selected.setInstitution(InstitutionField.getText());
                    selected.setEducation(EducationField.getText());

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
                    // if the dates are correct chronologically modify, else return
                    if (endDate != null && startDate.after(endDate)) {
                        JOptionPane.showMessageDialog(null, "The dates are invalid chronologically!");
                        return;
                    }
                    selected.setStart(startDate);
                    selected.setEnd(endDate);
                    selected.setMedie(Double.parseDouble(GPAField.getText()));
                    educationDefaultListModel.setElementAt(selected, index);
                    parent.update(user);
                    educationJList.clearSelection();
                }
            }
        });

        removeEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Education selected = educationJList.getSelectedValue();
                educationDefaultListModel.removeElement(selected);
                user.remove(selected);
                parent.update(user);
                educationJList.clearSelection();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
