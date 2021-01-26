package Exceptions;

// exception to throw when the resume is incomplete (used in the Builder Pattern)
public class ResumeIncompleteException extends Exception {
    public ResumeIncompleteException(String message) {
        super(message);
    }
}
