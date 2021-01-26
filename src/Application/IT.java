package Application;

public class IT extends Department {

    @Override
    public double getTotalSalaryBudget() {
        double total = 0;
        // just add the salary to the budget as it's taxes are 0
        for (Employee employee : getEmployees()) {
            total += employee.getSalary();
        }
        return total;
    }
}
