package Application;

import Exceptions.LanguageException;
import Exceptions.ResumeIncompleteException;
import Helpers.*;

import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;
// implemented using Builder Pattern
public class Resume {
    public Information info;
    public TreeSet<Education> educations;
    public TreeSet<Experience> experiences;

    private Resume (ResumeBuilder builder) {
        this.info = builder.info;
        this.educations = builder.educations;
        this.experiences = builder.experiences;
    }

    public static class ResumeBuilder {
        Information info = new Information();
        TreeSet<Education> educations = new TreeSet<>(new Comparator<Education>() {
            @Override
            public int compare(Education o1, Education o2) {
                return o1.compareTo(o2);
            }
        });
        TreeSet<Experience> experiences = new TreeSet<>(new Comparator<Experience>() {
            @Override
            public int compare(Experience o1, Experience o2) {
                return o1.compareTo(o2);
            }
        });

        public ResumeBuilder(String prenume, String nume) {
            this.info.setNume(nume);
            this.info.setPrenume(prenume);
        }

        public ResumeBuilder email(String email) {
            this.info.setEmail(email);
            return this;
        }

        public ResumeBuilder telefon(String telefon) {
            this.info.setTelefon(telefon);
            return this;
        }

        public ResumeBuilder sex(String sex) {
            this.info.setSex(sex);
            return this;
        }

        public ResumeBuilder data_de_nastere(Date data_de_nastere) {
            this.info.setData_de_nastere(data_de_nastere);
            return this;
        }

        public ResumeBuilder data_de_nastere(String data_de_nastere) {
            this.info.setData_de_nastere(data_de_nastere);
            return this;
        }

        public ResumeBuilder limbaStraina(LimbaStraina lang) {
            this.info.addLimbaStraina(lang);
            return this;
        }

        public ResumeBuilder limbaStraina(String lang, String skill) {
            try {
                this.info.addLimbaStraina(new LimbaStraina(lang, skill));
            }
            catch (LanguageException e) {
                System.out.println(e.getMessage());
            }
            return this;
        }

        public ResumeBuilder education(Education education) {
            this.educations.add(education);
            return this;
        }

        public ResumeBuilder experience(Experience experience) {
            this.experiences.add(experience);
            return this;
        }

        // if there is lacking info, throw exeption, else, build the resume
        public Resume build() throws ResumeIncompleteException {
            if (this.info.getEmail() == null || this.info.getTelefon() == null ||
                this.info.getSex() == null || this.info.getData_de_nastere() == null) {
                throw new ResumeIncompleteException("Information is incomplete");
            }
            if (this.educations.isEmpty()) {
                throw new ResumeIncompleteException("No education added");
            }
            return new Resume(this);
        }
    }
}
