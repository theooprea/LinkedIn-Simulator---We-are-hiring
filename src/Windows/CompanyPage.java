package Windows;

import Application.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CompanyPage extends JFrame{
    private JPanel MainPanel;
    private JTextArea Departments;
    private JTextArea Bugets;
    private JButton MenuButton;
    private JButton fireEmployeeButton;
    private JButton addEmployeeButton;
    private JButton addDepartmentButton;
    private JPanel MenuPanel;
    private JScrollPane DepartmentsPane;
    private JScrollPane BugetsPane;
    private JScrollPane RecruitersPane;
    private JLabel CompanyLabel;
    private JLabel ManagerLabel;
    private JLabel DepartmentsLabel;
    private JLabel RecruitersLabel;
    private JLabel BugetsLabel;
    private JButton removeDepartmentButton;
    private JButton removeRecruiterButton;
    private JButton moveDepartmentButton;
    private JButton moveEmployeeButton;
    private DefaultListModel recruitersModel;
    private JList recruitersList;
    private Boolean menuOpen;

    public CompanyPage(Company company) {
        super(company.getNume());
        setMinimumSize(new Dimension(1280, 720));
        setContentPane(MainPanel);

        CompanyLabel.setText(company.getNume());
        ManagerLabel.setText(company.getManager().getPrenume() + " " + company.getManager().getNume());

        updateData(company);

        menuOpen = false;
        MenuPanel.setVisible(false);

        // the menu buttons opens and closes a panel of additional buttons
        MenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!menuOpen) {
                    menuOpen = true;
                    MenuPanel.setVisible(true);
                }
                else {
                    menuOpen = false;
                    MenuPanel.setVisible(false);
                }
            }
        });

        // for each button I open an action window with each type for each action, as well as add a WindowAdapter as
        // window listener for each newly opened window
        addDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyMenuActionPage actionPage = new CompanyMenuActionPage(company, 0);
                actionPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        updateData(company);
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        updateData(company);
                    }
                });
            }
        });

        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyMenuActionPage actionPage = new CompanyMenuActionPage(company, 1);
                actionPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        updateData(company);
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        updateData(company);
                    }
                });
            }
        });

        fireEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyMenuActionPage actionPage = new CompanyMenuActionPage(company, 2);
                actionPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        updateData(company);
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        updateData(company);
                    }
                });
            }
        });

        removeDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyMenuActionPage actionPage = new CompanyMenuActionPage(company, 3);
                actionPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        updateData(company);
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        updateData(company);
                    }
                });
            }
        });

        removeRecruiterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyMenuActionPage actionPage = new CompanyMenuActionPage(company, 4);
                actionPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        updateData(company);
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        updateData(company);
                    }
                });
            }
        });

        moveDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyMenuActionPage actionPage = new CompanyMenuActionPage(company, 5);
                actionPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        updateData(company);
                    }
                    @Override
                    public void windowClosing(WindowEvent e) {
                        updateData(company);
                    }
                });
            }
        });

        moveEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyMenuActionPage actionPage = new CompanyMenuActionPage(company, 6);
                actionPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        updateData(company);
                    }
                });
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    // function to update company data every time an action is performed
    public void updateData(Company company) {
        String departments = "";
        for (Department department : company.getDepartments()) {
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
        Departments.setText(departments);
        Departments.setEditable(false);

        String salary = "";
        for (Department department : company.getDepartments()) {
            salary += department.getClass().getSimpleName() + ":\nBudget: " + department.getTotalSalaryBudget() + "\n\n";
        }
        Bugets.setText(salary);
        Bugets.setEditable(false);

        recruitersModel = new DefaultListModel();
        for (Recruiter recruiter : company.getRecruiters()) {
            recruitersModel.addElement(recruiter);
        }
        recruitersList = new JList(recruitersModel);
        recruitersList.setBackground(new Color(48, 48, 48));
        RecruitersPane.setViewportView(recruitersList);
    }
}
