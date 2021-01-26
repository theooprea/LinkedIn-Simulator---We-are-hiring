package Exceptions;

// exception to throw when the language skill doesn't match the given skill levels
public class LanguageException extends Exception {
    public LanguageException(String message) {
        super(message);
    }
}
