package Application;

import java.util.Calendar;
import java.util.Date;

public class Finance extends Department {
    // auxiliary function to determine wether the employee has one year of experience in Finance for the current job
    public boolean lessThanOneYear(Employee employee) {
        Experience current = null;
        for (Experience experience : employee.resume.experiences) {
            if (experience.getEnd() == null) {
                current = experience;
                break;
            }
        }
        if (current == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();

        Date now = new Date();
        calendar.setTime(now);
        int currentDay = calendar.DAY_OF_MONTH;

        calendar.setTime(current.getStart());
        int startDay = calendar.DAY_OF_MONTH;

        if (now.getYear() - current.getStart().getYear() < 1) {
            return true;
        }
        else if (now.getYear() - current.getStart().getYear() == 1) {
            if (now.getMonth() - current.getStart().getMonth() < 0) {
                return true;
            }
            else if (now.getMonth() - current.getStart().getMonth() == 0) {
                if (currentDay - startDay < 0) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    @Override
    public double getTotalSalaryBudget() {
        double total = 0;
        // iterate through the employees, if one has lass than one year, add the budget as 110%, else add it's salary
        // as 116%
        for (Employee employee : getEmployees()) {
            if (lessThanOneYear(employee)) {
                total += 110 * employee.getSalary() / 100;
            }
            else {
                total += 116 * employee.getSalary() / 100;
            }
        }
        return total;
    }
}
