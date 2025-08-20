package com.rudrprasad;

public class Guitar implements Instrument{
    @Override
    public void play(Note n) {
        System.out.println("guitar is playing note " + n);
    }
}
