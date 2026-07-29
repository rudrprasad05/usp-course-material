public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        System.out.println("Welcome to Java.");

    }
}

//

class Student {

    String name;
    int age;

    void introduce() {
        System.out.println("Hi, my name is " + name);
    }

}

public class Main {

    public static void main(String[] args) {

        Student s = new Student();

        s.name = "Alice";
        s.age = 21;

        s.introduce();

    }

}

//

class Student {

    String name;

    // Constructor
    Student(String name) {
        this.name = name;
    }

    void introduce() {
        System.out.println(name);
    }

}

public class Main {

    public static void main(String[] args) {

        Student s = new Student("Bob");

        s.introduce();

    }

}

//

class Person {

    String name;

}

public class Main {

    public static void main(String[] args) {

        Person p1 = new Person();

        p1.name = "John";

        Person p2 = p1;

        p2.name = "Mary";

        System.out.println(p1.name);
        System.out.println(p2.name);

    }

}

//

class Animal {

    void speak() {
        System.out.println("Animal sound");
    }

}

class Dog extends Animal {

    @Override
    void speak() {
        System.out.println("Woof!");
    }

}

public class Main {

    public static void main(String[] args) {

        Dog dog = new Dog();

        dog.speak();

    }

}

//

interface Vehicle {

    void start();

}

class Car implements Vehicle {

    @Override
    public void start() {
        System.out.println("Car started");
    }

}

public class Main {

    public static void main(String[] args) {

        Vehicle vehicle = new Car();

        vehicle.start();

    }

}

//

public class Main {

    public static void main(String[] args) {

        try {

            int answer = 10 / 0;

        } catch (ArithmeticException ex) {

            System.out.println("Cannot divide by zero.");

        }

        System.out.println("Program continues.");

    }

}