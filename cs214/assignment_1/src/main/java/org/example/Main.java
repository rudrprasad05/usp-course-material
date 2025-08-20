package org.example;

/*
*
* @author
* Rudr Prasad - S11219309
* Ahad Ali - S11221529
* Rishal Prasad - S11221067
*
*/

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import static org.example.HelperFunctions.parseDouble;
import static org.example.HelperFunctions.parseInt;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // create ArrayList and LinkedList
        ArrayList<University> universityArrayList = new ArrayList<>();
        LinkedList<University> universityLinkedList = new LinkedList<>();

        // map that will contain times used in graph
        final ConcurrentMap<String, Long> timesMap = new ConcurrentHashMap<>();

        // Read from file and populate both lists
        ReadFile(universityArrayList);
        ReadFile(universityLinkedList);

        System.out.println("Welcome to Algorithm Simulator. Pick an option from the menu (1-3)");
        System.out.println("1. Race all algorithms and display graph \n2. Show best, mean, median and worst solutions \n3. Show worst time complexities");

        String option = scanner.nextLine();

        switch (option) {
            case "1":
                // run threads with sort assignments for part 2 race
                ThreadRunner.createAndRunThreads(universityArrayList, universityLinkedList, timesMap);
                // create graphical chart
                JFreeChartController.createChart(timesMap);
                break;
            case "2":
                System.out.println("a");
                break;
            case "3":
                break;
            default:
                System.out.println("Invalid option");
        }


    }

    // use of polymorphism allows ReadFile to accept both ArrayList and LinkedList
    public static void ReadFile(List<University> universityList){
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
    private static void printToScreen(List<University> universities){
        universities.forEach(university -> System.out.printf(
            "%-5d %-60s %-20s %-8s %-5.2f %-4s %-10s %-6.2s %-6.2s %-6.2f %-6.2f %-6.2f %-6.2f%n",
            university.getRank(),
            university.getName(),
            university.getLocation(),
            university.getNumberOfStudents(),
            university.getRatioOfStudentToStaff(),
            university.getInternationalStudent(),
            university.getMaleToFemaleRatio(),
            university.getOverAllScore(),
            university.getTeachingScore(),
            university.getResearchScore(),
            university.getCitationScore(),
            university.getIndustryIncomeScore(),
            university.getInternationalOutlookScore()
        ));
    }
}
