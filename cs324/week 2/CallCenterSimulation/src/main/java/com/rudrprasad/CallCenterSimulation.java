package com.rudrprasad;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CallCenterSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<String> queue = new LinkedList<>();
        String input;

        System.out.println("Call center started");

        while (true){
            System.out.println("Select an option");
            System.out.println("1. New call");
            System.out.println("2. Serve call");
            System.out.println("3. View queue");
            System.out.println("4. Caller hang up");
            System.out.println("5. Exit");

            System.out.println("Enter your choice");
            input = scanner.nextLine();

            if(input.equals("1")){
                System.out.println("Enter caller name");
                String caller = scanner.nextLine();
                System.out.println(caller + " added to queue");
                queue.add(caller);
            }
            else if(input.equals("2")){
                if(queue.isEmpty()){
                    System.out.println("No callers in queue");
                }
                else{
                    System.out.println("Now serving " + queue.poll());
                }
            }
            else if(input.equals("3")){
                if(queue.isEmpty()){
                    System.out.println("No callers in queue");
                }
                else{
                    System.out.println("Current queue: " + queue);
                }
            }
            else if(input.equals("4")){
                if(queue.isEmpty()){
                    System.out.println("No callers in queue");
                    continue;
                }
                else{
                    System.out.println("Enter caller name: ");
                    String caller = scanner.nextLine();
                    if(queue.remove(caller)){
                        System.out.println("Caller " + caller + " removed from queue");
                    }
                    else{
                        System.out.println("Caller " + caller + " not found in queue");
                    }
                }
            }
            else if(input.equals("5")){
                System.out.println("Exiting programme");
                scanner.close();
                return;
            }
        }
    }
}
