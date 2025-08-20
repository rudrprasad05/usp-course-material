package com.assignment1.controllers;

/*
 * @author
 * Rudr Prasad - S11219309
 * Ahad Ali - S11221529
 * Rishal Prasad - S11221067
 *
 */

public class TimeController {
    public static long start(){
        return System.nanoTime();
    }

    public static long end(long startTime, String methodName){
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }
}
