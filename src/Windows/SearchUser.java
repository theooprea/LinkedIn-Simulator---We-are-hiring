package Windows;

import Application.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchUser extends JFrame {
    private JTextField SearchField;
    private JPanel MainPanel;
    private JButton SearchButton;
    private JLabel SearchLabel;
    public SearchUser(Integer admin) {
        super("Search Window");
        setMinimumSize(new Dimension(500, 300));
        setContentPane(MainPanel);

        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // similar to login page search for user, recruiter, employee and manager, giving them limited permisions
                // when opening a page
                String searchedName = SearchField.getText();
                if (searchedName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please insert a valid username");
                }
                else {
                    Application app = Application.getInstance();
                    Consumer toFind = null;

                    for (User user_in_app : app.getUsers()) {
                        String usercurrent = user_in_app.getPrenume() + " " + user_in_app.getNume();
                        if (usercurrent.equals(searchedName)) {
                            toFind = user_in_app;
                            break;
                        }
                    }

                    if (toFind == null) {
                        for (Company company : app.getCompanies()) {
                            for (Recruiter recruiter : company.getRecruiters()) {
                                String recruitercurrent = recruiter.getPrenume() + " " + recruiter.getNume();
                                if (recruitercurrent.equals(searchedName)) {
                                    toFind = recruiter;
                                    break;
                                }
                            }

                            if (toFind == null) {
                                for (Department department : company.getDepartments()) {
                                    for (Employee employee : department.getEmployees()) {
                                        String emplyeecurrent = employee.getPrenume() + " " + employee.getNume();
                                        if (emplyeecurrent.equals(searchedName)) {
                                            toFind = employee;
                                            break;
                                        }
                                    }
                                }
                                if (toFind != null) {
                                    ProfilePage profilePage = new ProfilePage(toFind, admin);
                                    return;
                                }
                            }
                            else {
                                ProfilePage profilePage = new ProfilePage(toFind, admin);
                                return;
                            }
                        }
                    }
                    else {
                        ProfilePage profilePage = new ProfilePage(toFind, admin);
                        return;
                    }

                    for (Company company : app.getCompanies()) {
                        String managerName = company.getManager().getPrenume() + " " + company.getManager().getNume();
                        if (managerName.equals(searchedName)) {
                            toFind = company.getManager();
                            break;
                        }
                    }

                    if (toFind == null) {
                        JOptionPane.showMessageDialog(null, "The user was not found");
                        SearchField.setText("");
                    }
                    else {
                        Manager manager = (Manager) toFind;
                        ManagerPage managerPage = new ManagerPage(manager);
                    }
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
