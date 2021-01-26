package Exceptions;

// exception to throw in case there are invalid dates
public class InvalidDatesException extends Exception {
    public InvalidDatesException(String mesaj) {
        super(mesaj);
    }
}
