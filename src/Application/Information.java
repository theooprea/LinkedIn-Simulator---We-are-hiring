package Application;

import Exceptions.LanguageException;
import Helpers.*;

import java.util.ArrayList;
import java.util.Date;

public class Information {
    private String nume, prenume, email, telefon, sex;
    private Date data_de_nastere;
    private ArrayList<LimbaStraina> limbiStraine = new ArrayList<>();

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public Date getData_de_nastere() {
        return data_de_nastere;
    }

    public String getSex() {
        return sex;
    }

    public ArrayList<LimbaStraina> getLimbiStraine() {
        return limbiStraine;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setData_de_nastere(Date data_de_nastere) {
        this.data_de_nastere = data_de_nastere;
    }

    // split the argument by "." and construct a Date according to it's documentation
    public void setData_de_nastere(String data_de_nastere) {
        String[] dates = data_de_nastere.split("\\.");
        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[0]);
        this.data_de_nastere = new Date(year - 1900, month - 1, day);
        // System.out.println(this.data_de_nastere + "\n" + data_de_nastere + "\n");
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void addLimbaStraina(String lang, String skill) {
        try {
            limbiStraine.add(new LimbaStraina(lang, skill));
        }
        catch (LanguageException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addLimbaStraina(LimbaStraina lang) {
        limbiStraine.add(lang);
    }

    public void removeLimbaStraina(LimbaStraina lang) {
        limbiStraine.remove(lang);
    }

    public void removeLimbaStraina(String lang) {
        for (LimbaStraina iterator : limbiStraine) {
            if (iterator.limba.equals(lang)) {
                limbiStraine.remove(iterator);
                return;
            }
        }
    }

    public void removeLimbaStraina(int index) {
        if (index < limbiStraine.size()) {
            limbiStraine.remove(index);
        }
    }

    public void setLimbaSkill(String lang, String skill) {
        if (skill.equals("Beginner") || skill.equals("Advanced") || skill.equals("Experienced")) {
            for (LimbaStraina iterator : limbiStraine) {
                if (iterator.limba.equals(lang)) {
                    iterator.skill = skill;
                }
            }
        }
    }
}
