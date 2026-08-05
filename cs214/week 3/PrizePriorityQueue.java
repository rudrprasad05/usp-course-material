import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrizePriorityQueue {
    private static final double MINIMUM_GPA = 4.0;
    private static final int MAX_RECIPIENTS = 5;
    private static final double PRIZE_MONEY = 1000.00;

    private static class Student {
        private final String id;
        private final String name;
        private final double gpa;

        private Student(String id, String name, double gpa) {
            this.id = id;
            this.name = name;
            this.gpa = gpa;
        }
    }

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("S111001", "Anita Kumar", 4.65),
                new Student("S111002", "Ravi Singh", 3.85),
                new Student("S111003", "Mereani Naidu", 4.92),
                new Student("S111004", "Joana Lal", 4.15),
                new Student("S111005", "Peter Thomas", 4.50),
                new Student("S111006", "Ariana Khan", 4.38),
                new Student("S111007", "Samuel Prasad", 4.75),
                new Student("S111008", "Litia Reddy", 4.05)
        );

        PriorityQueue<Student> prizeQueue = buildPrizeQueue(students);
        List<Student> recipients = selectRecipients(prizeQueue, MAX_RECIPIENTS);
        double share = recipients.isEmpty() ? 0 : PRIZE_MONEY / recipients.size();

        System.out.println("SCIMS prize recipients by highest GPA");
        System.out.println("-------------------------------------");
        printRecipients(recipients, share);

        System.out.println();
        System.out.println("Same recipients in alphabetical order");
        System.out.println("------------------------------------");
        printAlphabetically(recipients, share);

        System.out.println();
        System.out.println("Alphabetical printing is possible without refilling the priority queue");
        System.out.println("by copying the selected students into a list and sorting the list by name.");
    }

    private static PriorityQueue<Student> buildPrizeQueue(List<Student> students) {
        PriorityQueue<Student> queue = new PriorityQueue<>(
                Comparator.comparingDouble((Student student) -> student.gpa)
                        .reversed()
                        .thenComparing(student -> student.name)
        );

        for (Student student : students) {
            if (student.gpa > MINIMUM_GPA) {
                queue.add(student);
            }
        }

        return queue;
    }

    private static List<Student> selectRecipients(PriorityQueue<Student> queue, int maximumRecipients) {
        List<Student> recipients = new ArrayList<>();

        while (!queue.isEmpty() && recipients.size() < maximumRecipients) {
            recipients.add(queue.poll());
        }

        return recipients;
    }

    private static void printAlphabetically(List<Student> recipients, double share) {
        List<Student> alphabetical = new ArrayList<>(recipients);
        alphabetical.sort(Comparator.comparing(student -> student.name));
        printRecipients(alphabetical, share);
    }

    private static void printRecipients(List<Student> recipients, double share) {
        for (int i = 0; i < recipients.size(); i++) {
            Student student = recipients.get(i);
            System.out.printf(
                    "%d. %-14s %-18s GPA: %.2f Prize: $%.2f%n",
                    i + 1,
                    student.id,
                    student.name,
                    student.gpa,
                    share
            );
        }
    }
}
