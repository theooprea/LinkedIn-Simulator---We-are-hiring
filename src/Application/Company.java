package Application;

import DesignPatterns.*;
import Helpers.*;

import java.util.ArrayList;

public class Company implements Subject {
    private String nume;
    private Manager manager;
    private ArrayList<Department> departments = new ArrayList<>();
    private ArrayList<Recruiter> recruiters = new ArrayList<>();

    public Company(String nume, Manager manager) {
        this.nume = nume;
        this.manager = manager;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ArrayList<Recruiter> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(ArrayList<Recruiter> recruiters) {
        this.recruiters = recruiters;
    }

    public void add(Department department) {
        departments.add(department);
    }

    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Department department) {
        department.add(employee);
    }

    public void remove(Employee employee) {
        for (Department department : departments) {
            if (department.getEmployees().contains(employee)) {
                department.getEmployees().remove(employee);
                break;
            }
        }
    }

    public void remove(Department department) {
        ArrayList<Employee> aux = (ArrayList<Employee>) department.getEmployees().clone();
        for (Employee employee : aux) {
            department.remove(employee);
        }
        departments.remove(department);
    }

    public void remove(Recruiter recruiter) {
        recruiters.remove(recruiter);
    }

    // move every employee and job from the source to the destination, then remove the source department
    public void move(Department source, Department destination) {
        for (Employee employee : source.getEmployees()) {
            destination.add(employee);
        }
        for (Job job : source.getJobs()) {
            destination.add(job);
        }
        remove(source);
    }

    public void move(Employee employee, Department newDepartment) {
        remove(employee);
        newDepartment.add(employee);
    }

    public boolean contains(Department department) {
        for (Department dep : departments) {
            if (dep.equals(department))
                return true;
        }
        return false;
    }

    public boolean contains(Employee employee) {
        for (Department dep : departments) {
            if (dep.contains(employee))
                return true;
        }
        return false;
    }

    public boolean contains(Recruiter recruiter) {
        for (Recruiter rec : recruiters) {
            if (rec.equals(recruiter))
                return true;
        }
        return false;
    }

    // function to find the farthest recruiter from the given user (in case of equality, take the highest score recruiter)
    public Recruiter getRecruiter(User user) {
        // find the biggest distance from the given user to a recruiter
        int max_distance = 0;
        ArrayList<Recruiter> mostDistantRecruiters = new ArrayList<>();
        for (Recruiter recruiter : recruiters) {
            int k = user.getDegreeInFriendship(recruiter);
            if (max_distance < k)
                max_distance = k;
        }
        // add the recruiters at the maximum distance to the array of most distant recruiters
        for (Recruiter recruiter : recruiters) {
            int k = user.getDegreeInFriendship(recruiter);
            if (max_distance == k)
                mostDistantRecruiters.add(recruiter);
        }
        // find the one with the biggest score and return it
        Double most_experience = 0.00;
        Recruiter finalRecruiter = null;
        for (Recruiter recruiter : mostDistantRecruiters) {
            if (recruiter.getRating() > most_experience) {
                most_experience = recruiter.getRating();
                finalRecruiter = recruiter;
            }
        }
        return finalRecruiter;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> openJobs = new ArrayList<>();
        for (Department department : departments) {
            for (Job job : department.getJobs()) {
                openJobs.add(job);
            }
        }
        return openJobs;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void addObserver(User user) {
        observers.add(user);
    }

    public void removeObserver(User user) {
        observers.remove(user);
    }

    public void notifyAllObservers(Notification notification) {
        for (User user : observers) {
            user.update(notification);
        }
    }

    public String toString() {
        return new String(nume);
    }

    public String toStringDetailed() {
        String str = "";
        str = nume + " " + manager.getPrenume() + " " + manager.getNume() + "\n";
        for (Department department : departments) {
            str += department.toString();
        }
        str += "Recruiters: ";
        for (Recruiter recruiter : recruiters) {
            str += recruiter.getPrenume() + " " + recruiter .getNume() + "; ";
        }
        return str;
    }
}
