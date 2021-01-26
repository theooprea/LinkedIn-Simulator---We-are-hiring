package Windows;

import Application.*;
import Helpers.LimbaStraina;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AdminPage extends JFrame {
    private JButton clearButton;
    private JPanel mainPanel;
    private JScrollPane CompaniesPane;
    private JScrollPane UsersPane;
    private JTextArea GeneralDetails;
    private JTextArea SalariesExperience;
    private JTextArea DepartmentsEducation;
    private JLabel MainLabel;
    private JScrollPane GeneralDetailsPane;
    private JScrollPane DepEdPane;
    private JScrollPane SalExpPane;
    private JButton searchButton;
    private JLabel SearchConsumerLabel;
    private JList companyJList;
    private JList usersJlist;
    private Vector<Company> companies;
    private Vector<User> users;
    public AdminPage() {
        super("Admin Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1366, 768));
        setContentPane(mainPanel);

        GeneralDetails.setBackground(new Color(48, 48, 48));
        DepartmentsEducation.setBackground(new Color(48, 48, 48));
        SalariesExperience.setBackground(new Color(48, 48, 48));

        Application app = Application.getInstance();
        companies = new Vector<>(app.getCompanies());
        companyJList = new JList(companies);
        companyJList.setBackground(new Color(48, 48, 48));
        companyJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (companyJList.isSelectionEmpty()) {
                    return;
                }
                else {
                    Company selected = (Company) companyJList.getSelectedValue();
                    GeneralDetails.setText(selected.getNume() + " " + selected.getManager().getPrenume() + " " + selected.getManager().getNume());

                    // building the data string for the departments of the selected company
                    String departments = "";
                    for (Department department : selected.getDepartments()) {
                        departments += department.getClass().getSimpleName() + "\nEmployees: ";
                        for (Employee employee : department.getEmployees()) {
                            departments += employee.getPrenume() + " " + employee.getNume() + "; ";
                        }
                        departments += "\nJobs: ";
                        for (Job job : department.getJobs()) {
                            departments += job.getName() + "; ";
                        }
                        departments += "\n\n";
                    }
                    DepartmentsEducation.setText(departments);

                    // building the salary string for the selected company
                    String salary = "";
                    for (Department department : selected.getDepartments()) {
                        salary += department.getClass().getSimpleName() + ":\nBudget: " + department.getTotalSalaryBudget() + "\n\n";
                    }
                    SalariesExperience.setText(salary);

                    usersJlist.clearSelection();
                }
            }
        });
        CompaniesPane.setViewportView(companyJList);

        users = new Vector<>(app.getUsers());
        usersJlist = new JList(users);
        usersJlist.setBackground(new Color(48, 48, 48));
        usersJlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (usersJlist.isSelectionEmpty()) {
                    return;
                }
                else {
                    User selected = (User) usersJlist.getSelectedValue();

                    // building the info string for the selected user
                    String info = "";
                    info += "User: " + selected.getPrenume() + " " + selected.getNume() + "\n\n";
                    info += "Email: " + selected.getEmail() + " " + " Telephone: " + selected.getTelefon() +
                            "\nBirthday: " + selected.getBirthday() + " Sex: " + selected.getSex() + "\n\n";
                    info += "Foreign Languages: \n";
                    for (LimbaStraina lang : selected.getLimbiStraine()) {
                        info += lang.limba + ": " + lang.skill + "\n";
                    }
                    GeneralDetails.setText(info);

                    // building the education string for the selected user
                    String education = "";
                    for (Education ed : selected.getEducation()) {
                        education += ed.getEducation() + " " + ed.getInstitution() + "\n";
                        education += ed.getStartString() + " - " + ed.getEndString() + "\n\n";
                    }
                    DepartmentsEducation.setText(education);

                    // building the experience string for the selected user
                    String experience = "";
                    for (Experience exp : selected.getExperience()) {
                        experience += exp.getCompanie() + " " + exp.getPozitie() + "\n";
                        experience += exp.getStartString() + " - " + exp.getEndString() + "\n\n";
                    }
                    SalariesExperience.setText(experience);

                    companyJList.clearSelection();
                }
            }
        });
        UsersPane.setViewportView(usersJlist);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralDetails.setText("");
                DepartmentsEducation.setText("");
                SalariesExperience.setText("");
                usersJlist.clearSelection();
                companyJList.clearSelection();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchUser searchUser = new SearchUser(3);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
