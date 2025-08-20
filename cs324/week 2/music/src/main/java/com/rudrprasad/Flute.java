package com.rudrprasad;

public class Flute implements Instrument{
    @Override
    public void play(Note n) {
        System.out.println("flute is playing note " + n);
    }
}
