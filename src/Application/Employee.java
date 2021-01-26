package Application;

public class Employee extends Consumer {
    private String works_for;
    private Double salary;

    public Employee() {
        this.works_for = "";
        this.salary = 0.00;
    }
    public Employee (String works_for, Double salary, Resume resume) {
        this.works_for = works_for;
        this.salary = salary;
        this.resume = resume;
    }

    public String getWorks_for() {
        return works_for;
    }

    public void setWorks_for(String works_for) {
        this.works_for = works_for;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
