package com.assignment1;

/*
 * @author
 * Rudr Prasad - S11219309
 * Ahad Ali - S11221529
 * Rishal Prasad - S11221067
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Libs {

    public static Double parseDouble(String str) {
        if (str.isEmpty()) {
            return null;
        }
        try{
            return Double.parseDouble(str);
        }
        catch (NumberFormatException e){
            System.out.println(e);

        }
        return 0.0;
    }

    public static Integer parseInt(String str) {
        if (str.isEmpty()) {
            return null;
        }
        try{
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            System.out.println(e);

        }
        return 0;
    }
    public static double calculateMedian(ArrayList<Integer> comparisons) {
        Collections.sort(comparisons);
        int size = comparisons.size();
        if (size % 2 == 0) {
            return (comparisons.get(size / 2 - 1) + comparisons.get(size / 2)) / 2.0;
        } else {
            return comparisons.get(size / 2);
        }
    }
    public static void printThirtyRunResults(Map<String, ArrayList<Integer>> algorithmComparisonCounter) {
        int bestTime = Integer.MAX_VALUE;
        int worstTime = Integer.MIN_VALUE;
        double averageTime = Double.MAX_VALUE;

        String bestTimeName = "";
        String worstTimeName = "";
        String averageTimeName = "";


        System.out.printf("%-30s%-12s%-12s%-12s%-12s", "Algorithm", "Best", "Mean", "Median", "Worst");
        for (int i = 1; i <= 30; i++) {
            System.out.printf("%-8d", i);
        }
        System.out.println();

        for (Map.Entry<String, ArrayList<Integer>> entry : algorithmComparisonCounter.entrySet()) {
            String algorithmName = entry.getKey();
            ArrayList<Integer> comparisons = entry.getValue();

            // Calculate best, mean, median, and worst
            int best = Collections.min(comparisons);
            int worst = Collections.max(comparisons);
            double mean = comparisons.stream().mapToInt(val -> val).average().orElse(0.0);
            double median = calculateMedian(comparisons);

            if(best < bestTime) {
                bestTime = best;
                bestTimeName = algorithmName;
            }
            if(worst > worstTime) {
                worstTime = worst;
                worstTimeName = algorithmName;
            }
            if(mean < averageTime) {
                averageTime = mean;
                averageTimeName = algorithmName;
            }

            // Print results
            System.out.printf("%-30s%-12d%-12.2f%-12.2f%-12d", algorithmName, best, mean, median, worst);
            for (int count : comparisons) {
                System.out.printf("%-8d", count);
            }
            System.out.println();
        }
        System.out.println("\n");
        System.out.println("Best Algorithm (fastest): " + bestTimeName + " with " + bestTime + " comparisons");
        System.out.println("Best Algorithm (average): " + averageTimeName + " with " + averageTime + " comparisons");
        System.out.println("Worst Algorithm (slowest): " + worstTimeName + " with " + worstTime + " comparisions");

    }
}
