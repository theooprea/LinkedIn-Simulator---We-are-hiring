package Helpers;

import Application.Consumer;

// additional class to keep track of friend requests
public class FriendRequest {
    private String message;
    private Consumer sender;
    public FriendRequest(String message, Consumer sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Consumer getSender() {
        return sender;
    }

    public void setSender(Consumer sender) {
        this.sender = sender;
    }

    public String toString() {
        return new String(message + " Sent by " + sender.getPrenume() + " " + sender.getNume());
    }
}
