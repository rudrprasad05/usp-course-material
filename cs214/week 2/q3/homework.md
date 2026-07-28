# Question 3 - Homework

## Exception Handling In Java

Exception handling is a way to manage errors or unexpected events that happen while a
program is running. Instead of allowing the program to crash immediately, Java allows
the programmer to detect the problem, handle it, and continue or stop the program in a
controlled way.

### Exception Classes

In Java, exceptions are represented by classes. Most exception classes inherit from
`Throwable`, but the commonly used parent classes are:

- `Exception`: used for conditions that a program may want to catch and handle.
- `RuntimeException`: used for programming errors or unexpected runtime problems.
- `Error`: used for serious problems usually outside the program's control.

Examples of common exception classes include:

- `ArithmeticException`: happens when an illegal arithmetic operation occurs, such as
  division by zero.
- `ArrayIndexOutOfBoundsException`: happens when code tries to access an invalid array
  index.
- `NumberFormatException`: happens when text cannot be converted into a number.
- `IOException`: happens when an input or output operation fails.

Java exceptions can be grouped into two main categories:

- Checked exceptions: must be handled with `try`/`catch` or declared with `throws`.
  `IOException` is an example.
- Unchecked exceptions: do not need to be declared or caught. These usually inherit from
  `RuntimeException`.

### Try And Catch

The `try` block contains code that may cause an exception. The `catch` block contains
code that handles the exception if it occurs.

```java
public class DivisionExample {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero.");
        }
    }
}
```

In this example, dividing by zero causes an `ArithmeticException`. Instead of stopping
the program with an error message from the runtime, the `catch` block handles the
problem and prints a friendly message.

Java also supports `finally`, which runs whether an exception occurs or not. It is often
used for cleanup tasks.

```java
try {
    System.out.println("Opening resource...");
} catch (Exception e) {
    System.out.println("An error occurred.");
} finally {
    System.out.println("Cleanup runs here.");
}
```

### Creating Own Exceptions Using Inheritance

Programmers can create their own exception classes by extending an existing exception
class. This is useful when a program has a specific error condition that should be named
clearly.

```java
public class InvalidCallerException extends Exception {
    public InvalidCallerException(String message) {
        super(message);
    }
}
```

The custom exception can then be thrown when a rule is broken.

```java
public class CallCentre {
    public void addCaller(String name) throws InvalidCallerException {
        if (name == null || name.isBlank()) {
            throw new InvalidCallerException("Caller name cannot be empty.");
        }

        System.out.println("Caller added: " + name);
    }
}
```

The calling code must handle the checked exception.

```java
public class Main {
    public static void main(String[] args) {
        CallCentre callCentre = new CallCentre();

        try {
            callCentre.addCaller("");
        } catch (InvalidCallerException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

This uses inheritance because `InvalidCallerException` inherits the behavior of
`Exception`, while adding a more meaningful name for the program's own error.

## Cells In MATLAB

A cell array in MATLAB is a data structure that can store different types of data in the
same array. Normal arrays usually store values of the same type, such as only numbers,
but a cell array can store numbers, strings, matrices, vectors, or even other cell
arrays.

Cell arrays are created using curly braces.

```matlab
student = {'Anita', 21, [85 90 78]};
```

In this example:

- the first cell stores a character vector;
- the second cell stores a number;
- the third cell stores a numeric vector.

### Accessing Cell Contents

MATLAB uses two styles of indexing for cells:

- Parentheses `()` return a cell.
- Curly braces `{}` return the contents inside the cell.

```matlab
student = {'Anita', 21, [85 90 78]};

nameCell = student(1);
nameValue = student{1};
```

Here, `nameCell` is still a cell array, while `nameValue` is the actual text value
stored inside the first cell.

### Example With Multiple Records

Cell arrays are useful when storing records with mixed data.

```matlab
students = {
    'Anita', 21, [85 90 78];
    'Ravi',  20, [72 88 81];
    'Mere',  22, [91 84 89]
};

firstStudentName = students{1, 1};
firstStudentMarks = students{1, 3};
averageMark = mean(firstStudentMarks);

fprintf('%s has an average mark of %.2f\n', firstStudentName, averageMark);
```

This is useful because each row can describe one student while each column stores a
different kind of information.

## Summary

Java exception handling helps programs respond to errors in a controlled way using
exception classes, `try`/`catch`, `finally`, `throw`, and custom exception classes made
through inheritance. MATLAB cells are flexible containers that allow different data
types to be stored together, making them useful for mixed records and irregular data.
