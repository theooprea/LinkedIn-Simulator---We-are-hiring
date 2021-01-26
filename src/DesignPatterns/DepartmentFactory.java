package DesignPatterns;

import Application.*;

// implemented Factory Pattern to be able to create Departments
public class DepartmentFactory {
    public static Department getDepartment(String type) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("IT")) {
            return new IT();
        }
        else if (type.equalsIgnoreCase("Management")) {
            return new Management();
        }
        else if (type.equalsIgnoreCase("Marketing")) {
            return new Marketing();
        }
        else if (type.equalsIgnoreCase("Finance")) {
            return new Finance();
        }
        else {
            return null;
        }
    }
}
