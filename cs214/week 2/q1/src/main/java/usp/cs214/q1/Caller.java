package usp.cs214.q1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Caller {
    private static final DateTimeFormatter DISPLAY_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String name;
    private final String phoneNumber;
    private final String message;
    private final LocalDateTime joinedAt;

    public Caller(String name, String phoneNumber, String message) {
        this.name = requireText(name, "name");
        this.phoneNumber = requireText(phoneNumber, "phone number");
        this.message = message == null ? "" : message.trim();
        this.joinedAt = LocalDateTime.now();
    }

    public String name() {
        return name;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String message() {
        return message;
    }

    public String queueSummary() {
        String note = message.isBlank() ? "No message" : message;
        return "%s (%s) - joined %s - %s".formatted(
                name,
                phoneNumber,
                joinedAt.format(DISPLAY_TIME),
                note);
    }

    private static String requireText(String value, String fieldName) {
        String trimmed = Objects.requireNonNull(value, fieldName + " is required").trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return trimmed;
    }
}
