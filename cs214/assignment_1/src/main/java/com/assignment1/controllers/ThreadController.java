package com.assignment1.controllers;

/*
 * @author
 * Rudr Prasad - S11219309
 * Ahad Ali - S11221529
 * Rishal Prasad - S11221067
 *
 */

import com.assignment1.University;
import com.assignment1.sortMethods.*;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import static com.assignment1.Libs.printThirtyRunResults;
import static com.assignment1.controllers.MeanMedianSimulator.plotResultsThirtyRun;

public class ThreadController {
    static Map<String, ArrayList<Integer>> algorithmComparisonCounter = new HashMap<>();

    static BubbleSort<University> bubbleSort = new BubbleSort<>();
    static BuiltInSort<University> builtInSort = new BuiltInSort<>();
    static MergeSort<University> mergeSort = new MergeSort<>();
    static InsertionSort<University> insertionSort = new InsertionSort<>();

    // part 1
    public static void createAndRunThreads(ArrayList<University> arrayList, LinkedList<University> linkedList){
        final ConcurrentMap<String, Long> algorithmFinishTimes = new ConcurrentHashMap<>();
        ArrayList<Thread> threads = new ArrayList<>();

        // create all threads and add them to ArrayList of type Threads for easier management.
        threads.add(createThreadForRaceHelper(mergeSort, new ArrayList<>(arrayList), "MergeArray", algorithmFinishTimes));
        threads.add(createThreadForRaceHelper(mergeSort, new LinkedList<>(linkedList), "MergeLink", algorithmFinishTimes));
        threads.add(createThreadForRaceHelper(insertionSort, new ArrayList<>(arrayList), "insertionArray", algorithmFinishTimes));
        threads.add(createThreadForRaceHelper(insertionSort, new LinkedList<>(linkedList), "insertionLink", algorithmFinishTimes));
        threads.add(createThreadForRaceHelper(bubbleSort, new ArrayList<>(arrayList), "BubbleArray", algorithmFinishTimes));
        threads.add(createThreadForRaceHelper(bubbleSort, new LinkedList<>(linkedList), "BubbleLink", algorithmFinishTimes));
        threads.add(createThreadForRaceHelper(builtInSort, new ArrayList<>(arrayList), "builtInArray", algorithmFinishTimes));
        threads.add(createThreadForRaceHelper(builtInSort, new LinkedList<>(linkedList), "builtInLink", algorithmFinishTimes));

        // add loading state
        Thread loading = loadingThread(threads);

        // start all threads
        startAndJoinThreads(threads, loading);

//        JFreeChartController.createChart(algorithmFinishTimes);
        createRaceSimulation(algorithmFinishTimes);
        System.out.println("Done");

    }

    private static void createRaceSimulation(ConcurrentMap<String, Long> algorithmFinishTimes) {
        SwingUtilities.invokeLater(() -> {
            AlgorithmRaceSimulator race = new AlgorithmRaceSimulator(algorithmFinishTimes);
            race.setSize(800, 600);
            race.setLocationRelativeTo(null);
            race.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            race.setVisible(true);
        });
    }

    private static Thread createThreadForRaceHelper(Sorter<University> sorter, List<University> list, String sortName, ConcurrentMap<String, Long> algorithmFinishTimes) {
        return new Thread(() -> {
            long startTime = TimeController.start();
            University.resetComparisonCount();
            sorter.sort(list);
            long endTime = TimeController.end(startTime, sortName);
            System.out.println("\r" + sortName + ": " + endTime + " milliseconds");

            algorithmFinishTimes.put(sortName, endTime);
        });
    }

    // part 2
    public static void createAndRunThirtyThreads(ArrayList<University> arrayList, LinkedList<University> linkedList){
        ArrayList<Thread> threads = new ArrayList<>();

        // create all threads and add them to ArrayList of type Threads for easier management.
        threads.add(createThirtyThreadHelper(mergeSort, new ArrayList<>(arrayList), "MergeArray"));
        threads.add(createThirtyThreadHelper(mergeSort, new LinkedList<>(linkedList), "MergeLink"));
        threads.add(createThirtyThreadHelper(insertionSort, new ArrayList<>(arrayList), "insertionArray"));
        threads.add(createThirtyThreadHelper(insertionSort, new LinkedList<>(linkedList), "insertionLink"));
        threads.add(createThirtyThreadHelper(bubbleSort, new ArrayList<>(arrayList), "BubbleArray"));
        threads.add(createThirtyThreadHelper(bubbleSort, new LinkedList<>(linkedList), "BubbleLink"));
        threads.add(createThirtyThreadHelper(builtInSort, new ArrayList<>(arrayList), "builtInArray"));
        threads.add(createThirtyThreadHelper(builtInSort, new LinkedList<>(linkedList), "builtInLink"));

        // create loading state
        Thread loading = loadingThread(threads);

        // start all threads in array
        startAndJoinThreads(threads, loading);

        printThirtyRunResults(algorithmComparisonCounter);
        plotResultsThirtyRun(algorithmComparisonCounter);
        System.out.println("Done");
    }

