package Windows;

import Application.*;
import Helpers.FriendRequest;
import Helpers.LimbaStraina;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePage extends JFrame {
    private JTextArea GeneralInfo;
    private JPanel MainPanel;
    private JTextArea Education;
    private JTextArea Experience;
    private JLabel Username;
    private JButton InboxButton;
    private JTextField FriendField;
    private JButton FriendButton;
    private JTextArea Friends;
    private JLabel FriendsLabel;
    private JButton SpecialAction;
    private JButton LogoutButton;
    private JButton SearchUserButton;
    private JLabel GeneralInfoLabel;
    private JLabel EducationLabel;
    private JLabel ExperienceLabel;
    private JScrollPane GeneralInfoPane;
    private JScrollPane EducationPane;
    private JScrollPane ExperiencePane;
    private JScrollPane FriendsPane;
    private JButton editEducationButton;
    private JButton editExperienceButton;
    private JButton editLanguagesButton;
    private JButton menuButton;
    private JPanel MenuPanel;
    private JButton editInfoButton;
    private ProfilePage instace = null;
    private Boolean menuOpen;
    // admin: 1 for actual user, 2 for admin, 3 for visitor
    public ProfilePage(Consumer user, Integer admin) {
        super("Profile Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1366, 768));
        setContentPane(MainPanel);

        instace = this;

        menuOpen = false;
        MenuPanel.setVisible(false);
        menuButton.addActionListener(new ActionListener() {
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

        if (admin != 1) {
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            setMinimumSize(new Dimension(720, 480));
        }

        update(user);

        InboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InboxPage inboxPage = new InboxPage(user, instace);
            }
        });

        FriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application app = Application.getInstance();
                String username = FriendField.getText();
                Consumer toFind = null;

                // similar to the function of the login page, we seek a user, then a recruiter, then an employee
                for (User user_in_app : app.getUsers()) {
                    String usercurrent = user_in_app.getPrenume() + " " + user_in_app.getNume();
                    if (usercurrent.equals(username)) {
                        toFind = user_in_app;
                        break;
                    }
                }

                if (toFind == null) {
                    for (Company company : app.getCompanies()) {
                        if (toFind == null) {
                            for (Recruiter recruiter : company.getRecruiters()) {
                                String recruitercurrent = recruiter.getPrenume() + " " + recruiter.getNume();
                                if (recruitercurrent.equals(username)) {
                                    toFind = recruiter;
                                    break;
                                }
                            }

                            if (toFind == null) {
                                for (Department department : company.getDepartments()) {
                                    for (Employee employee : department.getEmployees()) {
                                        String emplyeecurrent = employee.getPrenume() + " " + employee.getNume();
                                        if (emplyeecurrent.equals(username)) {
                                            toFind = employee;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // if no consumer was found
                if (toFind == null) {
                    JOptionPane.showMessageDialog(null, "The user was not found");
                    FriendField.setText("");
                }
                else {
                    // not to add friend duplicates
                    if (toFind.getPrieteni().contains(user)) {
                        JOptionPane.showMessageDialog(null, "You are already friends with this user!");
                        return;
                    }
                    for (FriendRequest friendRequest : toFind.getFriendRequests()) {
                        if (friendRequest.getSender().equals(user)) {
                            JOptionPane.showMessageDialog(null, "You have already sent a friend request to this user");
                            return;
                        }
                    }

                    toFind.addFriendRequest(new FriendRequest("Add me to your connections list!", user));
                    JOptionPane.showMessageDialog(null, "Freind request sent succesfully");
                }
            }
        });

        if (user.getClass().getSimpleName().equals("User")) {
            SpecialAction.setText("Find a job");
        }
        else if (user.getClass().getSimpleName().equals("Recruiter")) {
            SpecialAction.setText("Find new talents");
        }
        else {
            SpecialAction.setVisible(false);
        }

        SpecialAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getClass().getSimpleName().equals("User")) {
                    ApplyPage applyPage = new ApplyPage((User) user);
                }
                else {
                    RecruitPage recruitPage = new RecruitPage((Recruiter) user);
                }
            }
        });

        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                setVisible(false);
            }
        });

        SearchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // similar to add friend request, search for an user / recruiter / employee
                String searchedName = FriendField.getText();
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
                                    // using admin = 2 in order to not be capable to modify data for the other user
                                    ProfilePage profilePage = new ProfilePage(toFind, 2);
                                    return;
                                }
                            } else {
                                ProfilePage profilePage = new ProfilePage(toFind, 2);
                                return;
                            }
                        }
                    } else {
                        ProfilePage profilePage = new ProfilePage(toFind, 2);
                        return;
                    }
                    if (toFind == null) {
                        JOptionPane.showMessageDialog(null, "The user was not found");
                    }
                }
            }
        });

        // security measures, not being able to perform actions as a visitor
        if (admin == 2) {
            InboxButton.setVisible(false);
            FriendButton.setVisible(false);
            FriendField.setVisible(false);
            LogoutButton.setVisible(false);
            menuButton.setVisible(false);
            FriendsLabel.setVisible(false);
            SearchUserButton.setVisible(false);
        }

        editInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditInfoPage editInfoPage = new EditInfoPage(user, instace);
            }
        });

        editEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditEducationPage editEducationPage = new EditEducationPage(user, instace);
            }
        });

        editExperienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditExperiencePage editExperiencePage = new EditExperiencePage(user, instace);
            }
        });

        editLanguagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditLanguagesPage editLanguagesPage = new EditLanguagesPage(user, instace);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    // function to update data and refresh page
    public void update(Consumer user) {
        Username.setText(user.getClass().getSimpleName() + ": " + user.getPrenume() + " " + user.getNume());

        String info = "";
        info += "User: " + user.getPrenume() + " " + user.getNume() + "\n";
        info += "Email: " + user.getEmail() + " " + " Telephone: " + user.getTelefon() +
                "\nBirthday: " + user.getBirthday() + " Sex: " + user.getSex() + "\n";
        info += "Foreign Languages: \n";
        for (LimbaStraina lang : user.getLimbiStraine()) {
            info += lang.limba + ": " + lang.skill + "; ";
        }
        GeneralInfo.setText(info);
        GeneralInfo.setEditable(false);

        String education = "";
        for (Education ed : user.getEducation()) {
            education += ed.getEducation() + " " + ed.getInstitution() + "\n";
            education += ed.getStartString() + " - " + ed.getEndString() + "\n\n";
        }
        Education.setText(education);
        Education.setEditable(false);

        String experience = "";
        for (Experience exp : user.getExperience()) {
            experience += exp.getCompanie() + " " + exp.getPozitie() + "\n";
            experience += exp.getStartString() + " - " + exp.getEndString() + "\n\n";
        }
        Experience.setText(experience);
        Experience.setEditable(false);

        String friends = "";
        for (Consumer consumer : user.getPrieteni()) {
            friends += consumer.getPrenume() + " " + consumer.getNume() + "\n";
        }
        Friends.setText(friends);
        Friends.setEditable(false);
    }
}
