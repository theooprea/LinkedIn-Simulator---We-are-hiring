package Application;

import DesignPatterns.*;
import Helpers.Notification;

import java.util.ArrayList;

public class User extends Consumer implements Observer {
    private ArrayList<String> companies = new ArrayList<>();
    public User(Resume resume) {
        this.resume = resume;
    }

    public void addCompanie(String companie) {
        companies.add(companie);
    }

    // when converting we instantiate a new employee and transfer the user's friends list and resume information, then
    // iterate through it's friends and remove the former user and add the newly formed employee
    public Employee convert() {
        Employee employee = new Employee();
        employee.prieteni = prieteni;
        employee.resume = resume;

        for (Consumer friend : prieteni) {
            friend.remove(this);
            friend.add(employee);
        }

        return employee;
    }

    // in order to get the total score we compute 1.5 * experience years and add the mean GPA
    public Double getTotalScore() {
        return (double) getExperienceYears() * 1.5 + meanGPA();
    }

    public ArrayList<String> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<String> companies) {
        this.companies = companies;
    }

    public void update(Notification notification) {
        getInbox().add(notification);
    }

    public String toString() {
        return new String(getPrenume() + " " + getNume());
    }
}
