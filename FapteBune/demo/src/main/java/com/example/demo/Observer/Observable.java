package com.example.demo.Observer;

public interface Observable {

    public void addObserver(Observer o);
    public void notifyall();
}
