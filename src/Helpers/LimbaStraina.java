package Helpers;

import Exceptions.LanguageException;

// class to design foreign languages that a user knows
public class LimbaStraina {
    public String limba, skill;
    public LimbaStraina(String limba, String skill) throws LanguageException {
        if (!skill.equals("Beginner") && !skill.equals("Advanced")  && !skill.equals("Experienced")) {
            throw new LanguageException("Skill not recognised");
        }
        this.limba = limba;
        this.skill = skill;
    }

    public String toString() {
        return new String(limba + " " + skill);
    }
}
