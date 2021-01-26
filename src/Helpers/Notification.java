package Helpers;

import Application.*;

// notifications that will be sent between company and users
public class Notification {
    private String message;
    private Company sender;

    public Notification(String message, Company sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Company getSender() {
        return sender;
    }

    public void setSender(Company sender) {
        this.sender = sender;
    }

    public String toString() {
        return new String(message + "\n" + "Sent by: " + sender.getNume());
    }
}
