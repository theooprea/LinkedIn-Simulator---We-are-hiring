package Application;

public class Management extends Department {
    @Override
    public double getTotalSalaryBudget() {
        double total = 0;
        // adding the salary to the budget considering 16% taxes
        for (Employee employee : getEmployees()) {
            total += employee.getSalary();
        }
        return 116 * total / 100;
    }
}
