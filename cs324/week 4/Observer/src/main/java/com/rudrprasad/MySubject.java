package com.rudrprasad;

import java.util.ArrayList;

public class MySubject{
    private final ArrayList<MyObserver> observers = new ArrayList<>();

    public void addObserver( MyObserver obs ){
        observers.add(obs);
    }

    public void removeObserver(MyObserver obs){
        observers.remove(obs);
    }

    protected void notifyObservers() {
        for (MyObserver observer : observers) observer.update();
    }
}
