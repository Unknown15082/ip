public class Event extends Task {
    private final String startTime;
    private final String endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String description, boolean isDone, String startTime, String endTime) {
        super(description, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
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
        return String.format("[E]%s (from: %s; by: %s)", super.toString(), this.startTime, this.endTime);
    }

    @Override
    public String toSaveData() {
        return String.format("E|%s|%s|%s", super.toSaveData(), this.startTime, this.endTime);
    }
}

