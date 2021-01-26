package Windows;

import Application.*;
import Helpers.Request;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class RecruitPage extends JFrame {
    private JPanel MainPanel;
    private JButton RequestButton;
    private JScrollPane JobsPane;
    private JScrollPane UsersPane;
    private JLabel TitleLabel;
    private JList jobsList;
    private JList usersList;
    private Company motherComp;
    public RecruitPage(Recruiter recruiter) {
        super("Apply Page");
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        // finding the company that has the recruiter
        Application app = Application.getInstance();
        Company mother = null;
        for (Company company : app.getCompanies()) {
            if (recruiter.getWorks_for().equals(company.getNume())) {
                mother = company;
            }
        }
        motherComp = mother;

        Vector<Job> jobs = new Vector<>(mother.getJobs());
        jobsList = new JList(jobs);
        JobsPane.setViewportView(jobsList);

        RequestButton.setEnabled(false);

        jobsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // in order not to perform action twice (on press and on release click)
                if (!e.getValueIsAdjusting()) {
                    if (jobsList.isSelectionEmpty()) {
                        return;
                    }
                    else {
                        // finds users that are a math for the selected job
                        Job selected = (Job) jobsList.getSelectedValue();
                        Vector<User> possibleUsers = new Vector<>();
                        for (User user : app.getUsers()) {
                            if (selected.meetsRequirements(user)) {
                                Boolean ok = true;
                                for (Request<Job, Consumer> request : motherComp.getManager().getRequests()) {
                                    if (request.getValue1().equals(user) && selected.equals(request.getKey())) {
                                        ok = false;
                                        break;
                                    }
                                }
                                if (ok) {
                                    possibleUsers.add(user);
                                }
                            }
                        }
                        usersList = new JList(possibleUsers);
                        UsersPane.setViewportView(usersList);
                        // add possibility to review the user
                        usersList.addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting()) {
                                    if (usersList.isSelectionEmpty()) {
                                        RequestButton.setEnabled(false);
                                    }
                                    else {
                                        User user = (User) usersList.getSelectedValue();
                                        ProfilePage profilePage = new ProfilePage(user, 2);
                                        RequestButton.setEnabled(true);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });

        RequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Job job = (Job) jobsList.getSelectedValue();
                User user = (User) usersList.getSelectedValue();
                recruiter.evaluate(job, user);
                jobsList.clearSelection();
                JOptionPane.showMessageDialog(null, "The request was submitted successfully");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
