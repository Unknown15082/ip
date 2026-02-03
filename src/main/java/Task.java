public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this(description, false);
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public static Task loadTask(String data) throws AirisException {
        return switch (data.charAt(0)) {
            case 'T' -> Todo.loadTask(data);
            case 'D' -> Deadline.loadTask(data);
            case 'E' -> Event.loadTask(data);
            default -> throw new AirisException("Invalid task type");
        };
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    private String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    public String toSaveData() {
        return String.format("%b|%s", this.isDone, this.description);
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }
}
