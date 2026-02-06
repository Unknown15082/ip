package airis.task;

import static airis.constants.TimeFormats.all;
import static airis.constants.TimeFormats.iso;
import static airis.constants.TimeFormats.standard;

import java.time.LocalDateTime;

/**
 * Deadline task type.
 */
public class Deadline extends Task {
    private final LocalDateTime due;

    public Deadline(String description, LocalDateTime due) {
        this(description, due, false);
    }

    public Deadline(String description, LocalDateTime due, boolean isDone) {
        super(description, isDone);
        this.due = due;
    }

    public static Task loadTask(String data) {
        String[] parts = data.split("\\|");
        boolean isDone = Boolean.parseBoolean(parts[1]);
        String description = parts[2];
        LocalDateTime due = LocalDateTime.parse(parts[3], all);

        return new Deadline(description, due, isDone);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)",
                super.toString(), this.due.format(standard));
    }

    @Override
    public String toSaveData() {
        return String.format("D|%s|%s",
                super.toSaveData(), this.due.format(iso));
    }
}
