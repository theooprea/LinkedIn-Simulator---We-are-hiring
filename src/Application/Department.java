package Application;

import Helpers.*;

import java.util.ArrayList;

public abstract class Department {
    private ArrayList<Employee> angajati = new ArrayList<>();
    private ArrayList<Job> jobs = new ArrayList<>();
    public abstract double getTotalSalaryBudget();
    public ArrayList<Job> getJobs() {
        ArrayList<Job> openJobs = new ArrayList<>();
        for (Job job : jobs) {
            if (job.getOpen()) {
                openJobs.add(job);
            }
        }
        return openJobs;
    }

    public void add(Employee employee) {
         angajati.add(employee);
    }

    public void remove(Employee employee) {
        angajati.remove(employee);
    }

    // adding a job to the list then finding the company in the app's data to notify all observers
    public void add(Job job) {
        jobs.add(job);
        Company mother = null;
        Application app = Application.getInstance();
        for (Company company : app.getCompanies()) {
            if (company.contains(this)) {
                mother = company;
                break;
            }
        }
        mother.notifyAllObservers(new Notification("Un nou job: " + job.getName() + " a fost adaugat", mother));
    }

    public ArrayList<Employee> getEmployees() {
        return angajati;
    }

    public boolean contains(Employee employee) {
        for (Employee emp : angajati) {
            if (emp.equals(employee))
                return true;
        }
        return false;
    }

    public boolean contains(Job job) {
        for (Job j : jobs) {
            if (j.equals(job))
                return true;
        }
        return false;
    }

    public String toString() {
        String str = getClass().getSimpleName() + ": ";
        for (Employee employee : angajati) {
            str += employee.getPrenume() + " " + employee.getNume() + "; ";
        }
        str += "\n Jobs: ";
        for (Job job : jobs) {
            str += job.getName() + "; ";
        }
        str += "\n";
        return str;
    }
}
