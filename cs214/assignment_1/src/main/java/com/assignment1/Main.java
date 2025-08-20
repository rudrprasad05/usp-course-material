package com.assignment1;

import com.assignment1.controllers.ThreadController;
import com.assignment1.controllers.TimeController;
import com.assignment1.sortMethods.*;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

import static com.assignment1.Libs.parseDouble;
import static com.assignment1.Libs.parseInt;

public class Main {
    public static void main(String[] args) {
        boolean running = true;

        // create ArrayList and LinkedList
        ArrayList<University> universityArrayList = new ArrayList<>();
        LinkedList<University> universityLinkedList = new LinkedList<>();

        // read file and populate lists
        readFile(universityArrayList);
        readFile(universityLinkedList);

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        while (running) {
            Collections.shuffle(universityArrayList);
            Collections.shuffle(universityLinkedList);

            System.out.println("\nWelcome to Algorithm Simulator. Pick an option from the menu (1-5)");
            System.out.println("""
                1. Use sorting algorithms\s
                2. Race all algorithms and display graph\s
                3. Show best, mean, median and worst solutions\s
                4. Show worst time complexities\s
                5. Exit
                """);

            String option = null;
            try {
                option = scanner.nextLine(); // Read user input
            } catch (Exception e) {
                System.out.println("Error reading input. Please try again.");
                continue;
            }

            switch (option) {
                case "1":
                    System.out.println("\nRunning individual sorting methods...\n");
                    runIndividualSortingMethods(scanner, universityArrayList, universityLinkedList);
                    break;
                case "2":
                    System.out.println("\nCreating and running threads...\n");
                    ThreadController.createAndRunThreads(universityArrayList, universityLinkedList);
                    break;
                case "3":
                    System.out.println("\nCreating and running threads 30 times...Warning!! This will take a few minutes due to the nature of some of the algorithms\n");
                    ThreadController.createAndRunThirtyThreads(universityArrayList, universityLinkedList);
                    break;
                case "4":
                    System.out.println("\nCreating and running threads...Warning!! This will take a few minutes due to the nature of some of the algorithms\n");
                    ThreadController.createAndRunTimeComplexityThreads(universityArrayList, universityLinkedList);
                    break;
                case "5":
                    System.out.println("\nExiting program...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }

        }
        scanner.close();
    }

    private static void runIndividualSortingMethods(Scanner scanner, ArrayList<University> universityArrayList, LinkedList<University> universityLinkedList) {
        System.out.println("""
            1. Merge sort\s
            2. Insertion Sort\s
            3. Bubble sort\s
            4. Built in Sort\s
            5. Exit
            """);
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                System.out.println("\nRunning merge sort...\n");
                runSortingMethodsHelper(new MergeSort<>(), universityArrayList, "Merge Sort ArrayList");
                runSortingMethodsHelper(new MergeSort<>(), universityLinkedList, "Merge Sort LinkedList");
                break;
            case "2":
                System.out.println("\nRunning insertion sort...\n");
                runSortingMethodsHelper(new InsertionSort<>(), universityArrayList, "Insertion Sort ArrayList");
                runSortingMethodsHelper(new InsertionSort<>(), universityLinkedList, "Insertion Sort LinkedList");
                break;
            case "3":
                System.out.println("\nRunning bubble sort...\n");
                runSortingMethodsHelper(new BubbleSort<>(), universityArrayList, "Bubble Sort ArrayList");
                runSortingMethodsHelper(new BubbleSort<>(), universityLinkedList, "Bubble Sort LinkedList");
                break;
            case "4":
                System.out.println("\nRunning builtin sort...\n");
                runSortingMethodsHelper(new BuiltInSort<>(), universityArrayList, "builtin Sort ArrayList");
                runSortingMethodsHelper(new BuiltInSort<>(), universityLinkedList, "builtin Sort LinkedList");
                break;
            case "5":
                System.out.println("\n<-- Going back");
                break;
            default:
                System.out.println("Invalid option");
        }

    }

    private static void runSortingMethodsHelper(Sorter<University> sorter, List<University> list, String sortName){
        Collections.shuffle(list);
        University.resetComparisonCount();

        long startTime = TimeController.start();
        sorter.sort(list);
        long endTime = TimeController.end(startTime, sortName);

        System.out.println(sortName + " finished in " + endTime + "ms" + " with " + University.getComparisonCount() + " comparisons");
    }

    public static void readFile(List<University> universityList){
        String fileName = "World University Rankings 2023-Cleaned.csv";
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);

        assert inputStream != null;
        try (Scanner scanner = new Scanner(inputStream)) {
            // Skip the first line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            // keep reading file until end of file is reached
            while (scanner.hasNextLine()) {
                // grab one line from file
                String line = scanner.nextLine();

                // use regex to separate the fields in each line.
                // can not just use .split(",") as some values contain commas.
                Pattern pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String[] parts = pattern.split(line);

                // define and assign all values that will be used in constructor
                // use helper methods like parseInt to ensure errors are caught
                Integer rank = Integer.parseInt(parts[0]);
                String name = parts[1].replace("\"", "");
                String location = parts[2].replace("\"", "");
                Integer numberOfStudents = parseInt(parts[3].replace("\"", "").replace(",", ""));
                Double ratioOfStudentToStaff = parseDouble(parts[4]);
                String internationalStudent = parts[5];
                String maleToFemaleRatio = parts[6];
                String overAllScore = parts[7];
                String teachingScore = parts[8];
                Double researchScore = parseDouble(parts[9]);
                Double citationScore = parseDouble(parts[10]);
                Double industryIncomeScore = parseDouble(parts[11]);
                Double internationalOutlookScore = parseDouble(parts[12]);

                // call University constructor and initialise the values
                University university = new University(rank, name, location, numberOfStudents, ratioOfStudentToStaff, internationalStudent, maleToFemaleRatio, overAllScore, teachingScore, researchScore, citationScore, industryIncomeScore, internationalOutlookScore);

                // add object to List (either array or linked)
                universityList.add(university);

            }
        }
    }
}