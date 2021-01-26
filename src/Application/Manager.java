package Application;

import Helpers.*;

import java.util.ArrayList;

public class Manager extends Employee {
    private ArrayList<Request<Job, Consumer>> requests = new ArrayList<>();

    public Manager (String works_for, Double salary, Resume resume) {
        super(works_for, salary, resume);
    }

    public void addRequest(Request<Job, Consumer> request) {
        requests.add(request);
    }

    public void process(Job job) {
        ArrayList<Request<Job, Consumer>> onlyThisJob = new ArrayList<>();
        Application app = Application.getInstance();
        Company mother = null;
        Department department = null;

        // find the company in the app that has this manager
        for (Company company : app.getCompanies()) {
            if (company.getNume().equals(job.getCompany())) {
                mother = company;
                break;
            }
        }
        // find the department which contains the given job
        for (Department dep : mother.getDepartments()) {
            if (dep.contains(job)) {
                department = dep;
                break;
            }
        }

        // keep track only of requests that have this job
        for (Request<Job, Consumer> request : requests) {
            if (request.getKey().equals(job)) {
                onlyThisJob.add(request);
            }
        }

        // if there are no requests for the job, return
        if (onlyThisJob.size() == 0) {
            return;
        }

        // sort in descending order
        int size = onlyThisJob.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (onlyThisJob.get(i).getScore() < onlyThisJob.get(j).getScore()) {
                    Request<Job, Consumer> aux = onlyThisJob.get(i);
                    onlyThisJob.set(i, onlyThisJob.get(j));
                    onlyThisJob.set(j, aux);
                }
            }
        }
        // remove the requests for this job
        for (Request<Job, Consumer> request : onlyThisJob) {
            requests.remove(request);
        }
        // employ the first noPositions candidates
        int employed = 0;
        int noPositions = job.getNoPositions();
        boolean toEmploy = true;
        for (int i = 0; i < size; i++) {
            User utilizator = (User)onlyThisJob.get(i).getValue1();
            // convert the user if it is still viewed as an user in the app (and if there are still places for the job)
            // and employ in the company
            // send according notification if employed or rejected
            if (app.contains(utilizator) && toEmploy) {
                Employee employee = utilizator.convert();
                employee.setSalary(job.getSalary());
                employee.setWorks_for(mother.getNume());
                mother.add(employee, department);
                employed++;
                app.remove(utilizator);
                for (Company company : app.getCompanies()) {
                    company.removeObserver(utilizator);
                }
                employee.addNotification(new Notification("Ati fost angajat pe pozitia " + job.getName(), mother));
                System.out.println("Utilizatorul: " + utilizator.getPrenume() + " " +
                        utilizator.getNume() + " a fost angajat la " + mother.getNume() +
                        " pe pozitia de " + job.getName() + " " + onlyThisJob.get(i).getScore());
            }
            if (employed == noPositions) {
                toEmploy = false;
            }
            if (!toEmploy) {
                utilizator.update(new Notification("Ati fost respins pt pozitia " + job.getName(), mother));
            }
        }
        // send notifications when the job is closed
        job.setOpen(false);
        mother.notifyAllObservers(new Notification("Jobul " + job.getName() + " a fost inchis", mother));
    }

    public ArrayList<Request<Job, Consumer>> getRequests() {
        return requests;
    }
}
