package airis.task;

import static airis.constants.TimeFormats.ALL;
import static airis.constants.TimeFormats.ISO;
import static airis.constants.TimeFormats.STANDARD;

import java.time.LocalDateTime;

/**
 * Event task type.
 */
public class Event extends Task {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        this(description, startTime, endTime, false);
    }

    /**
     * Full constructor.
     * @param description Description.
     * @param startTime Start time.
     * @param endTime End time.
     * @param isDone Whether the task is done.
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime, boolean isDone) {
        super(description, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Create a task from information string.
     * @param data Information string.
     * @return The created task.
     */
    public static Task loadTask(String data) {
        String[] parts = data.split("\\|");
        boolean isDone = Boolean.parseBoolean(parts[1]);
        String description = parts[2];
        LocalDateTime startTime = LocalDateTime.parse(parts[3], ALL);
        LocalDateTime endTime = LocalDateTime.parse(parts[4], ALL);

        return new Event(description, startTime, endTime, isDone);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s; to: %s)",
                super.toString(),
                this.startTime.format(STANDARD),
                this.endTime.format(STANDARD));
    }

    @Override
    public String toSaveData() {
        return String.format("E|%s|%s|%s",
                super.toSaveData(),
                this.startTime.format(ISO),
                this.endTime.format(ISO));
    }
}