    private static Thread createThirtyThreadHelper(Sorter<University> sorter, List<University> list, String sortName){
        final int NUM_RUNS = 30;
        return new Thread(() -> {
            int comparisons = 0;
            int totalComparisons = 0;
            ArrayList<Integer> comparisonCounts = new ArrayList<>();
            for (int i = 0; i < NUM_RUNS; i++) {
                University.resetComparisonCount();
                Collections.shuffle(list);
                sorter.sort(list);

                comparisons = University.getComparisonCount();
                totalComparisons += comparisons;
                comparisonCounts.add(comparisons);
            }
            algorithmComparisonCounter.put(sortName, comparisonCounts);
            System.out.println("\r" + sortName + " - Number of Comparisons: " + totalComparisons);
        });
    }

    // part 3
    public static void createAndRunTimeComplexityThreads(ArrayList<University> arrayList, LinkedList<University> linkedList){
        final ConcurrentMap<String, Map<Integer, Long>> algorithmFinishTimes = new ConcurrentHashMap<>();
        ArrayList<Thread> threads = new ArrayList<>();

        // create all threads and add them to ArrayList of type Threads for easier management.
        threads.add(createAndRunTimeComplexityHelper(mergeSort, new ArrayList<>(arrayList), "MergeArray", algorithmFinishTimes));
        threads.add(createAndRunTimeComplexityHelper(mergeSort, new LinkedList<>(linkedList), "MergeLink", algorithmFinishTimes));
        threads.add(createAndRunTimeComplexityHelper(insertionSort, new ArrayList<>(arrayList), "insertionArray", algorithmFinishTimes));
        threads.add(createAndRunTimeComplexityHelper(insertionSort, new LinkedList<>(linkedList), "insertionLink", algorithmFinishTimes));
        threads.add(createAndRunTimeComplexityHelper(bubbleSort, new ArrayList<>(arrayList), "BubbleArray", algorithmFinishTimes));
        threads.add(createAndRunTimeComplexityHelper(bubbleSort, new LinkedList<>(linkedList), "BubbleLink", algorithmFinishTimes));
        threads.add(createAndRunTimeComplexityHelper(builtInSort, new ArrayList<>(arrayList), "builtInArray", algorithmFinishTimes));
        threads.add(createAndRunTimeComplexityHelper(builtInSort, new LinkedList<>(linkedList), "builtInLink", algorithmFinishTimes));

        // loading state
        Thread loading = loadingThread(threads);

        // start all threads in array
        startAndJoinThreads(threads, loading);

        createGraphs("Time Complexity Graphs", algorithmFinishTimes);
        System.out.println("Done");

    }

    private static Thread createAndRunTimeComplexityHelper(Sorter<University> sorter,List<University> list, String algorithmName, ConcurrentMap<String, Map<Integer, Long>> algorithmFinishTimes){
        final int START = 100;
        final int END = 1000;
        final int GAP = 50;
        final int SIZE = (END - START) / GAP + 1;
        final int ITERATIONS = 30;

        int[] nNums = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            nNums[i] = START + i * GAP;
        }

        return new Thread(() -> {
            Map<Integer, Long> timeResults = new ConcurrentHashMap<>();
            for (int n : nNums) {
                long sum = 0;
                for (int i = 0; i < ITERATIONS; i++) {
                    long tempTime = 0;
                    Collections.shuffle(list);
                    List<University> sublist = null;
                    if(list instanceof ArrayList<University>){
                        sublist = new ArrayList<>(list.subList(0, n));
                    }
                    else if(list instanceof LinkedList<University>){
                        sublist = new LinkedList<>(list.subList(0, n));
                    }
                    else{
                        sublist = new ArrayList<>(list.subList(0, n));
                    }
                    long startTime = System.nanoTime();
                    sorter.sort(sublist);
                    long endTime = System.nanoTime();
                    tempTime = (endTime - startTime);
                    sum += tempTime;
                }
                timeResults.put(n, sum / ITERATIONS);
            }
            algorithmFinishTimes.put(algorithmName, timeResults);
            System.out.println("\rThread " + algorithmName + " finished in max time=" + algorithmFinishTimes.get(algorithmName).get(END)/1_000_000 + " milliseconds");
        });

    }

    private static Thread loadingThread(ArrayList<Thread> threads) {
        return new Thread(() -> {
            try {
                String[] spinner = {"|", "/", "-", "\\"};
                int index = 0;
                while (anyThreadAlive(threads)) {
                    System.out.print("\rLoading... " + spinner[index++]);
                    index %= spinner.length;
                    Thread.sleep(100);
                }
                System.out.print("\r                              \r");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static boolean anyThreadAlive(ArrayList<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private static void createGraphs(String title, ConcurrentMap<String, Map<Integer, Long>> algorithmFinishTimes){
        TimeComplexitySimulator chart = new TimeComplexitySimulator(title, algorithmFinishTimes);
        chart.pack();
        chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chart.setVisible(true);
    }

    private static void startAndJoinThreads(ArrayList<Thread> threads, Thread loading) {
        for(Thread thread : threads){
            thread.start();
        }
        loading.start();

        // join threads so function waits for them to finish then proceeding
        try {
            for(Thread thread : threads){
                thread.join();
            }
            loading.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
