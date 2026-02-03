import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private static final DateTimeFormatter humanFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private static final DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = LocalDateTime.parse(startTime, humanFormat);
        this.endTime = LocalDateTime.parse(endTime, humanFormat);
    }

    public Event(String description, boolean isDone, String startTime, String endTime) {
        super(description, isDone);
        this.startTime = LocalDateTime.parse(startTime, humanFormat);
        this.endTime = LocalDateTime.parse(endTime, humanFormat);
    }

    public static Task loadTask(String data) {
        String[] parts = data.split("\\|");
        boolean isDone = Boolean.parseBoolean(parts[1]);
        String description = parts[2];
        String startTime = parts[3];
        String endTime = parts[4];

        return new Event(description, isDone, startTime, endTime);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s; by: %s)",
                super.toString(),
                this.startTime.format(isoFormat), this.endTime.format(isoFormat));
    }

    @Override
    public String toSaveData() {
        return String.format("E|%s|%s|%s", super.toSaveData(), this.startTime, this.endTime);
    }
}

