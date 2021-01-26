package Application;

public class Marketing extends Department {
    @Override
    public double getTotalSalaryBudget() {
        double total = 0;
        // add the salary to the budget considering the taxes, 10%, 0% or 16%
        for (Employee employee : getEmployees()) {
            if (employee.getSalary() >= 5000) {
                total += 110 * employee.getSalary() / 100;
            }
            else if (employee.getSalary() <= 3000){
                total += employee.getSalary();
            }
            else {
                total += 116 * employee.getSalary() / 100;
            }
        }
        return total;
    }
}
