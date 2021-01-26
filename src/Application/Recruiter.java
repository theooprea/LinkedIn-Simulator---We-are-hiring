package Application;

import Helpers.*;
import java.util.ArrayList;

public class Recruiter extends Employee {
    private Double rating;
    public Recruiter (String works_for, Double salary, Resume resume) {
        super(works_for, salary, resume);
        rating = 5.00;
    }
    public int evaluate(Job job, User user) {
        // find the score and the manager of the company which has the given job
        double scor = rating * user.getTotalScore();
        Application app = Application.getInstance();
        ArrayList<Company> companies = app.getCompanies();
        for (Company company : companies) {
            if (company.getNume().equals(getWorks_for())) {
                // add the request to the manager
                company.getManager().addRequest(new Request<Job, Consumer>(job, user, this, scor));
            }
        }
        // increase recruiter rating
        rating += 0.1;
        // keeping the score as an integer by approximating to the nearest superior int
        int scor_int = (int)scor;
        scor -= scor_int;
        if (scor != 0) {
            scor_int++;
        }
        return scor_int;
    }
    public Double getRating() {
        return rating;
    }

    public String toString() {
        return new String(getPrenume() + " " + getNume() + " " + rating);
    }
}
