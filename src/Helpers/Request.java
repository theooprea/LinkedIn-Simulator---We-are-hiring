package Helpers;

import Application.Consumer;

// class to keep track of job applications
public class Request<K, V> {
    private K key;
    private V value1, value2;
    private Double score;
    public Request(K key, V value1, V value2, Double score) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.score = score;
    }
    public K getKey() {
        return key;
    }
    public V getValue1() {
        return value1;
    }
    public V getValue2() {
        return value2;
    }
    public Double getScore() {
        return score;
    }
    public String toString() {
        Consumer user = (Consumer) value1;
        Consumer recruiter = (Consumer) value2;
        return new String("Job: " + key + " ; \nUser: " + user.getPrenume() + " " + user.getNume() +
                " ; Recruiter: " + recruiter.getPrenume() + " " + recruiter.getNume() +" ; \nScore: " + score + "\n");
    }
}