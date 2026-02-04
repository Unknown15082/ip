package airis.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deadline task type.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter humanFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private static final DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime due;

    public Deadline(String description, String due) {
        super(description);
        this.due = LocalDateTime.parse(due, humanFormat);
    }

    public Deadline(String description, boolean isDone, String due) {
        super(description, isDone);
        this.due = LocalDateTime.parse(due, humanFormat);
    }

    public static Task loadTask(String data) {
        String[] parts = data.split("\\|");
        boolean isDone = Boolean.parseBoolean(parts[1]);
        String description = parts[2];
        String due = parts[3];

        return new Deadline(description, isDone, due);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(), this.due.format(isoFormat));
    }

    @Override
    public String toSaveData() {
        return String.format("D|%s|%s", super.toSaveData(), this.due.format(humanFormat));
    }
}
