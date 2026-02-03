public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    public static Task loadTask(String data) {
        String[] parts = data.split("\\|");
        boolean isDone = Boolean.parseBoolean(parts[1]);
        String description = parts[2];

        return new Todo(description, isDone);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }

    @Override
    public String toSaveData() {
        return String.format("T|%s", super.toSaveData());
    }
}
