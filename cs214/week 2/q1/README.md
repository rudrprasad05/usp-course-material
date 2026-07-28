# Question 1 - ANZ Call Centre Simulation

The best data structure for this application is a FIFO queue. It gives callers a fair
first-come-first-served order: new callers join the rear of the queue and consultants
serve callers from the front.

This Java console simulation supports:

- adding callers to the waiting queue;
- serving the next caller in FIFO order;
- showing how many callers are currently waiting;
- listing callers in queue order;
- letting a caller hang up by giving their name and message.

## Compile

```sh
javac -d out $(find src/main/java -name '*.java')
```

## Run

```sh
java -cp out usp.cs214.q1.CallCentreApp
```
