package Application;

import Helpers.*;

import java.util.*;

public abstract class Consumer {
    private ArrayList<Notification> inbox = new ArrayList<>();
    private ArrayList<FriendRequest> friendRequests = new ArrayList<>();
    public ArrayList<Consumer> prieteni = new ArrayList<>();
    public Resume resume;

    public void add(Education education) {
        resume.educations.add(education);
    }

    public void add(Experience experience) {
        resume.experiences.add(experience);
    }

    public void add(Consumer consumer) {
        if (!prieteni.contains(consumer)) {
            prieteni.add(consumer);
        }
    }

    public ArrayList<Consumer> getPrieteni() {
        return prieteni;
    }

    // function to get degree in friendship to given consumer using BFS algorithm
    public int getDegreeInFriendship(Consumer consumer) {
        // initiate visited vector and a queue to hold nodes we go to
        Vector<Consumer> visited = new Vector<>();
        Vector<ConsumerNode> queue = new Vector<>();

        // add the starting node (the "this" consumer) to the queu and the visited vector
        ConsumerNode start = new ConsumerNode(this, 0);
        queue.add(start);
        visited.add(start.consumer);

        // while the queue is not empty
        while (!queue.isEmpty()) {
            // pop the first element from the queue
            ConsumerNode current = queue.firstElement();
            queue.remove(0);

            // iterate through the current user's friends and if we find the searched one we return the distance
            for (Consumer friend : current.consumer.prieteni) {
                if (friend.equals(consumer)) {
                    return current.count + 1;
                }
                // if not, we add them to the queue with one extra cost to distance
                else {
                    if (!visited.contains(friend)) {
                        queue.add(new ConsumerNode(friend, current.count + 1));
                        visited.add(friend);
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    public void remove(Education education) {
        resume.educations.remove(education);
    }

    public void remove(Experience experience) {
        resume.experiences.remove(experience);
    }

    public void remove(Consumer consumer) {
        prieteni.remove(consumer);
    }

    // iterate through user's educations and hold the biggest grad year (taking into consideration only college degrees)
    public Integer getGraduationYear() {
        Integer gradYear = null;
        for (Education education : resume.educations) {
            if (education.getEnd() != null && education.getEducation().equals("college")) {
                if (gradYear == null) {
                    gradYear = education.getEnd().getYear();
                }
                else {
                    if (education.getEnd().getYear() > gradYear) {
                        gradYear = education.getEnd().getYear();
                    }
                }
            }
        }
        if (gradYear == null) {
            return null;
        }

        // Date's getYear returns a number with 1900 less than the real one
        return gradYear + 1900;
    }

    // compute the sum by iterating through the educations and divide by the count of educations
    public Double meanGPA() {
        Double suma = 0.00;
        int count = 0;
        for (Education edu : resume.educations) {
            suma += edu.getMedie();
            count++;
        }
        if (count == 0)
            return 0.00;

        return suma / count;
    }

    // compute the difference in milliseconds between the end date of each experience and the start date (current date
    // instead of end date if it is null) and divide it by 31536000000, the number of milliseconds in one year
    // after computing the whole sum, approximate it by adding (1 year and 1 month = 2 years)
    public int getExperienceYears() {
        double years = 0;
        double aux;
        // calculate experience years
        for (Experience experience : resume.experiences) {
            if (experience.getEnd() != null) {
                aux = experience.getEnd().getTime() - experience.getStart().getTime();
                aux = aux / 31536000;
                aux = aux / 1000;
            }
            else {
                aux = new Date().getTime() - experience.getStart().getTime();
                aux = aux / 31536000;
                aux = aux / 1000;
            }
            years += aux;
        }
        // cast to get the whole part and subtract to check if the reminder is 0
        int years_int = (int)years;
        years -= years_int;
        // if the reminder isn't 0 add one more yead (aproximate by adding)
        if (years != 0) {
            years_int++;
        }
        return years_int;
    }

    public String toString() {
        String str = "";
        str += resume.info.getPrenume() + " ";
        str += resume.info.getNume() + " ";
        str += resume.info.getEmail() + " ";
        str += resume.info.getTelefon() + " ";
        str += resume.info.getSex() + "\n\n";
        for (LimbaStraina lang : resume.info.getLimbiStraine()) {
            str += lang.limba + " " + lang.skill + "\n";
        }
        str += "\n";
        for (Education education : resume.educations) {
            str += education.toString() + "\n";
        }
        str += "\n";
        for (Experience exp : resume.experiences) {
            str += exp.toString() + "\n";
        }
        str += "\n";
        return str;
    }

    public String getNume() {
        return resume.info.getNume();
    }

    public String getPrenume() {
        return resume.info.getPrenume();
    }

    public String getEmail() {
        return resume.info.getEmail();
    }

    public String getTelefon() {
        return resume.info.getTelefon();
    }

    public Date getData_de_nastere() {
        return resume.info.getData_de_nastere();
    }

    // used Calendar in order to get the day of the month, Calendar supports a method to get day of the month while
    // Date doesn't
    public String getBirthday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resume.info.getData_de_nastere());
        return new String(calendar.get(Calendar.DAY_OF_MONTH) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "." +
                calendar.get(Calendar.YEAR));
    }

    public String getSex() {
        return resume.info.getSex();
    }

    public ArrayList<LimbaStraina> getLimbiStraine() {
        return resume.info.getLimbiStraine();
    }

    public void setNume(String nume) {
        this.resume.info.setNume(nume);
    }

    public void setPrenume(String prenume) {
        this.resume.info.setPrenume(prenume);
    }

    public void setEmail(String email) {
        this.resume.info.setEmail(email);
    }

    public void setTelefon(String telefon) {
        this.resume.info.setTelefon(telefon);
    }

    public void setData_de_nastere(Date data_de_nastere) {
        this.resume.info.setData_de_nastere(data_de_nastere);
    }

    public void setData_de_nastere(String data_de_nastere) {
        this.resume.info.setData_de_nastere(data_de_nastere);
    }

    public void setSex(String sex) {
        this.resume.info.setSex(sex);
    }

    public void addLimbaStraina(String lang, String skill) {
        this.resume.info.addLimbaStraina(lang, skill);
    }

    public void addLimbaStraina(LimbaStraina lang) {
        this.resume.info.addLimbaStraina(lang);
    }

    public void removeLimbaStraina(LimbaStraina lang) {
        this.resume.info.removeLimbaStraina(lang);
    }

    public void removeLimbaStraina(String lang) {
        this.resume.info.removeLimbaStraina(lang);
    }

    public void removeLimbaStraina(int index) {
        this.resume.info.removeLimbaStraina(index);
    }

    public void setLimbaSkill(String lang, String skill) {
        this.resume.info.setLimbaSkill(lang, skill);
    }

    public TreeSet<Education> getEducation() {
        return resume.educations;
    }

    public TreeSet<Experience> getExperience() {
        return resume.experiences;
    }

    public ArrayList<Notification> getInbox() {
        return inbox;
    }

    public void addNotification(Notification notification) {
        inbox.add(notification);
    }

    public void addFriendRequest(FriendRequest friendRequest) {
        friendRequests.add(friendRequest);
    }

    public ArrayList<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void acceptFriendRequest(FriendRequest friendRequest) {
        add(friendRequest.getSender());
        friendRequest.getSender().add(this);
        friendRequests.remove(friendRequest);
    }

    public void declineFriendRequest(FriendRequest friendRequest) {
        friendRequests.remove(friendRequest);
    }
}
