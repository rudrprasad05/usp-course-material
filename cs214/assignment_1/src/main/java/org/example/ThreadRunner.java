package org.example;

/*
 * @author
 * Rudr Prasad - S11219309
 * Ahad Ali - S11221529
 * Rishal Prasad - S11221067
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentMap;

public class ThreadRunner {
    public static void createAndRunThreads(ArrayList<University> arrayList, LinkedList<University> linkedList, ConcurrentMap<String, Long> timesMap){

        // threads for merge sort
        Thread mergeSortArrayListThread = createThreadHelper(arrayList, "MergeArray", timesMap);
        Thread mergeSortLinkedListThread = createThreadHelper(linkedList, "MergeLink", timesMap);

        // insertion sort
        Thread insertionSortArrayListThread = createThreadHelper(arrayList, "InsertionArray", timesMap);
        Thread insertionSortLinkedListThread = createThreadHelper(linkedList, "InsertionLink", timesMap);

        // bubble sort
        Thread bubbleSortArrayListThread = createThreadHelper(arrayList, "BubbleArray", timesMap);
        Thread bubbleSortLinkedListThread = createThreadHelper(linkedList, "BubbleLink", timesMap);

        // built in
        Thread builtInSortArrayListThread = createThreadHelper(arrayList, "BuiltInArray", timesMap);
        Thread builtInSortLinkedListThread = createThreadHelper(linkedList, "BuiltInLink", timesMap);

        // start the threads
        mergeSortArrayListThread.start();
        mergeSortLinkedListThread.start();
        insertionSortArrayListThread.start();
        insertionSortLinkedListThread.start();
        bubbleSortArrayListThread.start();
        bubbleSortLinkedListThread.start();
        builtInSortArrayListThread.start();
        builtInSortLinkedListThread.start();

        // join threads so function waits for them to finish then proceeding
        try {
            mergeSortArrayListThread.join();
            mergeSortLinkedListThread.join();
            insertionSortArrayListThread.join();
            insertionSortLinkedListThread.join();
            bubbleSortArrayListThread.join();
            bubbleSortLinkedListThread.join();
            builtInSortArrayListThread.join();
            builtInSortLinkedListThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Done");

    }

    private static Thread createThreadHelper(ArrayList<University> arrayList, String methodName, ConcurrentMap<String, Long> timesMap) {
        // create new thread for Array List
        return new Thread(() -> {
            long startTime = TimeController.start();
            ArrayList<University> tempUniversityArrayList = new ArrayList<>(arrayList);

            if(methodName.toLowerCase().contains("merge")){
                SortMethods.mergeSort(tempUniversityArrayList, University.getRankComparator().reversed());
            }
            else if(methodName.toLowerCase().contains("insertion")){
                SortMethods.insertionSort(tempUniversityArrayList, University.getRankComparator().reversed());
            }
            else if(methodName.toLowerCase().contains("bubble")){
                SortMethods.bubbleSort(tempUniversityArrayList, University.getRankComparator().reversed());
            }
            else{
                SortMethods.builtInSort(tempUniversityArrayList, University.getRankComparator().reversed());
            }

            // calculate time taken for algorithm to run
            long endTime = TimeController.end(startTime, methodName);
            // put the time into map which will be used to create graph
            timesMap.put(methodName, endTime);
        });
    }

    private static Thread createThreadHelper(LinkedList<University> arrayList, String methodName, ConcurrentMap<String, Long> timesMap) {
        // create new thread for Array List
        return new Thread(() -> {
            long startTime = TimeController.start();
            // create new linked list so the original isn't affected
            LinkedList<University> tempUniversityLinkedList = new LinkedList<>(arrayList);

            if(methodName.toLowerCase().contains("merge")){
                SortMethods.mergeSort(tempUniversityLinkedList, University.getRankComparator().reversed());
            }
            else if(methodName.toLowerCase().contains("insertion")){
                SortMethods.insertionSort(tempUniversityLinkedList, University.getRankComparator().reversed());
            }
            else if(methodName.toLowerCase().contains("bubble")){
                SortMethods.bubbleSort(tempUniversityLinkedList, University.getRankComparator().reversed());
            }
            else{
                SortMethods.builtInSort(tempUniversityLinkedList, University.getRankComparator().reversed());
            }

            // calculate time taken for algorithm to run
            long endTime = TimeController.end(startTime, methodName);
            // put the time into map which will be used to create graph
            timesMap.put(methodName, endTime);
        });
    }
}
