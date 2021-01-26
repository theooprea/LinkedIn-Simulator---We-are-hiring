import Application.*;
import Exceptions.*;
import DesignPatterns.*;
import Helpers.*;
import Windows.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws InvalidDatesException, ResumeIncompleteException, IOException, ParseException, LanguageException {
        ArrayList<Manager> managers = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<Recruiter> recruiters = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        Application app = Application.getInstance();

        // opening the consumers.json file
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("consumers.json"));
        JSONObject theFile = (JSONObject) obj;

        // opening all arrays for all types of users
        JSONArray managersJson = (JSONArray) theFile.get("managers");
        JSONArray employeesJson = (JSONArray) theFile.get("employees");
        JSONArray recruitersJson = (JSONArray) theFile.get("recruiters");
        JSONArray usersJson = (JSONArray) theFile.get("users");

        // for each manager
        for (Object manager : managersJson) {
            JSONObject managerJson = (JSONObject) manager;

            // parse general info
            String fullName = (String) managerJson.get("name");
            String[] names = fullName.split(" ");
            String name = names[0];
            String surname = names[1];

            String email = (String) managerJson.get("email");
            String phone = (String) managerJson.get("phone");
            String date_of_birth = (String) managerJson.get("date_of_birth");
            String sex = (String) managerJson.get("genre");
            Long salary = (Long) managerJson.get("salary");

            String works_for = null;

            // parse languages
            JSONArray languages = (JSONArray) managerJson.get("languages");
            JSONArray languageSkills = (JSONArray) managerJson.get("languages_level");
            ArrayList<LimbaStraina> limbi = new ArrayList<>();
            for (int i = 0; i < languages.size(); i++) {
                String language = (String) languages.get(i);
                String skill = (String) languageSkills.get(i);
                limbi.add(new LimbaStraina(language, skill));
            }

            // parse educations
            ArrayList<Education> educatii = new ArrayList<>();
            JSONArray educations = (JSONArray) managerJson.get("education");
            for (Object education : educations) {
                JSONObject educationJson = (JSONObject) education;
                String level = (String) educationJson.get("level");
                String edname = (String) educationJson.get("name");
                String start_date = (String) educationJson.get("start_date");
                String end_date = (String) educationJson.get("end_date");
                Double gpa = (Double) educationJson.get("grade");

                // parse dates for each education
                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    educatii.add(new Education(startDate, null, edname, level, gpa));
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    educatii.add(new Education(startDate, endDate, edname, level, gpa));
                }
            }

            // parse experiences
            ArrayList<Experience> experiente = new ArrayList<>();
            JSONArray experiences = (JSONArray) managerJson.get("experience");
            for (Object experience : experiences) {
                JSONObject experienceJson = (JSONObject) experience;
                String company = (String) experienceJson.get("company");
                String position = (String) experienceJson.get("position");
                String start_date = (String) experienceJson.get("start_date");
                String end_date = (String) experienceJson.get("end_date");

                // parse dates for each experience
                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    experiente.add(new Experience(startDate, null, position, company));
                    works_for = company;
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    experiente.add(new Experience(startDate, endDate, position, company));
                }
            }
            // build the resume and the manager
            Resume.ResumeBuilder builder = new Resume.ResumeBuilder(name, surname)
                    .email(email)
                    .telefon(phone)
                    .data_de_nastere(date_of_birth)
                    .sex(sex);
            for (LimbaStraina lang : limbi) {
                builder = builder.limbaStraina(lang);
            }
            for (Education education : educatii) {
                builder = builder.education(education);
            }
            for (Experience experience : experiente) {
                builder = builder.experience(experience);
            }

            // add the manager to the managers list
            managers.add(new Manager(works_for, salary.doubleValue(), builder.build()));
        }

        // parse employees
        for (Object employee : employeesJson) {
            JSONObject employeeJson = (JSONObject) employee;

            // parse general info
            String fullName = (String) employeeJson.get("name");
            String[] names = fullName.split(" ");
            String name = names[0];
            String surname = names[1];

            String email = (String) employeeJson.get("email");
            String phone = (String) employeeJson.get("phone");
            String date_of_birth = (String) employeeJson.get("date_of_birth");
            String sex = (String) employeeJson.get("genre");
            Long salary = (Long) employeeJson.get("salary");

            String works_for = null;

            // parse languages
            JSONArray languages = (JSONArray) employeeJson.get("languages");
            JSONArray languageSkills = (JSONArray) employeeJson.get("languages_level");
            ArrayList<LimbaStraina> limbi = new ArrayList<>();
            for (int i = 0; i < languages.size(); i++) {
                String language = (String) languages.get(i);
                String skill = (String) languageSkills.get(i);
                limbi.add(new LimbaStraina(language, skill));
            }

            // parse educations
            ArrayList<Education> educatii = new ArrayList<>();
            JSONArray educations = (JSONArray) employeeJson.get("education");
            for (Object education : educations) {
                JSONObject educationJson = (JSONObject) education;

                String level = (String) educationJson.get("level");
                String edname = (String) educationJson.get("name");
                String start_date = (String) educationJson.get("start_date");
                String end_date = (String) educationJson.get("end_date");
                Number gpa = (Number) educationJson.get("grade");

                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    educatii.add(new Education(startDate, null, edname, level, gpa.doubleValue()));
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    educatii.add(new Education(startDate, endDate, edname, level, gpa.doubleValue()));
                }
            }

            // parse experiences
            ArrayList<Experience> experiente = new ArrayList<>();
            JSONArray experiences = (JSONArray) employeeJson.get("experience");
            for (Object experience : experiences) {
                JSONObject experienceJson = (JSONObject) experience;
                String company = (String) experienceJson.get("company");
                String position = (String) experienceJson.get("position");
                String start_date = (String) experienceJson.get("start_date");
                String end_date = (String) experienceJson.get("end_date");

                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    experiente.add(new Experience(startDate, null, position, company));
                    works_for = company;
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    experiente.add(new Experience(startDate, endDate, position, company));
                }
            }
            // build the resume and the user
            Resume.ResumeBuilder builder = new Resume.ResumeBuilder(name, surname)
                    .email(email)
                    .telefon(phone)
                    .data_de_nastere(date_of_birth)
                    .sex(sex);
            for (LimbaStraina lang : limbi) {
                builder = builder.limbaStraina(lang);
            }
            for (Education education : educatii) {
                builder = builder.education(education);
            }
            for (Experience experience : experiente) {
                builder = builder.experience(experience);
            }

            // add the employee to the employee list
            employees.add(new Employee(works_for, salary.doubleValue(), builder.build()));
        }

        // parse recruiters
        for (Object recruiter : recruitersJson) {
            JSONObject recruiterJson = (JSONObject) recruiter;

            // parse info
            String fullName = (String) recruiterJson.get("name");
            String[] names = fullName.split(" ");
            String name = names[0];
            String surname = names[1];

            String email = (String) recruiterJson.get("email");
            String phone = (String) recruiterJson.get("phone");
            String date_of_birth = (String) recruiterJson.get("date_of_birth");
            String sex = (String) recruiterJson.get("genre");
            Long salary = (Long) recruiterJson.get("salary");

            String works_for = null;

            // parse laguages
            JSONArray languages = (JSONArray) recruiterJson.get("languages");
            JSONArray languageSkills = (JSONArray) recruiterJson.get("languages_level");
            ArrayList<LimbaStraina> limbi = new ArrayList<>();
            for (int i = 0; i < languages.size(); i++) {
                String language = (String) languages.get(i);
                String skill = (String) languageSkills.get(i);
                limbi.add(new LimbaStraina(language, skill));
            }

            // parse educations
            ArrayList<Education> educatii = new ArrayList<>();
            JSONArray educations = (JSONArray) recruiterJson.get("education");
            for (Object education : educations) {
                JSONObject educationJson = (JSONObject) education;

                String level = (String) educationJson.get("level");
                String edname = (String) educationJson.get("name");
                String start_date = (String) educationJson.get("start_date");
                String end_date = (String) educationJson.get("end_date");
                Number gpa = (Number) educationJson.get("grade");

                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    educatii.add(new Education(startDate, null, edname, level, gpa.doubleValue()));
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    educatii.add(new Education(startDate, endDate, edname, level, gpa.doubleValue()));
                }
            }

            // parse experiences
            ArrayList<Experience> experiente = new ArrayList<>();
            JSONArray experiences = (JSONArray) recruiterJson.get("experience");
            for (Object experience : experiences) {
                JSONObject experienceJson = (JSONObject) experience;
                String company = (String) experienceJson.get("company");
                String position = (String) experienceJson.get("position");
                String start_date = (String) experienceJson.get("start_date");
                String end_date = (String) experienceJson.get("end_date");

                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    experiente.add(new Experience(startDate, null, position, company));
                    works_for = company;
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    experiente.add(new Experience(startDate, endDate, position, company));
                }
            }
            // build the resume and the recruiter
            Resume.ResumeBuilder builder = new Resume.ResumeBuilder(name, surname)
                    .email(email)
                    .telefon(phone)
                    .data_de_nastere(date_of_birth)
                    .sex(sex);
            for (LimbaStraina lang : limbi) {
                builder = builder.limbaStraina(lang);
            }
            for (Education education : educatii) {
                builder = builder.education(education);
            }
            for (Experience experience : experiente) {
                builder = builder.experience(experience);
            }

            // add the recruiter to the recruiter list
            recruiters.add(new Recruiter(works_for, salary.doubleValue(), builder.build()));
        }

        // parse users
        for (Object user : usersJson) {
            JSONObject userJson = (JSONObject) user;

            // parse general info
            String fullName = (String) userJson.get("name");
            String[] names = fullName.split(" ");
            String name = names[0];
            String surname = names[1];

            String email = (String) userJson.get("email");
            String phone = (String) userJson.get("phone");
            String date_of_birth = (String) userJson.get("date_of_birth");
            String sex = (String) userJson.get("genre");

            // parse companies that the user is interested in
            JSONArray companiesJson = (JSONArray) userJson.get("interested_companies");
            ArrayList<String> interestedCompanies = new ArrayList<>();
            for (Object company : companiesJson) {
                String companyString = (String) company;
                interestedCompanies.add(companyString);
            }

            // parse languages
            JSONArray languages = (JSONArray) userJson.get("languages");
            JSONArray languageSkills = (JSONArray) userJson.get("languages_level");
            ArrayList<LimbaStraina> limbi = new ArrayList<>();
            for (int i = 0; i < languages.size(); i++) {
                String language = (String) languages.get(i);
                String skill = (String) languageSkills.get(i);
                limbi.add(new LimbaStraina(language, skill));
            }

            // parse educations
            ArrayList<Education> educatii = new ArrayList<>();
            JSONArray educations = (JSONArray) userJson.get("education");
            for (Object education : educations) {
                JSONObject educationJson = (JSONObject) education;

                String level = (String) educationJson.get("level");
                String edname = (String) educationJson.get("name");
                String start_date = (String) educationJson.get("start_date");
                String end_date = (String) educationJson.get("end_date");
                Number gpa = (Number) educationJson.get("grade");

                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    educatii.add(new Education(startDate, null, edname, level, gpa.doubleValue()));
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    educatii.add(new Education(startDate, endDate, edname, level, gpa.doubleValue()));
                }
            }

            // parse experiences
            ArrayList<Experience> experiente = new ArrayList<>();
            JSONArray experiences = (JSONArray) userJson.get("experience");
            for (Object experience : experiences) {
                JSONObject experienceJson = (JSONObject) experience;
                String company = (String) experienceJson.get("company");
                String position = (String) experienceJson.get("position");
                String start_date = (String) experienceJson.get("start_date");
                String end_date = (String) experienceJson.get("end_date");

                String[] dateStart = start_date.split("\\.");
                int yearS = Integer.parseInt(dateStart[2]);
                int monthS = Integer.parseInt(dateStart[1]);
                int dayS = Integer.parseInt(dateStart[0]);
                Date startDate = new Date(yearS - 1900, monthS - 1, dayS);

                if (end_date == null) {
                    experiente.add(new Experience(startDate, null, position, company));
                }
                else {
                    String[] dateEnd = end_date.split("\\.");
                    int yearE = Integer.parseInt(dateEnd[2]);
                    int monthE = Integer.parseInt(dateEnd[1]);
                    int dayE = Integer.parseInt(dateEnd[0]);
                    Date endDate = new Date(yearE - 1900, monthE - 1, dayE);

                    experiente.add(new Experience(startDate, endDate, position, company));
                }
            }
            // build the resume and the user
            Resume.ResumeBuilder builder = new Resume.ResumeBuilder(name, surname)
                    .email(email)
                    .telefon(phone)
                    .data_de_nastere(date_of_birth)
                    .sex(sex);
            for (LimbaStraina lang : limbi) {
                builder = builder.limbaStraina(lang);
            }
            for (Education education : educatii) {
                builder = builder.education(education);
            }
            for (Experience experience : experiente) {
                builder = builder.experience(experience);
            }

            User utilizator = new User(builder.build());
            for (String company : interestedCompanies) {
                utilizator.addCompanie(company);
            }
            // add the user to the users list and add it to the app
            users.add(utilizator);
            app.add(utilizator);
        }

        // parse the companies file
        Object objCompanies = parser.parse(new FileReader("companies.json"));
        JSONObject theFileCompanies = (JSONObject) objCompanies;

        JSONArray companiesJson = (JSONArray) theFileCompanies.get("companies");
        // parse companies
        for (Object company : companiesJson) {
            JSONObject companyJson = (JSONObject) company;

            // add the name and find the manager in the manager list to add to the company
            String name = (String) companyJson.get("name");
            String managerString = (String) companyJson.get("manager");

            Manager toFind = null;
            for (Manager manager : managers) {
                String nume = manager.resume.info.getPrenume() + " " + manager.resume.info.getNume();
                if (nume.equals(managerString)) {
                    toFind = manager;
                    break;
                }
            }

            // add the current company to the app
            Company currentCompany = new Company(name, toFind);
            app.add(currentCompany);

            // adding departments
            JSONArray departmentsJson = (JSONArray) companyJson.get("departments");
            for (Object department : departmentsJson) {
                JSONObject departmentJson = (JSONObject) department;

                String depName = (String) departmentJson.get("department_name");
                Department currentDepartment = DepartmentFactory.getDepartment(depName);

                // adding employees to the department
                JSONArray depEmployees = (JSONArray) departmentJson.get("employees");
                for (Object employee : depEmployees) {
                    String employeeString = (String) employee;
                    Employee toFindEmp = null;

                    // finding the employee in the employees list
                    for (Employee employee_in_app : employees) {
                        String employeeName = employee_in_app.resume.info.getPrenume() + " " + employee_in_app.resume.info.getNume();
                        if (employeeName.equals(employeeString)) {
                            toFindEmp = employee_in_app;
                            break;
                        }
                    }
                    // add employee to department
                    currentDepartment.add(toFindEmp);
                }
                //add department to company
                currentCompany.add(currentDepartment);

                // adding jobs in departments
                JSONArray jobs = (JSONArray) departmentJson.get("jobs");
                for (Object job : jobs) {
                    JSONObject jobJson = (JSONObject) job;

                    String jobName = (String) jobJson.get("name");
                    Number noPositions = (Number) jobJson.get("locuri");
                    Number salary = (Number) jobJson.get("salary");

                    Number gradYearLoweraux = (Number) jobJson.get("gradYearLower");
                    Number gradYearUpperaux = (Number) jobJson.get("gradYearUpper");
                    Number xpMinaux = (Number) jobJson.get("xpMin");
                    Number xpMaxaux = (Number) jobJson.get("xpMax");
                    Number gpaMinaux = (Number) jobJson.get("gpaMin");
                    Number gpaMaxaux = (Number) jobJson.get("gpaMax");

                    Integer gradYearLower = null;
                    Integer gradYearUpper = null;
                    Integer xpMin = null;
                    Integer xpMax = null;
                    Double gpaMin = null;
                    Double gpaMax = null;

                    if (gradYearLoweraux != null) {
                        gradYearLower = gradYearLoweraux.intValue();
                    }
                    if (gradYearUpperaux != null) {
                        gradYearUpper = gradYearUpperaux.intValue();
                    }
                    if (xpMinaux != null) {
                        xpMin = xpMinaux.intValue();
                    }
                    if (xpMaxaux != null) {
                        xpMax = xpMaxaux.intValue();
                    }
                    if (gpaMinaux != null) {
                        gpaMin = gpaMinaux.doubleValue();
                    }
                    if (gpaMaxaux != null) {
                        gpaMax = gpaMaxaux.doubleValue();
                    }

                    Job jobCurrent = new Job(jobName, name, noPositions.intValue(), salary.doubleValue(),
                            gradYearLower, gradYearUpper, xpMin, xpMax, gpaMin, gpaMax, true);
                    currentDepartment.add(jobCurrent);
                }
            }

            // finding the IT department to add the recruiters to it
            Department IT = null;
            for (Department department : currentCompany.getDepartments()) {
                if (department.getClass().getSimpleName().equals("IT")) {
                    IT = department;
                }
            }

            // adding recruiters
            JSONArray recruitersJsonCompany = (JSONArray) companyJson.get("recruiters");
            for (Object recruiterObj : recruitersJsonCompany) {
                String recruiterString = (String) recruiterObj;
                for (Recruiter recruiter : recruiters) {
                    String recruiterName = recruiter.getPrenume() + " " + recruiter.getNume();
                    if (recruiterName.equals(recruiterString)) {
                        currentCompany.add(recruiter);
                        IT.add(recruiter);
                        break;
                    }
                }
            }
        }

        // parsing the network file
        Object objNetwork = parser.parse(new FileReader("network.json"));
        JSONObject theFileNetwork = (JSONObject) objNetwork;

        JSONArray connections = (JSONArray) theFileNetwork.get("connections");

        // parsing connections
        for (Object connection : connections) {
            JSONObject connectionJson = (JSONObject) connection;

            String source = (String) connectionJson.get("source");
            String destination = (String) connectionJson.get("destination");

            // find the source and destination consumers in either the user, employee or recruiter list, depending on
            // the consumer designation of each connection
            Consumer sourceCons = null;
            Consumer destCons = null;

            int indexSource = Integer.valueOf(source.substring(1)) - 1;
            int indexDest = Integer.valueOf(destination.substring(1)) - 1;

            // if user
            if (source.charAt(0) == 'U') {
                sourceCons = users.get(indexSource);
            }
            // if employee
            if (source.charAt(0) == 'E') {
                sourceCons = employees.get(indexSource);
            }
            // if recruiter
            if (source.charAt(0) == 'R') {
                sourceCons = recruiters.get(indexSource);
            }

            // if user
            if (destination.charAt(0) == 'U') {
                destCons = users.get(indexDest);
            }
            // if employee
            if (destination.charAt(0) == 'E') {
                destCons = employees.get(indexDest);
            }
            // if recruiter
            if (destination.charAt(0) == 'R') {
                destCons = recruiters.get(indexDest);
            }

            sourceCons.add(destCons);
            destCons.add(sourceCons);
        }

        // !!!!!!!!!
        //
        // uncomment this if you want to see an automatic simulation of apply employe proccess involving all users
        // and all companies/jobs that they are interested in and where they qualify
        //
        // !!!!!!!!!

        for (User user : users) {
            for (String company : user.getCompanies()) {
                Company toFind = null;
                for (Company company_in_app : app.getCompanies()) {
                    if (company.equals(company_in_app.getNume())) {
                        toFind = company_in_app;
                        break;
                    }
                }
                ArrayList<Job> jobs = toFind.getJobs();
                for (Job job : jobs) {
                    job.apply(user);
                }
            }
        }
        for (Company company : app.getCompanies()) {
            for (Job job : company.getJobs()) {
                company.getManager().process(job);
            }
        }

        // setting the look and feel for the app to a dark theme
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");

        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (InstantiationException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            System.err.println(ex.getMessage());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println(ex.getMessage());
        }
        // start the app with the login file
        LoginPage loginPage = new LoginPage();
    }
}
