package Helpers;

import Application.Consumer;

// helper class to use in the BFS algorithm to keep track of the distance between users
public class ConsumerNode {
    public Consumer consumer;
    public int count;
    public ConsumerNode(Consumer consumer, int count) {
        this.consumer = consumer;
        this.count = count;
    }
}
