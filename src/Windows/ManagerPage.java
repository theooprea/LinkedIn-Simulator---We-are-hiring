package Windows;

import Application.*;
import Helpers.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerPage extends JFrame {
    private JPanel MainPanel;
    private JButton RefuseButton;
    private JButton AcceptButton;
    private JScrollPane RequestsPane;
    private JTextArea CompanyDetails;
    private JLabel Username;
    private JButton LogoutButton;
    private JButton companyButton;
    private JButton ProcessButton;
    private JList requests;
    private DefaultListModel<Request<Job, Consumer>> requstsModel;
    public ManagerPage(Manager manager) {
        super("Manager Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1366, 768));
        setContentPane(MainPanel);

        update(manager);

        RefuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request<Job, Consumer> request = (Request<Job, Consumer>) requests.getSelectedValue();
                manager.getRequests().remove(request);
                requstsModel.removeElement(request);
            }
        });

        AcceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request<Job, Consumer> request = (Request<Job, Consumer>) requests.getSelectedValue();
                manager.getRequests().remove(request);
                requstsModel.removeElement(request);
                User user = (User) request.getValue1();
                Job jobtoemployto = request.getKey();
                jobtoemployto.setNoPositions(jobtoemployto.getNoPositions() - 1);
                if (jobtoemployto.getNoPositions() == 0) {
                    jobtoemployto.setOpen(false);
                }
                // convert user to employee and remove user from app
                Application app = Application.getInstance();
                Employee toEmploy = user.convert();
                toEmploy.setSalary(jobtoemployto.getSalary());
                toEmploy.setWorks_for(jobtoemployto.getCompany());
                app.remove(user);

                // remove all requests of the user, as well as other's for this job if the job was closed
                for (int i = 0; i < manager.getRequests().size(); i++) {
                    Request<Job, Consumer> currentRequest = manager.getRequests().get(i);
                    Job currentJob = currentRequest.getKey();
                    Consumer current = currentRequest.getValue1();
                    if (request.getValue1().equals(current) || !currentJob.getOpen()) {
                        manager.getRequests().remove(currentRequest);
                        requstsModel.removeElement(currentRequest);
                        i--;
                    }
                }

                // find the company and department that the job is tied to
                Company mother = null;
                Department toFind = null;

                for (Company company : app.getCompanies()) {
                    if (company.getManager().equals(manager)) {
                        mother = company;
                        break;
                    }
                }

                for (Department department : mother.getDepartments()) {
                    if (department.contains(jobtoemployto)) {
                        toFind = department;
                        break;
                    }
                }

                // employ the employee and update the data
                mother.add(toEmploy, toFind);
                for (Employee employee : toFind.getEmployees()) {
                    System.out.println(employee);
                }

                String companyDetails = mother.getNume() + "\n\n";
                for (Department department : mother.getDepartments()) {
                    companyDetails += department.getClass().getSimpleName() + "\nEmployees: ";
                    for (Employee employee : department.getEmployees()) {
                        companyDetails += employee.getPrenume() + " " + employee.getNume() + "; ";
                    }
                    companyDetails += "\n\n";
                }

                CompanyDetails.setText(companyDetails);
            }
        });

        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                setVisible(false);
            }
        });

        companyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application app = Application.getInstance();
                Company toFind = null;
                // find the company in the app and open a page for it
                for (Company company : app.getCompanies()) {
                    String managerString = manager.getPrenume() + " " + manager.getNume();
                    String iteratorString = company.getManager().getPrenume() + " " + company.getManager().getNume();
                    if (managerString.equals(iteratorString)) {
                        toFind = company;
                        break;
                    }
                }
                CompanyPage companyPage = new CompanyPage(toFind);
            }
        });

        ProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Company toFind = null;
                Application app = Application.getInstance();
                // process all requests that the manager has by calling the implemented method in the manager class
                for (Company company : app.getCompanies()) {
                    if (company.getNume().equals(manager.getWorks_for())) {
                        toFind = company;
                        break;
                    }
                }
                for (Job job : toFind.getJobs()) {
                    manager.process(job);
                }
                update(manager);
            }
        });

        RefuseButton.setEnabled(false);
        AcceptButton.setEnabled(false);

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    // function to refresh the page after modifies has been done
    public void update(Manager manager) {
        Username.setText(manager.getPrenume() + " " + manager.getNume() + "   ");

        Application app = Application.getInstance();
        Company toFind = null;
        for (Company company : app.getCompanies()) {
            if (company.getManager().equals(manager)) {
                toFind = company;
                break;
            }
        }

        String companyDetails = toFind.getNume() + "\n\n";
        for (Department department : toFind.getDepartments()) {
            companyDetails += department.getClass().getSimpleName() + "\nEmployees: ";
            for (Employee employee : department.getEmployees()) {
                companyDetails += employee.getPrenume() + " " + employee.getNume() + "; ";
            }
            companyDetails += "\n\n";
        }

        CompanyDetails.setText(companyDetails);
        CompanyDetails.setEditable(false);

        requstsModel = new DefaultListModel<>();
        for (Request<Job, Consumer> request : manager.getRequests()) {
            if (app.contains(request.getValue1())) {
                requstsModel.addElement(request);
            }
        }
        requests = new JList(requstsModel);
        requests.setBackground(new Color(48, 48, 48));
        RequestsPane.setViewportView(requests);

        requests.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (requests.isSelectionEmpty()) {
                    RefuseButton.setEnabled(false);
                    AcceptButton.setEnabled(false);
                }
                else {
                    RefuseButton.setEnabled(true);
                    AcceptButton.setEnabled(true);
                }
            }
        });
    }
}
