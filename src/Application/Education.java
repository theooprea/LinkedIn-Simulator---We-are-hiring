package Application;

import Exceptions.InvalidDatesException;

import java.util.Calendar;
import java.util.Date;

public class Education implements Comparable {
    private Date start, end;
    private String institution, education;
    private Double medie;
    // checking to see if the end is null, otherwise, if start is after end we throw the exception, or if end is
    // after now, we set end to null (ongoing education)
    public Education(Date start, Date end, String institution, String education, Double medie) throws InvalidDatesException {
        if (end == null) {
            this.end = null;
        }
        else {
            Date now = new Date();
            if (start == null || start.after(end)) {
                throw new InvalidDatesException("Datele introduse sunt invalide");
            }
            if (end.after(now)) {
                end = null;
            }
            this.end = end;
        }
        this.start = start;
        this.institution = institution;
        this.education = education;
        this.medie = medie;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getInstitution() {
        return institution;
    }

    public String getEducation() {
        return education;
    }

    public Double getMedie() {
        return medie;
    }

    public String getStartString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        return new String(calendar.get(Calendar.DAY_OF_MONTH) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "." +
                calendar.get(Calendar.YEAR));
    }

    public String getEndString() {
        if (end == null) {
            return new String("");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        return new String(calendar.get(Calendar.DAY_OF_MONTH) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "." +
                calendar.get(Calendar.YEAR));
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setMedie(Double medie) {
        this.medie = medie;
    }

    @Override
    public int compareTo(Object o) {
        Education other = (Education) o;
        // return 0 in order to find the given object
        if (getInstitution().equals(other.getInstitution()) && getEducation().equals(other.getEducation())
                && getStartString().equals(other.getStartString()) && getEndString().equals(other.getEndString())
                && getMedie().equals(other.getMedie())) {

            return 0;
        }
        // if the education is ongoing compare by start date
        if (end == null || other.end == null) {
            return start.compareTo(other.start);
        }
        else {
            // if end dates are equal compare by GPA
            if (end == other.end) {
                if (medie >= other.medie)
                    return -1;
                return 1;
            }
            else {
                return -1 * end.compareTo(other.end);
            }
        }
    }

    public String toString() {
        if (end != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            String str = new String(institution + " " + education + " started " + (calendar.get(Calendar.MONTH) +
                    1) + " " + calendar.get(Calendar.YEAR) + " to ");
            calendar.setTime(end);
            str += (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR);
            return str;
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            return new String(institution + " " + education + " started " + (calendar.get(Calendar.MONTH) +
                    1) + " " + calendar.get(Calendar.YEAR) + " to " + "present" + " " + medie);
        }
    }
}
