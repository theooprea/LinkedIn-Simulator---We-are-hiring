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

public class ApplyPage extends JFrame {
    private JPanel MainPanel;
    private JButton ApplyButton;
    private JLabel TitleLabel;
    private JScrollPane CompaniesPane;
    private JScrollPane JobsPane;
    private JList companiesJlist;
    private JList jobsList;
    public ApplyPage(User user) {
        super("Apply Page");
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        TitleLabel.setText("Available fobs for: " + user.getPrenume() + " " + user.getNume());

        // get the jobs available for the loged in user according to the companies it wants, all companies by default
        Application app = Application.getInstance();
        Vector<Company> companies = new Vector<>();
        for (Company company : app.getCompanies()) {
            if (user.getCompanies().contains(company.getNume())) {
                companies.add(company);
            }
        }
        if (companies.size() == 0) {
            companies = new Vector<>(app.getCompanies());
        }
        companiesJlist = new JList(companies);
        CompaniesPane.setViewportView(companiesJlist);
        jobsList = new JList();
        ApplyButton.setEnabled(false);

        companiesJlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (companiesJlist.isSelectionEmpty()) {
                        return;
                    }
                    else {
                        // search available jobs in the selected company
                        Company selected = (Company) companiesJlist.getSelectedValue();
                        Vector<Job> possibleJobs = new Vector<>();
                        for (Job job : selected.getJobs()) {
                            if (job.meetsRequirements(user)) {
                                // chacks so that the user can't add multiple requests
                                Boolean ok = true;
                                for (Request<Job, Consumer> request : selected.getManager().getRequests()) {
                                    if (request.getValue1().equals(user) && job.equals(request.getKey())) {
                                        ok = false;
                                        break;
                                    }
                                }
                                if (ok) {
                                    possibleJobs.add(job);
                                }
                            }
                        }
                        jobsList = new JList(possibleJobs);
                        jobsList.addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting()) {
                                    if (jobsList.isSelectionEmpty()) {
                                        ApplyButton.setEnabled(false);
                                    }
                                    else {
                                        ApplyButton.setEnabled(true);
                                    }
                                }
                            }
                        });
                        JobsPane.setViewportView(jobsList);
                    }
                }
            }
        });

        ApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Job jobapply = (Job) jobsList.getSelectedValue();
                jobapply.apply(user);
                JOptionPane.showMessageDialog(null, "The request was submitted successfully");
                Company selected = (Company) companiesJlist.getSelectedValue();
                Vector<Job> possibleJobs = new Vector<>();
                for (Job job : selected.getJobs()) {
                    if (job.meetsRequirements(user)) {
                        // remove jobs the user has already aplied to
                        Boolean ok = true;
                        for (Request<Job, Consumer> request : selected.getManager().getRequests()) {
                            if (request.getValue1().equals(user) && job.equals(request.getKey())) {
                                ok = false;
                                break;
                            }
                        }
                        if (ok) {
                            possibleJobs.add(job);
                        }
                    }
                }
                jobsList = new JList(possibleJobs);
                jobsList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            if (jobsList.isSelectionEmpty()) {
                                ApplyButton.setEnabled(false);
                            }
                            else {
                                ApplyButton.setEnabled(true);
                            }
                        }
                    }
                });
                JobsPane.setViewportView(jobsList);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
