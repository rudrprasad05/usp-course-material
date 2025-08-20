package com.rudrprasad;

public class Number extends MySubject{
    private int value;
    public int getValue(){ return value; }

    public void setValue( int in ){
        value = in;
        notifyObservers();
    }
}
