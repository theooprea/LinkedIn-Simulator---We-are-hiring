package Application;

import java.util.ArrayList;

public class Job {
    private String name, company;
    private Boolean open;
    private Constraint<Integer> graduation;
    private Constraint<Integer> experience;
    private Constraint<Double> medie;
    private ArrayList<User> candidati = new ArrayList<>();
    private int noPositions;
    private Double salary;

    public Job(String name, String company, int noPositions, Double salary,
               Integer gradYearLower, Integer gradYearUppper, Integer xpMin, Integer xpMax,
               Double gpaMin, Double gpaMax, Boolean open) {
        this.name = name;
        this.company = company;
        this.noPositions = noPositions;
        this.salary = salary;
        graduation = new Constraint<Integer>(gradYearLower, gradYearUppper);
        experience = new Constraint<Integer>(xpMin, xpMax);
        medie = new Constraint<Double>(gpaMin, gpaMax);
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Constraint<Integer> getGraduation() {
        return graduation;
    }

    public void setGraduation(Constraint<Integer> graduation) {
        this.graduation = graduation;
    }

    public Constraint<Integer> getExperience() {
        return experience;
    }

    public void setExperience(Constraint<Integer> experience) {
        this.experience = experience;
    }

    public Constraint<Double> getMedie() {
        return medie;
    }

    public void setMedie(Constraint<Double> medie) {
        this.medie = medie;
    }

    public ArrayList<User> getCandidati() {
        return candidati;
    }

    public void setCandidati(ArrayList<User> candidati) {
        this.candidati = candidati;
    }

    public int getNoPositions() {
        return noPositions;
    }

    public void setNoPositions(int noPositions) {
        this.noPositions = noPositions;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void apply(User user) {
        // if the job is open and the user meets the requirements proceed
        if (open && meetsRequirements(user)) {
            // find the company in which the job is listed
            Company mother = null;
            Application app = Application.getInstance();
            for (Company company : app.getCompanies()) {
                if (company.getNume().equals(this.company)) {
                    mother = company;
                    break;
                }
            }
            // find the required recruiter
            Recruiter finalRecruiter = mother.getRecruiter(user);
            // which will evaluate the user for this job
            finalRecruiter.evaluate(this, user);
            // add the user to the company's subscribers
            mother.addObserver(user);
        }
    }
    public boolean meetsRequirements(User user) {
        // if the user hasn't graduated yet but there is a graduation constraint fail the request
        if (user.getGraduationYear() == null && graduation.getLower_limit() != null && graduation.getUpper_limit() != null) {
            return false;
        }

        // verify graduation limits
        if ((graduation.getLower_limit() != null && user.getGraduationYear() < graduation.getLower_limit()) ||
                (graduation.getUpper_limit() != null && user.getGraduationYear() > graduation.getUpper_limit())) {
            return false;
        }

        // verify experience limits
        if ((experience.getLower_limit() != null && user.getExperienceYears() < experience.getLower_limit()) ||
                (experience.getUpper_limit() != null && user.getExperienceYears() > experience.getUpper_limit())) {
            return false;
        }

        // verify GPA limits
        if (medie.getLower_limit() != null && user.meanGPA() < medie.getLower_limit()) {
            return false;
        }

        return true;
    }

    public String toString() {
        return new String(name + "@" + company);
    }
}
