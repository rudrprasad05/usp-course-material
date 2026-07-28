package usp.cs214.q1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public final class CallCentre {
    private final Queue<Caller> waitingCallers = new ArrayDeque<>();

    public void receiveCall(Caller caller) {
        waitingCallers.add(caller);
    }

    public Optional<Caller> serveNextCaller() {
        return Optional.ofNullable(waitingCallers.poll());
    }

    public int waitingCount() {
        return waitingCallers.size();
    }

    public List<Caller> waitingCallers() {
        return new ArrayList<>(waitingCallers);
    }

    public Optional<Caller> hangUp(String callerName) {
        String nameToFind = callerName == null ? "" : callerName.trim();
        if (nameToFind.isEmpty()) {
            return Optional.empty();
        }

        Iterator<Caller> iterator = waitingCallers.iterator();
        while (iterator.hasNext()) {
            Caller caller = iterator.next();
            if (caller.name().equalsIgnoreCase(nameToFind)) {
                iterator.remove();
                return Optional.of(caller);
            }
        }

        return Optional.empty();
    }
}
