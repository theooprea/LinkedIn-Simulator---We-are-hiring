package Windows;

import Application.*;
import DesignPatterns.DepartmentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CompanyMenuActionPage extends JFrame {
    private JPanel MainPanel;
    private JPanel LabelPanels;
    private JTextField InputField1;
    private JTextField InputField2;
    private JButton Button;
    private JLabel Label1;
    private JLabel Label2;
    private JLabel TitleLabel;

    // according to the action I need, passed as argument as actionType, 0 to 5, each for an action needed
    // depending on the action I need, i disable or eneble an input field and a label, as well as change the label text
    public CompanyMenuActionPage(Company company, Integer actionType) {
        super(company.getNume());
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        if (actionType == 0) {
            TitleLabel.setText("Add department");
            Label2.setVisible(false);
            InputField2.setVisible(false);
            Label1.setText("Department name");
            Button.setText("Add");
            Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Department department : company.getDepartments()) {
                        if (department.getClass().getSimpleName().equals(InputField1.getText())) {
                            JOptionPane.showMessageDialog(null, "The department already exists");
                            InputField1.setText("");
                            return;
                        }
                    }
                    Department toAdd = DepartmentFactory.getDepartment(InputField1.getText());
                    if (toAdd == null) {
                        JOptionPane.showMessageDialog(null, "No such department");
                        InputField1.setText("");
                        return;
                    }
                    company.add(toAdd);
                    dispose();
                }
            });
        }
        else if (actionType == 1) {
            TitleLabel.setText("Add employee");
            Label1.setText("User name");
            Label2.setText("To department");
            Button.setText("Add");
            Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Department toFind = getDepartment(company, InputField2.getText());

                    Application app = Application.getInstance();
                    User userToAdd = null;
                    for (User user : app.getUsers()) {
                        String current = user.getPrenume() + " " + user.getNume();
                        if (current.equals(InputField1.getText())) {
                            userToAdd = user;
                            break;
                        }
                    }

                    if (userToAdd == null) {
                        JOptionPane.showMessageDialog(null, "No such user");
                        InputField1.setText("");
                        return;
                    }

                    if (toFind == null) {
                        JOptionPane.showMessageDialog(null, "No such department");
                        InputField2.setText("");
                        return;
                    }
                    ArrayList<Job> jobs = new ArrayList<>();
                    for (Job job : toFind.getJobs()) {
                        jobs.add(job);
                    }
                    if (jobs.size() == 0) {
                        JOptionPane.showMessageDialog(null, "No job available");
                        InputField1.setText("");
                        InputField2.setText("");
                        return;
                    }
                    Employee toAdd = userToAdd.convert();
                    toAdd.setWorks_for(jobs.get(0).getCompany());
                    toAdd.setSalary(jobs.get(0).getSalary());
                    company.add(toAdd, toFind);
                    app.remove(userToAdd);
                    dispose();
                }
            });
        }
        else if (actionType == 2) {
            TitleLabel.setText("Fire employee");
            Label2.setVisible(false);
            InputField2.setVisible(false);
            Label1.setText("Employee name");
            Button.setText("Fire");
            Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Employee toFire = getEmployee(company, InputField1.getText());
                    if (toFire == null) {
                        JOptionPane.showMessageDialog(null, "No such employee");
                        InputField1.setText("");
                        return;
                    }
                    company.remove(toFire);
                    dispose();
                }
            });
        }
        else if (actionType == 3) {
            TitleLabel.setText("Remove department");
            Label2.setVisible(false);
            InputField2.setVisible(false);
            Label1.setText("Department name");
            Button.setText("Remove");
            Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Department toFind = getDepartment(company, InputField1.getText());
                    if (toFind == null) {
                        JOptionPane.showMessageDialog(null, "No such department");
                        InputField1.setText("");
                        return;
                    }
                    company.remove(toFind);
                    dispose();
                }
            });
        }
        else if (actionType == 4) {
            TitleLabel.setText("Remove recruiter");
            Label2.setVisible(false);
            InputField2.setVisible(false);
            Label1.setText("Recruiter name");
            Button.setText("Remove");
            Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Recruiter toFind = getRecruiter(company, InputField1.getText());
                    if (toFind == null) {
                        JOptionPane.showMessageDialog(null, "No such recruiter");
                        InputField1.setText("");
                        return;
                    }
                    company.remove(toFind);
                    dispose();
                }
            });
        }
        else if (actionType == 5) {
            TitleLabel.setText("Move department");
            Label1.setText("Department name");
            Label2.setText("Move to");
            Button.setText("Move");
            Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Department source = getDepartment(company, Label1.getText());
                    Department destination = getDepartment(company, Label2.getText());
                    if (source == null) {
                        JOptionPane.showMessageDialog(null, "Department " + Label1.getText() + " not found");
                        InputField1.setText("");
                        return;
                    }
                    if (destination == null) {
                        JOptionPane.showMessageDialog(null, "Department " + Label2.getText() + " not found");
                        InputField2.setText("");
                        return;
                    }
                    company.move(source, destination);
                    dispose();
                }
            });
        }
        else {
            TitleLabel.setText("Move Employee");
            Label1.setText("Employee name");
            Label2.setText("Department name");
            Button.setText("Move");
            Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Employee toMove = getEmployee(company, InputField1.getText());
                    Department toFind = getDepartment(company, InputField2.getText());
                    if (toMove == null) {
                        JOptionPane.showMessageDialog(null, "No such emplyee");
                        InputField1.setText("");
                        return;
                    }
                    if (toFind == null) {
                        JOptionPane.showMessageDialog(null, "No such department");
                        InputField2.setText("");
                        return;
                    }
                    company.move(toMove, toFind);
                    dispose();
                }
            });
        }

        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    // function to find employee in given company by given name
    public Employee getEmployee(Company company, String nume) {
        for (Department department : company.getDepartments()) {
            for (Employee employee : department.getEmployees()) {
                String current = employee.getPrenume() + " " + employee.getNume();
                if (current.equals(nume)) {
                    return employee;
                }
            }
        }
        return null;
    }

    // function to find department in given company by given name
    public Department getDepartment(Company company, String nume) {
        for (Department department : company.getDepartments()) {
            if (department.getClass().getSimpleName().equals(nume)) {
                return department;
            }
        }
        return null;
    }

    // function to find recruiter in given company by given name
    public Recruiter getRecruiter(Company company, String nume) {
        for (Recruiter recruiter : company.getRecruiters()) {
            String current = recruiter.getPrenume() + " " + recruiter.getNume();
            if (current.equals(nume)) {
                return recruiter;
            }
        }
        return null;
    }
}
