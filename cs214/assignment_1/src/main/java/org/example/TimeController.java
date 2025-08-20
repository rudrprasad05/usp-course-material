package org.example;

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
        long elapsedTimeInMilli = (endTime - startTime) / 1_000_000;
        System.out.println(methodName + ": " + elapsedTimeInMilli + " milliseconds");
        return elapsedTimeInMilli;
    }
}
