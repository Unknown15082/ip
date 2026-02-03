public class Deadline extends Task {
    private final String due;

    public Deadline(String description, String due) {
        super(description);
        this.due = due;
    }

    public Deadline(String description, boolean isDone, String due) {
        super(description, isDone);
        this.due = due;
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
        return String.format("[D]%s (by: %s)", super.toString(), this.due);
    }

    @Override
    public String toSaveData() {
        return String.format("D|%s|%s", super.toSaveData(), this.due);
    }
}
