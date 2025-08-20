package com.rudrprasad;

public class Main {
    public static void main(String[] args) {
        Guitar guitar = new Guitar();
        Flute flute = new Flute();

        Music.tune(flute);
        Music.tune(guitar);
    }
}