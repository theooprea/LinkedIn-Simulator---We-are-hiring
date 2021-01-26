package Windows;

import Application.*;
import Exceptions.InvalidDatesException;
import Exceptions.LanguageException;
import Exceptions.ResumeIncompleteException;
import Helpers.LimbaStraina;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class SignupPage extends JFrame {
    private JPanel MainPanel;
    private JLabel NameLabel;
    private JLabel SurnameLabel;
    private JLabel EmailLabel;
    private JLabel TelephoneLabel;
    private JLabel SexLabel;
    private JLabel BirthLabel;
    private JTextField BirthdateField;
    private JTextField SexField;
    private JTextField TelephoneField;
    private JTextField EmailField;
    private JTextField SurnameField;
    private JTextField NameField;
    private JLabel BirthWarningLabel;
    private JScrollPane EducationPane;
    private JTextField InstitutionField;
    private JTextField EducationField;
    private JTextField StartdateField;
    private JTextField EnddateField;
    private JTextField GPAField;
    private JButton addEducationButton;
    private JLabel PositionLabel;
    private JLabel CompanyLabel;
    private JLabel StartExpLabel;
    private JLabel EndExpLabel;
    private JTextField EndExpField;
    private JTextField StartExpField;
    private JTextField CompanyField;
    private JTextField PositionField;
    private JButton addExperienceButton;
    private JButton addLanguageButton;
    private JTextField LanguageField;
    private JTextField SkillField;
    private JScrollPane ExperiencePane;
    private JScrollPane LanguagesPane;
    private JButton createUserButton;
    private JButton removeLanguageButton;
    private JButton removeEducationButton;
    private JButton removeExperienceButton;
    private DefaultListModel<Education> educationDefaultListModel = new DefaultListModel<>();
    private DefaultListModel<Experience> experienceDefaultListModel = new DefaultListModel<>();
    private DefaultListModel<LimbaStraina> limbaStrainaDefaultListModel = new DefaultListModel<>();
    private JList<Education> educationJList;
    private JList<Experience> experienceJList;
    private JList<LimbaStraina> limbaStrainaJList;

    public SignupPage(LoginPage parent) {
        super("Sign Up");
        setMinimumSize(new Dimension(1280, 720));
        setContentPane(MainPanel);

        educationJList = new JList<>(educationDefaultListModel);
        experienceJList = new JList<>(experienceDefaultListModel);
        limbaStrainaJList = new JList<>(limbaStrainaDefaultListModel);
        EducationPane.setViewportView(educationJList);
        ExperiencePane.setViewportView(experienceJList);
        LanguagesPane.setViewportView(limbaStrainaJList);

        addLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // trying to add language considering the exception
                LimbaStraina limbaStraina = null;
                try {
                    limbaStraina = new LimbaStraina(LanguageField.getText(), SkillField.getText());
                }
                catch (LanguageException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
                if (limbaStraina != null) {
                    limbaStrainaDefaultListModel.addElement(limbaStraina);
                }
            }
        });

        addEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // trying to add an education by parsing the dates and considering the exception
                String[] dateStart = StartdateField.getText().split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                Date endDate = null;
                if (!EnddateField.getText().equals("null")) {
                    String[] dateEnd = EnddateField.getText().split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    endDate = new Date(yearE - 1900, monthE - 1, dayE);
                }

                Education education = null;
                try {
                    education = new Education(startDate, endDate, InstitutionField.getText(), EducationField.getText(), Double.parseDouble(GPAField.getText()));
                }
                catch (InvalidDatesException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }

                if (education != null) {
                    educationDefaultListModel.addElement(education);
                }
            }
        });

        addExperienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // trying to add an experience after parsing the dates and considering the exception
                String[] dateStart = StartExpField.getText().split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                Date endDate = null;
                if (!EnddateField.getText().equals("null")) {
                    String[] dateEnd = EndExpField.getText().split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    endDate = new Date(yearE - 1900, monthE - 1, dayE);
                }

                Experience experience = null;
                try {
                    experience = new Experience(startDate, endDate, PositionField.getText(), CompanyField.getText());
                }
                catch (InvalidDatesException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }

                if (experience != null) {
                    experienceDefaultListModel.addElement(experience);
                }
            }
        });

        removeLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LimbaStraina selected = (LimbaStraina) limbaStrainaJList.getSelectedValue();
                limbaStrainaDefaultListModel.removeElement(selected);
            }
        });

        removeEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Education selected = (Education) educationJList.getSelectedValue();
                educationDefaultListModel.removeElement(selected);
            }
        });

        removeExperienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Experience selected = (Experience) experienceJList.getSelectedValue();
                experienceDefaultListModel.removeElement(selected);
            }
        });

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if there is empty input send warning
                if (NameField.getText().equals("") || SurnameField.getText().equals("") || EmailField.getText().equals("")
                    || TelephoneField.getText().equals("") || SexField.getText().equals("") || BirthdateField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please complete all information labels");
                }
                else {
                    // using the builder pattern build the resume and then the user
                    Resume.ResumeBuilder builder = new Resume.ResumeBuilder(NameField.getText(), SurnameField.getText())
                            .email(EmailField.getText())
                            .telefon(TelephoneField.getText())
                            .data_de_nastere(BirthdateField.getText())
                            .sex(SexField.getText());
                    // adding the languages, educations and experience to the builder
                    for (int i = 0; i < limbaStrainaDefaultListModel.getSize(); i++) {
                        builder = builder.limbaStraina(limbaStrainaDefaultListModel.elementAt(i));
                    }
                    for (int i = 0; i < educationDefaultListModel.getSize(); i++) {
                        builder = builder.education(educationDefaultListModel.getElementAt(i));
                    }
                    for (int i = 0; i < experienceDefaultListModel.getSize(); i++) {
                        builder = builder.experience(experienceDefaultListModel.getElementAt(i));
                    }
                    User user = null;
                    try {
                        user = new User(builder.build());
                    }
                    catch (ResumeIncompleteException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    // build the user
                    if (user != null) {
                        Application app = Application.getInstance();
                        app.add(user);
                        JOptionPane.showMessageDialog(null, "The user was created successfully!");
                        parent.getUsernameField().setText(user.getPrenume());
                        parent.getPasswordField().setText(user.getNume());
                        dispose();
                    }
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
