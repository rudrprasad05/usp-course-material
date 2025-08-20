# README Assignment 1 CS214
## AlgorithmRacer 3000
### Contributors
- Rudr Prasad s11219309
- Ahad Ali s11221529
- Rishal Prasad s11221067

### How to Run
Before running, ensure all dependencies have been installed by refreshing pom.xml file
Go to the Main.java file and click run. This should bring up the main menu

### Dependencies
- Java 21
- JFreeChart

### Build System
Maven

### Files
- Main.java: starting point of the algorithm racer. 
- University.java: contains the university class that stores data from university.csv
- Libs.java: contains helper functions such as safe parsing of int, doubles and calculating median. 
- Sorter.java: interface that contains method sort thats implemented and overridden by the sorting methods. 
- sorting methods
  - bubble sort
  - builtin sort
  - insertion sort
  - merge sort
- controllers
  - AlgorithmRaceSimulator.java: used for part 2 of the question. Will create a race between all the algorithms. showing in real time the speed at which each algorithm finishes the same task
  - MeanMedianSimulator.java: used for part 3 to find best algorithm as well as the average. 
  - ThreadController.java: will take care of creating, starting and ending threads. 
  - TimeComplexitySimulator.java: create graph that updates every second to show how the time complexity changes as list size (n) increases. 
  - TimeController.java: handle time taken for each algorithms. 
    
### Resources
- World University Rankings 2023-Cleaned.csv - contains data on universities. 