package usp.cs214.q1;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class CallCentreApp {
    private final CallCentre callCentre = new CallCentre();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new CallCentreApp().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readRequiredLine("Choose an option: ");
            System.out.println();

            switch (choice) {
                case "1" -> receiveCall();
                case "2" -> serveNextCaller();
                case "3" -> showWaitingCount();
                case "4" -> listWaitingCallers();
                case "5" -> hangUpCaller();
                case "0" -> running = false;
                default -> System.out.println("Invalid option. Please choose 0 to 5.");
            }

            System.out.println();
        }

        System.out.println("Call centre simulation closed.");
    }

    private void printMenu() {
        System.out.println("ANZ Call Centre");
        System.out.println("1. Receive a new call");
        System.out.println("2. Serve next caller");
        System.out.println("3. Show waiting count");
        System.out.println("4. List waiting callers");
        System.out.println("5. Caller hangs up");
        System.out.println("0. Exit");
    }

    private void receiveCall() {
        String name = readRequiredLine("Caller name: ");
        String phoneNumber = readRequiredLine("Captured phone number: ");
        String message = readOptionalLine("Message or reason for calling: ");

        callCentre.receiveCall(new Caller(name, phoneNumber, message));
        System.out.printf("%s joined the queue. Current queue size: %d%n",
                name,
                callCentre.waitingCount());
    }

    private void serveNextCaller() {
        Optional<Caller> caller = callCentre.serveNextCaller();
        if (caller.isEmpty()) {
            System.out.println("No callers are waiting.");
            return;
        }

        System.out.printf("Consultant is now serving: %s%n", caller.get().queueSummary());
        System.out.printf("Callers still waiting: %d%n", callCentre.waitingCount());
    }

    private void showWaitingCount() {
        System.out.printf("Callers currently in the queue: %d%n", callCentre.waitingCount());
    }

    private void listWaitingCallers() {
        List<Caller> callers = callCentre.waitingCallers();
        if (callers.isEmpty()) {
            System.out.println("No callers are waiting.");
            return;
        }

        System.out.println("Current queue:");
        for (int i = 0; i < callers.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, callers.get(i).queueSummary());
        }
    }

    private void hangUpCaller() {
        String name = readRequiredLine("Caller name to remove: ");
        Optional<Caller> caller = callCentre.hangUp(name);
        if (caller.isPresent()) {
            System.out.printf("%s was removed from the queue. Message saved: %s%n",
                    caller.get().name(),
                    displayMessage(caller.get().message()));
        } else {
            System.out.printf("No waiting caller found with the name '%s'.%n", name);
        }
        System.out.printf("Callers still waiting: %d%n", callCentre.waitingCount());
    }

    private String readRequiredLine(String prompt) {
        while (true) {
            String value = readOptionalLine(prompt);
            if (!value.isBlank()) {
                return value;
            }
            System.out.println("This value is required.");
        }
    }

    private String readOptionalLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String displayMessage(String message) {
        return message.isBlank() ? "No message" : message;
    }
}
