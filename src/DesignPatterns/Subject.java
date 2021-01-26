package DesignPatterns;

import Application.User;
import Helpers.Notification;

import java.util.ArrayList;

// subject interface for Observer Pattern implementation
public interface Subject {
    ArrayList<User> observers = new ArrayList<>();

    public void addObserver(User user);
    public void removeObserver(User user);
    public void notifyAllObservers(Notification notification);
}
