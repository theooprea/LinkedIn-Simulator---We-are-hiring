package Application;

import java.util.*;

public class Application {
    private ArrayList<Company> companies = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private static Application instance = null;
    private Application() {

    }

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public Company getCompany(String name) {
        for (Company iterator : companies) {
            if (iterator.getNume().equals(name))
                return iterator;
        }
        return null;
    }

    public void add(Company company) {
        companies.add(company);
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean remove(Company company) {
        for (Company iterator : companies) {
            if (iterator.equals(company)) {
                companies.remove(iterator);
                return true;
            }
        }
        return false;
    }

    public boolean remove(User user) {
        for (User iterator : users) {
            if (iterator.equals(user)) {
                users.remove(iterator);
                return true;
            }
        }
        return false;
    }

    public boolean contains(Consumer consumer) {
        return users.contains(consumer);
    }

    // iterate through the each company to find the ones with name in the list, through each company's department
    // and through each department's job, if it is open, add it to the vector and return it
    public ArrayList<Job> getJobs(List<String> companies) {
        ArrayList<Job> available = new ArrayList<>();
        for (String companie : companies) {
            for (Company comp : this.companies) {
                if (comp.getNume().equals(companie)) {
                    for (Department department : comp.getDepartments()) {
                        for (Job job : department.getJobs()) {
                            available.add(job);
                        }
                    }
                    break;
                }
            }
        }
        return available;
    }
}
