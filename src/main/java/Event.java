public class Event extends Task {
    private final String startTime;
    private final String endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
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

