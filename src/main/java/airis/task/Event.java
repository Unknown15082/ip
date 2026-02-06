package airis.task;

import static airis.constants.TimeFormats.all;
import static airis.constants.TimeFormats.iso;
import static airis.constants.TimeFormats.standard;

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

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime, boolean isDone) {
        super(description, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Task loadTask(String data) {
        String[] parts = data.split("\\|");
        boolean isDone = Boolean.parseBoolean(parts[1]);
        String description = parts[2];
        LocalDateTime startTime = LocalDateTime.parse(parts[3], all);
        LocalDateTime endTime = LocalDateTime.parse(parts[4], all);

        return new Event(description, startTime, endTime, isDone);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s; to: %s)",
                super.toString(),
                this.startTime.format(standard),
                this.endTime.format(standard));
    }

    @Override
    public String toSaveData() {
        return String.format("E|%s|%s|%s",
                super.toSaveData(),
                this.startTime.format(iso),
                this.endTime.format(iso));
    }
}

