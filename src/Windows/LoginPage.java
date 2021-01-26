package Windows;

import Application.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JPanel MainPanel;
    private JLabel UsernameLabel;
    private JLabel PasswordLabel;
    private JTextField UsernameField;
    private JTextField PasswordField;
    private JButton LoginButton;
    private JButton signUpButton;
    private LoginPage instance;

    public LoginPage() {
        super("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 400));
        setContentPane(MainPanel);

        instance = this;

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // as a proof of concept i used the user's name as username and the user's surname as password
                String searchedName = UsernameField.getText() + " " + PasswordField.getText();

                // to log in as admin
                if (searchedName.equals("admin admin")) {
                    setVisible(false);
                    AdminPage adminPage = new AdminPage();
                    return;
                }

                // warning for empty input
                if (searchedName.equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Please insert a valid username");
                }
                else {
                    Application app = Application.getInstance();
                    Consumer toFind = null;

                    // firstly search for a user
                    for (User user_in_app : app.getUsers()) {
                        String usercurrent = user_in_app.getPrenume() + " " + user_in_app.getNume();
                        if (usercurrent.equals(searchedName)) {
                            toFind = user_in_app;
                            break;
                        }
                    }

                    // if didn't find a user
                    if (toFind == null) {
                        for (Company company : app.getCompanies()) {
                            // search for recrutier
                            for (Recruiter recruiter : company.getRecruiters()) {
                                String recruitercurrent = recruiter.getPrenume() + " " + recruiter.getNume();
                                if (recruitercurrent.equals(searchedName)) {
                                    toFind = recruiter;
                                    break;
                                }
                            }

                            if (toFind == null) {
                                // search for employee
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
                                    setVisible(false);
                                    // open a page for employee
                                    ProfilePage profilePage = new ProfilePage(toFind, 1);
                                    return;
                                }
                            } else {
                                setVisible(false);
                                // open a page for recruiter
                                ProfilePage profilePage = new ProfilePage(toFind, 1);
                                return;
                            }
                        }
                    } else {
                        setVisible(false);
                        // open page for user
                        ProfilePage profilePage = new ProfilePage(toFind, 1);
                        return;
                    }

                    // search for manager
                    for (Company company : app.getCompanies()) {
                        String managerName = company.getManager().getPrenume() + " " + company.getManager().getNume();
                        if (managerName.equals(searchedName)) {
                            toFind = company.getManager();
                            break;
                        }
                    }

                    if (toFind == null) {
                        JOptionPane.showMessageDialog(null, "The user was not found");
                        UsernameField.setText("");
                        PasswordField.setText("");
                    } else {
                        Manager manager = (Manager) toFind;
                        setVisible(false);
                        // open page for manager
                        ManagerPage managerPage = new ManagerPage(manager);
                    }
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupPage signupPage = new SignupPage(instance);
            }
        });

        getRootPane().setDefaultButton(LoginButton);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    public JTextField getUsernameField() {
        return UsernameField;
    }

    public JTextField getPasswordField() {
        return PasswordField;
    }
}
