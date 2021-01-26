package Application;

import Exceptions.InvalidDatesException;

import java.util.Calendar;
import java.util.Date;

public class Experience implements Comparable {
    private Date start, end;
    private String pozitie, companie;

    // simmilar to education, we check the dates
    public Experience(Date start, Date end, String pozitie, String companie) throws InvalidDatesException {
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
        this.pozitie = pozitie;
        this.companie = companie;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
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

    @Override
    public int compareTo(Object o) {
        Experience other = (Experience) o;
        // return 0 in order to find the element
        if (getStartString().equals(other.getStartString()) && getEndString().equals(other.getEndString()) &&
                getPozitie().equals(other.getPozitie()) && getCompanie().equals(other.getCompanie())) {
            return 0;
        }
        if (end != null && other.end != null) {
            // if end dates are equal compare by company name
            if (end.equals(other.end)) {
                return companie.compareTo(other.companie);
            }
            // else compare by end date
            else {
                return -1 * end.compareTo(other.end);
            }
        }
        // if an end date is null compare by company name
        else {
            return companie.compareTo(other.companie);
        }
    }

    public String toString() {
        if (end != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            String str = new String(companie + " " + pozitie + " started " + (calendar.get(Calendar.MONTH) +
                    1) + " " + calendar.get(Calendar.YEAR) + " to ");
            calendar.setTime(end);
            str += (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR);
            return str;
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            return new String(companie + " " + pozitie + " started " + (calendar.get(Calendar.MONTH) +
                    1) + " " + calendar.get(Calendar.YEAR) + " to " + "present");
        }
    }
}
